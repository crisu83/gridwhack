package gridwhack.grid;

import java.awt.Graphics2D;

import gridwhack.GridWhack;
import gridwhack.entity.unit.Player;
import gridwhack.entity.unit.attack.CombatScenario;
import gridwhack.entity.unit.event.*;
import gridwhack.event.IEventListener;
import gridwhack.fov.IViewer;
import gridwhack.gui.unit.HealthBar;
import gridwhack.path.IMover;

/**
 * Grid unit class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GridUnit extends GridEntity implements IMover, IViewer
{
	public static enum Directions { LEFT, RIGHT, UP, DOWN }
	
	protected static final int CRITICAL_MULTIPLIER = 2;
	
	protected String name;
	protected HealthBar healthBar;

	protected int currentHealth;
	protected int maximumHealth;
	protected int level = 1; // units are level 1 by default.
	protected int minimumDamage;
	protected int maximumDamage;
	protected int attackCooldown;
	protected int movementCooldown = 0; // units cannot move by default

	protected long nextAttackTime;
	protected long nextMoveTime;
	protected GridUnit killedBy;

	protected boolean dead = false; // units are obviously not dead by default

	protected int viewRange = 0; // units are blind by default
	protected GridFov fov;
	protected GridPath path;
	
	/**
	 * Creates the unit.
	 * @param filename the sprite filename.
	 * @param grid the grid the unit exists on.
	 */
	public GridUnit(String filename, Grid grid)
	{
		super(filename, grid);
		
		// calculate the next time the unit can engage.
		nextAttackTime = System.currentTimeMillis() + getAttackCooldown();
		
		// calculate the next time the unit can move.
		nextMoveTime = System.currentTimeMillis() + getMovementCooldown();

		// create a new field of view for the unit.
		fov = new GridFov(grid, this);
	}

	/**
	 * Sets the unit health.
	 * @param health the health.
	 */
	public void setHealth(int health)
	{
		currentHealth = maximumHealth = health;
	}
	
	/**
	 * Attacks the target unit.
	 * @param target the unit to engage.
	 */
	public void attack(GridUnit target)
	{
		// make sure the unit may engage.
		if( isHostile(target) && isAttackAllowed() )
		{
			CombatScenario scenario = new CombatScenario(this, target, rand);
			scenario.engage();
		}
	}
	
	/**
	 * @return whether the unit is allowed to move. 
	 */
	public boolean isAttackAllowed()
	{
		long attackInterval = getAttackCooldown();
		
		// check if the unit may engage.
		if( attackInterval>0 && nextAttackTime<System.currentTimeMillis() )
		{
			// calculate the next time the unit can engage.
			nextAttackTime += attackInterval;
			return true;
		}
		// unit may not engage yet.
		else
		{
			return false;
		}
	}

	/**
	 * Increases the unit health by the given amount.
	 * @param amount the amount.
	 */
	public synchronized void increaseHealth(int amount)
	{
		currentHealth += amount;
	}
	
	/**
	 * Reduces the units health by the specified amount.
	 * @param amount the amount to reduce the health.
	 */
	public synchronized void reduceHealth(int amount)
	{
		// reduce the unit health.
		currentHealth -= amount;
		
		// let all listeners know that this unit has lost health.
		fireUnitEvent( new UnitEvent(UnitEvent.UNIT_HEALTHLOSS, this) );
		
		// make sure that the unit health is not below or equal to zero.
		if( currentHealth<=0 )
		{
			markDead();
		}
	}
	
	/**
	 * Marks the unit dead.
	 */
	public synchronized void markDead()
	{
		dead = true;
		
		// let all listeners know that this unit is dead.
		fireUnitEvent( new UnitEvent(UnitEvent.UNIT_DEATH, this) );

		// dead units needs to be removed.
		super.markRemoved();
	}

	/**
	 * Actions to be taken when the unit moves.
	 * @param direction the direction to move.
	 */
	public void move(Directions direction)
	{
		if( direction==Directions.LEFT )
		{
			grid.moveUnit(-1, 0, this);
		}
		else if( direction==Directions.RIGHT )
		{
			grid.moveUnit(1, 0, this);
		}
		else if( direction==Directions.UP )
		{
			grid.moveUnit(0, -1, this);
		}
		else if( direction==Directions.DOWN )
		{
			grid.moveUnit(0, 1, this);
		}
		else
		{
			// stand still.
		}

		// let all listeners know that the unit has moved.
		markMoved();
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
	public synchronized void markMoved()
	{
		// update the field of view.
		updateFov();

		// let all listeners know that the unit has moved.
		fireUnitEvent( new UnitEvent(UnitEvent.UNIT_MOVE, this) );
	}

	/**
	 * Updates the units field of view.
	 */
	public void updateFov()
	{
		// update the field of view.
		fov.update(this.getGridX(), this.getGridY(), this.getViewRange());
	}

	/**
	 * Fires an unit event.
	 * @param e the event.
	 */
	private synchronized void fireUnitEvent(UnitEvent e)
	{
		for( IEventListener listener : getListeners() )
		{			
			// Make sure we only notify unit listeners.
			if( listener instanceof IUnitListener )
			{
				switch( e.getType() )
				{
					// Unit has died.
					case UnitEvent.UNIT_DEATH:
						( (IUnitListener) listener ).onUnitDeath(e);
						break;
					
					// Unit has been spawned.
					case UnitEvent.UNIT_SPAWN:
						( (IUnitListener) listener ).onUnitSpawn(e);
						break;
						
					// Unit has gained health.
					case UnitEvent.UNIT_HEALTHGAIN:
						( (IUnitListener) listener ).onUnitHealthGain(e);
						break;
						
					// Unit has lost health.
					case UnitEvent.UNIT_HEALTHLOSS:
						( (IUnitListener) listener ).onUnitHealthLoss(e);
						break;
						
					// Unit has moved.
					case UnitEvent.UNIT_MOVE:
						( (IUnitListener) listener ).onUnitMove(e);
						break;
						
					// Unknown event.
					default:
				}
			}
		}
	}

	public void setDamage(int minimum, int maximum)
	{
		this.minimumDamage = minimum;
		this.maximumDamage = maximum;
	}
	
	/**
	 * Renders the non-player unit.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		// Make sure the unit is not dead.
		if( !dead )
		{
			super.render(g);
			
			// render the health bar as well if necessary.
			if( healthBar!=null )
			{
				healthBar.render(g);
			}

			if( GridWhack.DEBUG )
			{
				/*
				if( this instanceof Player && fov!=null )
				{
					fov.render(g);
				}
				*/

				if( path!=null )
				{
					path.render(g);
				}
			}
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
	 * Returns the path to the given target if available.
	 * @param tgx the target grid x-coordinate.
	 * @param tgy the target grid y-coordinate.
	 * @param maxPathLength the maximum length of path.
	 * @return the path or null if unavailable.
	 */
	public GridPath getPath(int tgx, int tgy, int maxPathLength)
	{
		return grid.getPath(this.getGridX(), this.getGridY(), tgx, tgy, maxPathLength, this);
	}

	/**
	 * Returns the units field of view.
	 * @return the fov.
	 */
	public GridFov getFov()
	{
		return fov;
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
	 * @param level the level of the unit.
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}

	/**
	 * @param health the maximum health of the unit.
	 */
	public void setMaximumHealth(int health)
	{
		this.maximumHealth = health;
	}

	/**
	 * @return the maximum health of this unit.
	 */
	public int getMaximumHealth()
	{
		return maximumHealth;
	}

	/**
	 * @return the current health of this unit.
	 */
	public int getCurrentHealth()
	{
		return currentHealth;
	}
	
	/**
	 * @return the minimum damage the unit inflicts.
	 */
	public int getMinimumDamage()
	{
		return minimumDamage;
	}

	/**
	 * @return the maximum damage the unit inflicts.
	 */
	public int getMaximumDamage()
	{
		return maximumDamage;
	}

	/**
	 * @param attackCooldown the time between attacks in milliseconds.
	 */
	public void setAttackCooldown(int attackCooldown)
	{
		this.attackCooldown = attackCooldown;
	}

	/**
	 * @return the time between attacks in milliseconds.
	 */
	public long getAttackCooldown()
	{
		return attackCooldown;
	}

	/**
	 * @param movementCooldown the time between movement in milliseconds.
	 */
	public void setMovementCooldown(int movementCooldown)
	{
		this.movementCooldown = movementCooldown;
	}

	/**
	 * @return the time between movement in milliseconds.
	 */
	public long getMovementCooldown()
	{
		return movementCooldown;
	}

	/**
	 * @param killedBy unit which killed the unit.
	 */
	public void setKilledBy(GridUnit killedBy)
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
	 * @param viewRange the unit view range in grid cells.
	 */
	public void setViewRange(int viewRange)
	{
		this.viewRange = viewRange;
	}

	/**
	 * @return the unit view range in grid cells.
	 */
	public int getViewRange()
	{
		return viewRange;
	}
	
	/**
	 * Returns whether the target unit is hostile.
	 * @param target the target unit.
	 * @return whether the target is hostile.
	 */
	public abstract boolean isHostile(GridUnit target);
}