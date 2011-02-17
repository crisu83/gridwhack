package gridwhack.entity.unit;

import gridwhack.entity.item.GridLoot;
import gridwhack.entity.unit.hostile.HostileUnit;
import gridwhack.grid.Grid;
import gridwhack.grid.GridUnit;

public class Player extends GridUnit 
{
	protected int experience;
	
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
	
	public void pickUp(GridLoot loot)
	{
		System.out.println("looted successfully.");
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