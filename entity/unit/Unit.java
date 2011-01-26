package gridwhack.entity.unit;

import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.CEvent;
import gridwhack.entity.unit.attack.AttackScenario;
import gridwhack.grid.Grid;
import gridwhack.grid.GridEntity;
import gridwhack.gui.unit.HealthBar;

/**
 * Unit base class.
 */
public abstract class Unit extends GridEntity
{
	public static enum Directions { LEFT, RIGHT, UP, DOWN }
	
	protected static final int CRITICAL_MULTIPLIER = 2;
	
	protected String name;
	protected HealthBar healthBar;
	protected int currentHealth;
	protected int maximumHealth;
	protected int level;
	protected int damage;
	protected int attackCooldown;
	protected int movementCooldown;
	protected long nextAttackTime;
	protected long nextMoveTime;
	protected boolean dead;
	protected Unit killedBy;
	
	/**
	 * Constructs the unit.
	 * @param filename the sprite filename.
	 * @param grid the grid the unit exists on.
	 */
	public Unit(String filename, Grid grid)
	{
		super(filename, grid);
		
		// movement interval defaults to zero
		// which means that the unit cannot move.
		movementCooldown = 0;
		
		// calculate the next time the unit can attack.
		nextAttackTime = System.currentTimeMillis() + getAttackCooldown();
		
		// calculate the next time the unit can move.
		nextMoveTime = System.currentTimeMillis() + getMovementCooldown();
		
		// units are obviously not dead by default.
		dead = false;
	}
	
	public void init()
	{
		// set current health to maximum health
		currentHealth = maximumHealth;
	}
	
	/**
	 * Sets the unit health. 
	 * Wrapper method for setMaximumHealth.
	 * @param health the health.
	 */
	public void setHealth(int health)
	{
		this.setMaximumHealth(health);
	}
	
	/**
	 * Chooses a target out of given potential targets and attacks it.
	 * @param targets the potential targets.
	 */
	public void chooseTarget(ArrayList<Unit> targets)
	{
		// loop through the targets looking for 
		// a hostile target to attack.
		for( Unit target : targets )
		{
			if( target!=this && isHostile(target) )
			{
				// valid target found, attack it.
				attack(target);
				
				// break the loop because we can only attack
				// one unit with each attack.
				break;
			}
		}
	}
	
	/**
	 * Attacks the target unit.
	 * @param target the unit to attack.
	 */
	public void attack(Unit target)
	{
		// make sure the unit may attack.
		if( attackAllowed() )
		{
			new AttackScenario(this, target, rand).attack();
		}
	}
	
	/**
	 * @return whether the unit is allowed to move. 
	 */
	public boolean attackAllowed()
	{
		long attackInterval = getAttackCooldown();
		
		// check if the unit may attack.
		if( attackInterval>0 && nextAttackTime<System.currentTimeMillis() )
		{
			// calculate the next time the unit can attack.
			nextAttackTime += attackInterval;
			return true;
		}
		// unit may not attack yet.
		else
		{
			return false;
		}
	}
	
	/**
	 * Reduces the units health by the specified amount.
	 * @param amount the amount to reduce the health.
	 */
	public void reduceHealth(int amount)
	{
		// reduce the unit health.
		currentHealth -= amount;
		
		// let all listeners know that this unit has lost health.
		fireEvent( new CEvent("healthLoss", this) );
		
		// make sure that the unit health is not below or equal to zero.
		if( currentHealth<=0 )
		{
			markDead();
		}
	}
	
	/**
	 * Marks the unit dead.
	 */
	public void markDead()
	{
		this.dead = true;
		
		// let all listeners know that this unit is dead.
		fireEvent( new CEvent("death", this) );
		
		// dead units needs to be removed.
		super.markRemoved();
	}
	
	/**
	 * @return whether the unit is allowed to move. 
	 */
	public boolean movementAllowed()
	{
		long movementCooldown = getMovementCooldown();
		
		// check if the unit may move.
		if( movementCooldown>0 && nextMoveTime<System.currentTimeMillis() )
		{
			// calculate the next time the unit can move.
			nextMoveTime += movementCooldown;
			return true;
		}
		// unit may not move at this time.
		else
		{		
			return false;
		}
	}
	
	/**
	 * Mark the unit to have moved.
	 */
	public void markMoved()
	{
		// let all listeners know that the unit has moved.
		fireEvent( new CEvent("move", this) );
	}
	
	/**
	 * Renders the non-player unit.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		super.render(g);
		
		// render the health bar aswell if necessary.
		if( healthBar!=null )
		{
			healthBar.render(g);
		}
	}
	
	/**
	 * @return the critical multiplier.
	 */
	public int getCritialMultiplier()
	{
		return CRITICAL_MULTIPLIER;
	}
	
	/**
	 * @return the name of the unit.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param name the name of the unit.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return the level of the unit.
	 */
	public int getLevel()
	{
		return level;
	}
	
	/**
	 * @param level the level of the unit.
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	/**
	 * @return the current health of the unit.
	 */
	public int getCurrentHealth()
	{
		return currentHealth;
	}
	
	/**
	 * Increases the unit health by the given amount.
	 * @param amount the amount.
	 */
	public void increaseHealth(int amount)
	{
		currentHealth += amount;
	}
	
	/**
	 * Decreases the unit health by the given amount.	
	 * @param amount the amount.
	 */
	public void decreaseHealth(int amount)
	{
		currentHealth -= amount;
	}
	
	/**
	 * @return the maximum health of the unit.
	 */
	public int getMaximumHealth()
	{
		return maximumHealth;
	}
	
	/**
	 * @param health the maximum health of the unit.
	 */
	public void setMaximumHealth(int health)
	{
		this.maximumHealth = health;
	}
	
	/**
	 * @return the damage the unit inflicts.
	 * when attacking successfully.
	 */
	public int getDamage()
	{
		return damage;
	}
	
	/**
	 * @param damage the damage the unit inflicts
	 * when attacking successfully.
	 */
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	/**
	 * @return the time between attacks in milliseconds.
	 */
	public long getAttackCooldown()
	{
		return attackCooldown;
	}
	
	/**
	 * @param attackCooldown the time between attacks in milliseconds.
	 */
	public void setAttackCooldown(int attackCooldown)
	{
		this.attackCooldown = attackCooldown;
	}
	
	/**
	 * @return the time between movement in milliseconds.
	 */
	public long getMovementCooldown()
	{
		return movementCooldown;
	}
	
	/**
	 * @param movementCooldown the time between movement in milliseconds.
	 */
	public void setMovementCooldown(int movementCooldown)
	{
		this.movementCooldown = movementCooldown;
	}
	
	/**
	 * @return unit which killed the unit.
	 */
	public Unit getKilledBy()
	{
		return killedBy;
	}
	
	/**
	 * @param killedBy unit which killed the unit.
	 */
	public void setKilledBy(Unit killedBy)
	{
		this.killedBy = killedBy;
	}
	
	/**
	 * @return whether the unit is dead.
	 */
	public boolean getDead()
	{
		return dead;
	}
	
	/**
	 * Returns whether the target unit is hostile.
	 * @param target the target unit.
	 */
	public abstract boolean isHostile(Unit target);
}