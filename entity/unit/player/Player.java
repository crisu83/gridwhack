package gridwhack.entity.unit.player;

import gridwhack.entity.unit.hostile.HostileUnit;
import gridwhack.entity.unit.player.event.IPlayerListener;
import gridwhack.entity.unit.player.event.PlayerEvent;
import gridwhack.event.IEventListener;
import gridwhack.grid.Grid;
import gridwhack.grid.GridCell;
import gridwhack.grid.GridLoot;
import gridwhack.grid.GridUnit;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiWindow;
import gridwhack.gui.item.LootBox;
import gridwhack.gui.message.MessageLog;

/**
 * Player class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Player extends GridUnit
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
	 * @param grid the grid the player belongs to.
	 */
	public Player(Grid grid)
	{		
		super("player.png", grid);

		setName("Player");
		setLevel(1);
		setHealth(300);
		setDamage(50, 100);
		setAttackCooldown(1000);
		setMovementCooldown(1000);
		setViewRange(10);

		grid.setPlayer(10, 10, this);
	}

	public void updateFov()
	{
		super.updateFov();

		// TODO: Think of a better way to call this.	
		grid.updateVisible();
	}
	
	/**
	 * Returns the total experience of this player.
	 * @return the experience.
	 */
	public int getExperience()
	{
		return experience;
	}

	/**
	 * Returns the experience gained on the current level.
	 * @param level the level.
	 * @return the experience.
	 */
	public int getLevelCurrentExperience(int level)
	{
		return getExperience() - getLevelMaximumExperience(level-1);
	}

	/**
	 * Returns the maximum experience on the current level.
	 * @param level the level.
	 * @return the experience.
	 */
	public int getLevelMaximumExperience(int level)
	{
		return experienceLevels[ level ];
	}

	/**
	 * Increase the player experience.
	 * @param amount the amount to increase the experience.
	 */
	public void increaseExprience(int amount)
	{
		experience += amount;

		MessageLog.addMessage("You gain " + amount + " Experience.");

		int level = getLevel();
		int nextLevel = getLevelMaximumExperience(level);

		// Check if the player has leveled up.
		if( experience>=nextLevel )
		{
			levelUp();
		}

		// let all listeners know that the player has gained experience.
		firePlayerEvent( new PlayerEvent(PlayerEvent.PLAYER_EXPERIENCEGAIN, this) );

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
		firePlayerEvent( new PlayerEvent(PlayerEvent.PLAYER_LEVELGAIN, this) );
	}

	/**
	 * Opens the loot window.
	 */
	public void openLootWindow()
	{
		GridCell cell = getCell();

		// Make sure that we have a cell.
		if( cell!=null )
		{
			GridLoot loot = cell.getLoot();

			// Make sure we have loot.
			if( loot!=null )
			{
				// We are now looting.
				startLooting();

				LootBox lootBox = new LootBox(5, 5, loot, this);
				int windowHeight = ( lootBox.getLineHeight() * loot.getItemCount() )+8;
				GuiWindow lootWindow = new GuiWindow((int) getX()+24, (int) getY()+24, 120, windowHeight, null);
				lootWindow.addElement(Gui.PLAYER_LOOTBOX, lootBox);
				Gui.getInstance().addPanel(Gui.PLAYER_LOOTWINDOW, lootWindow);
			}
			// No loot.
			else
			{
				MessageLog.addMessage("Nothing to loot.");
			}
		}
	}

	/**
	 * Loots the selected item.
	 */
	public void lootSelectedItem()
	{
		LootBox lootBox = (LootBox) Gui.getInstance().getPanel(Gui.PLAYER_LOOTWINDOW).getElement(Gui.PLAYER_LOOTBOX);
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
	 * @return whether the player is looting.
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
		Gui.getInstance().removePanel(Gui.PLAYER_LOOTWINDOW);
	}

	/**
	 * Fires an player event.
	 * @param e the event.
	 */
	private synchronized void firePlayerEvent(PlayerEvent e)
	{
		for( IEventListener listener : getListeners() )
		{
			// Make sure we only notify unit listeners.
			if( listener instanceof IPlayerListener)
			{
				switch( e.getType() )
				{
					// Player has gained experience.
					case PlayerEvent.PLAYER_EXPERIENCEGAIN:
						( (IPlayerListener) listener ).onPlayerGainExperience(e);
						break;

					// Player has gained a level.
					case PlayerEvent.PLAYER_LEVELGAIN:
						( (IPlayerListener) listener ).onPlayerGainLevel(e);
						break;

					// Unknown event.
					default:
				}
			}
		}
	}
	
	/**
	 * Returns whether the target unit is hostile.
	 * @param target the target unit.
	 */
	public boolean isHostile(GridUnit target)
	{
		return target instanceof HostileUnit;
	}
}