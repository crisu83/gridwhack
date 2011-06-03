package gridwhack.gameobject.character.player;

import gridwhack.core.ImageLoader;
import gridwhack.event.IEventListener;
import gridwhack.gameobject.character.Character;
import gridwhack.gameobject.character.hostile.HostileCharacter;
import gridwhack.gameobject.character.player.event.IPlayerExperienceListener;
import gridwhack.gameobject.character.player.event.IPlayerLevelListener;
import gridwhack.gameobject.character.player.event.IPlayerListener;
import gridwhack.gameobject.character.player.event.PlayerEvent;
import gridwhack.gameobject.grid.Grid;
import gridwhack.gameobject.grid.GridCell;
import gridwhack.gameobject.loot.Loot;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;
import gridwhack.gui.GuiPanel;
import gridwhack.gui.GuiWindow;
import gridwhack.gui.item.LootBox;
import gridwhack.gui.message.MessageLog;
import gridwhack.util.Vector2;

/**
 * Player class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Player extends Character
{
	protected int[] experienceLevels = {
		0, // level 1
		1000, // level 2
		2500, // level 3
		4750, // level 4
		8125, // level 5
		13200, // Level 6
		20825, // level 7
		32275, // level 8
		49450, // level 9
		75225, // level 10
	};

	protected int experience = 0;
	private static volatile boolean looting = false;

	/**
	 * Creates the player.
	 */
	public Player()
	{		
		super();

		setImage(ImageLoader.getInstance().loadImage("WarriorMale.png"));

		setName("Player");
		setLevel(1);
		setHealth(300);
		setDamage(50, 100);
		setAttackCooldown(1000);
		setMovementCooldown(1000);
		setViewRange(10);
	}
	
	/**
	 * Returns the total experience of this player.
	 * @return The experience.
	 */
	public int getExperience()
	{
		return experience;
	}

	/**
	 * Returns the experience gained on the current level.
	 * @param level The level.
	 * @return The experience.
	 */
	public int getLevelCurrentExperience(int level)
	{
		//level = level>0 ? level : getLevel();
		return getExperience() - getLevelMaximumExperience(level-1);
	}

	/**
	 * Returns the maximum experience on the current level.
	 * @param level The level.
	 * @return The experience.
	 */
	public int getLevelMaximumExperience(int level)
	{
		//level = level>0 ? level : getLevel();
		return experienceLevels[ level ];
	}

	/**
	 * Increase the player experience.
	 * @param amount The amount to increase the experience.
	 */
	public void increaseExperience(int amount)
	{
		experience += amount;

		MessageLog.addMessage("You gain " + amount + " Experience.");

		int nextLevel = getLevelMaximumExperience( getLevel() );

		// Check if the player has leveled up.
		if (experience >= nextLevel)
		{
			levelUp();
		}

		// Let all listeners know that the player has gained experience.
		firePlayerEvent( new PlayerEvent(PlayerEvent.Type.EXPERIENCEGAIN, this) );
	}

	/**
	 * Actions to be taken when this player levels up.
	 */
	protected void levelUp()
	{
		// Increase the player level by one.
		setLevel( getLevel() + 1 );

		MessageLog.addMessage("You have reached level " + getLevel() + "!");

		int maximumHealth = getMaximumHealth();
		int newMaximumHealth = (int) Math.round(maximumHealth * 1.1);

		// Increase the player's maximum health by 10%.
		setMaximumHealth( newMaximumHealth );

		// Heal the player.
		setHealth( getMaximumHealth() );

		// let all listeners know that the player has gained a level.		
		firePlayerEvent( new PlayerEvent(PlayerEvent.Type.LEVELGAIN, this) );
	}

	/**
	 * Opens the loot window.
	 */
	public void openLootWindow()
	{
		Vector2 gp = getGridPosition();

		if (gp != null)
		{
			GridCell cell = grid.getCell((int) gp.x, (int) gp.y);

			// Make sure that we have a cell.
			if (cell != null)
			{
				Loot loot = cell.getLoot();

				// Make sure we have loot.
				if(loot != null)
				{
					// We are now looting.
					startLooting();

					Vector2 position = getPosition();

					LootBox lootBox = new LootBox(5, 5, loot, this);
					int windowHeight = ( lootBox.getLineHeight() * loot.getItemCount() )+8;
					GuiWindow lootWindow = new GuiWindow((int) position.x + 24, (int) position.y + 24, 120, windowHeight);
					lootWindow.addChild(GuiElement.GuiElementType.PLAYER_LOOTBOX, lootBox);
					Gui.getInstance().addPanel(GuiPanel.GuiPanelType.WINDOW_PLAYER_LOOT, lootWindow);
				}
				// No loot.
				else
				{
					MessageLog.addMessage("Nothing to loot.");
				}
			}
		}
	}

	/**
	 * Loots the selected item.
	 */
	public void lootSelectedItem()
	{
		LootBox lootBox = (LootBox) Gui.getInstance().getPanel(
				GuiPanel.GuiPanelType.WINDOW_PLAYER_LOOT).getChild(GuiElement.GuiElementType.PLAYER_LOOTBOX);
		
		lootBox.lootSelectedItem();
	}

	/**
	 * Actions to be taken when player starts looting.
	 */
	public void startLooting()
	{
		looting = true;
	}

	/**
	 * @return Whether the player is looting.
	 */
	public boolean isLooting()
	{
		return looting;
	}

	/**
	 * Actions to be taken when the player stops looting.
	 */
	public void stopLooting()
	{
		looting = false;

		// We need to close the loot window.
		Gui.getInstance().removePanel(GuiPanel.GuiPanelType.WINDOW_PLAYER_LOOT);
	}

	/**
	 * Fires a player event.
	 * @param e The event.
	 */
	private synchronized void firePlayerEvent(PlayerEvent e)
	{
		// Loop through the listeners and notify them.
		for( IEventListener listener : getListeners() )
		{
			// Make sure we only notify player listeners.
			if( listener instanceof IPlayerListener )
			{
				switch( (PlayerEvent.Type) e.getType() )
				{
					// Player has gained experience.
					case EXPERIENCEGAIN:
						if( listener instanceof IPlayerExperienceListener )
						{
							( (IPlayerExperienceListener) listener ).onPlayerGainExperience(e);
						}
						break;

					// Player has gained a level.
					case LEVELGAIN:
						if( listener instanceof IPlayerLevelListener )
						{
							( (IPlayerLevelListener) listener ).onPlayerGainLevel(e);
						}
						break;

					// Unknown event.
					default:
						System.out.println("Failed to fire player event, type '" + e.getType() + "' is invalid!");
				}
			}
		}
	}
	
	/**
	 * Returns whether the target character is hostile.
	 * @param target The target character.
	 */
	public boolean isHostile(Character target)
	{
		return target instanceof HostileCharacter;
	}
}