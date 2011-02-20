package gridwhack.entity.unit;

import gridwhack.GridWhack;
import gridwhack.entity.unit.hostile.HostileUnit;
import gridwhack.grid.Grid;
import gridwhack.grid.GridCell;
import gridwhack.grid.GridLoot;
import gridwhack.grid.GridUnit;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiPanel;
import gridwhack.gui.GuiWindow;
import gridwhack.gui.item.LootBox;
import gridwhack.gui.message.MessageLog;

/**
 * Player class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Player extends GridUnit
{
	protected int experience;

	private static volatile boolean looting = false;

	/**
	 * Creates the player.
	 * @param grid the grid the player belongs to.
	 */
	public Player(Grid grid, GridWhack game)
	{		
		super("player.png", grid);

		setName("You");
		setLevel(1);
		setHealth(300);
		setDamage(50, 100);
		setAttackCooldown(1000);
		setMovementCooldown(1000);
		setViewRange(10);
	}

	public void updateFov()
	{
		super.updateFov();

		// TODO: Think of a better way to call this.	
		grid.updateVisible();
	}
	
	/**
	 * @return the player experience.
	 */
	public int getExperience()
	{
		return experience;
	}
	
	/**
	 * Increase the player experience.
	 * @param amount the amount to increase the experience.
	 */
	public void increaseExprience(int amount)
	{
		experience += amount;
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
			startLooting();

			GridLoot loot = cell.getLoot();

			// Make sure we have loot.
			if( loot!=null )
			{
				GuiWindow lootWindow = new GuiWindow((int) getX()+24, (int) getY()+24, 100, 100, null);
				LootBox lootBox = new LootBox(5, 5, loot, this);
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
	 * Returns whether the target unit is hostile.
	 * @param target the target unit.
	 */
	public boolean isHostile(GridUnit target)
	{
		return target instanceof HostileUnit;
	}
}