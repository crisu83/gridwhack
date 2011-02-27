package gridwhack.entity.character;

import gridwhack.entity.character.attack.BattleScenario;
import gridwhack.entity.character.event.CharacterEvent;
import gridwhack.entity.character.event.ICharacterListener;
import gridwhack.event.IEventListener;
import gridwhack.grid.Grid;
import gridwhack.grid.GridUnit;

import java.awt.*;

/**
 * Character class file.
 * All characters must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Character extends GridUnit
{
	// Character types.
	public static enum Type {
		PLAYER,
		ORC,
		KOBOLD,
		SKELETON
	}

	protected static final int CRITICAL_MULTIPLIER = 2;

	protected String name;

	protected int level = 1; // characters are level 1 by default.
	protected int currentHealth;
	protected int maximumHealth;
	protected int minimumDamage;
	protected int maximumDamage;
	protected int viewRange = 0; // characters are blind by default

	protected int attackCooldown;
	protected int movementCooldown = 0; // characters cannot move by default
	protected long nextAttackTime;
	protected long nextMoveTime;

	protected volatile boolean dead = false; // characters are obviously not dead by default

	protected Character killedBy;

	/**
	 * Creates the character.
	 * @param filename the sprite filename.
	 * @param grid the grid the character exists on.
	 */
	public Character(String filename, Grid grid)
	{
		super(filename, grid);

		// Calculate the next time the character can attack.
		nextAttackTime = System.currentTimeMillis() + getAttackCooldown();

		// Calculate the next time the character can move.
		nextMoveTime = System.currentTimeMillis() + getMovementCooldown();
	}

	/**
	 * Initializes the character.
	 */
	public void init()
	{
		updateFov(); // we need to update the field of view.
	}

	/**
	 * Sets the character health.
	 * @param health the health.
	 */
	public void setHealth(int health)
	{
		currentHealth = maximumHealth = health;
	}

	/**
	 * Sets the character's damage range.
	 * @param minimum the minimum damage.
	 * @param maximum the maximum damage.
	 */
	public void setDamage(int minimum, int maximum)
	{
		this.minimumDamage = minimum;
		this.maximumDamage = maximum;
	}

	/**
	 * Attacks the target character.
	 * @param target the character to attack.
	 */
	public void attack(Character target)
	{
		// make sure the character may attack.
		if( isHostile(target) && isAttackAllowed() )
		{
			BattleScenario scenario = new BattleScenario(this, target, rand);
			scenario.start();
		}
	}

	/**
	 * Returns whether the character is allowed to move.
	 * @return whether attacking is allowed.
	 */
	public boolean isAttackAllowed()
	{
		long attackInterval = getAttackCooldown();

		// Check if the character may attack.
		if( attackInterval>0 && nextAttackTime<System.currentTimeMillis() )
		{
			// calculate the next time the character can engage.
			nextAttackTime += attackInterval;
			return true;
		}
		// Character may not attack yet.
		else
		{
			return false;
		}
	}

	/**
	 * Increases the character health by the given amount.
	 * @param amount the amount.
	 */
	public synchronized void increaseHealth(int amount)
	{
		int health = currentHealth + amount;

		// Increase the character health and make sure that it does not exceed the maximum.
		currentHealth = health<maximumHealth ? health : maximumHealth;

		// Let all listeners know that this character has gained health.
		fireCharacterEvent(new CharacterEvent(CharacterEvent.CHARACTER_HEALTHGAIN, this));
	}

	/**
	 * Reduces the character health by the specified amount.
	 * @param amount the amount.
	 */
	public synchronized void reduceHealth(int amount)
	{
		int health = currentHealth - amount;

		// Reduce the character health and make sure that it does not become negative.
		currentHealth = health>0 ? health : 0;

		// Let all listeners know that this character has lost health.
		fireCharacterEvent(new CharacterEvent(CharacterEvent.CHARACTER_HEALTHLOSS, this));

		// Mark the character dead if its health is reduced to zero.
		if( currentHealth<=0 )
		{
			markDead();
		}
	}

	/**
	 * Marks this character dead.
	 */
	public synchronized void markDead()
	{
		dead = true;

		// Let all listeners know that this character has died.
		fireCharacterEvent(new CharacterEvent(CharacterEvent.CHARACTER_DEATH, this));

		// Mark this character to be removed.
		super.markRemoved();
	}

	/**
	 * Actions to be taken when the character moves.
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
			// Stand still.
		}

		// Let all listeners know that the character has moved.
		markMoved();
	}

	/**
	 * Returns whether the character is allowed to move.
	 * @return whether moving is allowed.
	 */
	public boolean movementAllowed()
	{
		long movementCooldown = getMovementCooldown();

		// Check if the character may move.
		if( movementCooldown>0 && nextMoveTime<System.currentTimeMillis() )
		{
			// Calculate the next time the character can move.
			nextMoveTime += movementCooldown;
			return true;
		}
		// Character may not move at this time.
		else
		{
			return false;
		}
	}

	/**
	 * Mark this character to have moved.
	 */
	public synchronized void markMoved()
	{
		// Update the field of view.
		updateFov();

		// Let all listeners know that the character has moved.
		fireCharacterEvent(new CharacterEvent(CharacterEvent.CHARACTER_MOVE, this));
	}

	/**
	 * Updates the field of view for this character.
	 */
	public void updateFov()
	{
		super.updateFov(viewRange);
	}

	/**
	 * Fires an event for this character.
	 * @param e the event.
	 */
	private synchronized void fireCharacterEvent(CharacterEvent e)
	{
		for( IEventListener listener : getListeners() )
		{
			// Make sure we only notify character listeners.
			if( listener instanceof ICharacterListener)
			{
				switch( e.getType() )
				{
					// Character has died.
					case CharacterEvent.CHARACTER_DEATH:
						( (ICharacterListener) listener ).onCharacterDeath(e);
						break;

					// Character has been spawned.
					case CharacterEvent.CHARACTER_SPAWN:
						( (ICharacterListener) listener ).onCharacterSpawn(e);
						break;

					// Character has gained health.
					case CharacterEvent.CHARACTER_HEALTHGAIN:
						( (ICharacterListener) listener ).onCharacterHealthGain(e);
						break;

					// Character has lost health.
					case CharacterEvent.CHARACTER_HEALTHLOSS:
						( (ICharacterListener) listener ).onCharacterHealthLoss(e);
						break;

					// Character has moved.
					case CharacterEvent.CHARACTER_MOVE:
						( (ICharacterListener) listener ).onCharacterMove(e);
						break;

					// Unknown event.
					default:
				}
			}
		}
	}

	/**
	 * Renders this character.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		// Make sure the character is not dead.
		if( !dead )
		{
			super.render(g);

			/*
			if( GridWhack.DEBUG )
			{
				if( this instanceof Player && fov!=null )
				{
					fov.render(g);
				}

				if( path!=null )
				{
					path.render(g);
				}
			}
			*/
		}
	}

	/**
	 * Returns critical multiplier for this character.
	 * @return the multiplier.
	 */
	public int getCritialMultiplier()
	{
		return CRITICAL_MULTIPLIER;
	}

	/**
	 * Sets the name of this character.
	 * @param name the name.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the name of this character.
	 * @return the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the character level.
	 * @param level the level.
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}

	/**
	 * Returns the character level.
	 * @return the level.
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * Sets the maximum health for this character.
	 * @param health the maximum health.
	 */
	public void setMaximumHealth(int health)
	{
		this.maximumHealth = health;
	}

	/**
	 * Returns the character maximum health.
	 * @return the maximum health.
	 */
	public int getMaximumHealth()
	{
		return maximumHealth;
	}

	/**
	 * Returns the character current health.
	 * @return the current health.
	 */
	public int getCurrentHealth()
	{
		return currentHealth;
	}

	/**
	 * Returns the minimum damage that this character can inflict.
	 * @return the minimum damage.
	 */
	public int getMinimumDamage()
	{
		return minimumDamage;
	}

	/**
	 * Returns the maximum damage this character can inflict.
	 * @return the maximum damage.
	 */
	public int getMaximumDamage()
	{
		return maximumDamage;
	}

	/**
	 * Sets the time that this character must wait in between attacks.
	 * @param cooldown the time in milliseconds.
	 */
	public void setAttackCooldown(int cooldown)
	{
		this.attackCooldown = cooldown;
	}

	/**
	 * Returns the time that this character must wait in between attacks.
	 * @return the time in milliseconds.
	 */
	public long getAttackCooldown()
	{
		return attackCooldown;
	}

	/**
	 * Sets the time that this character must wait in between moves.
	 * @param cooldown the timein milliseconds.
	 */
	public void setMovementCooldown(int cooldown)
	{
		this.movementCooldown = cooldown;
	}

	/**
	 * Returns the time that this character must wait in between moves.
	 * @return the time in milliseconds.
	 */
	public long getMovementCooldown()
	{
		return movementCooldown;
	}

	/**
	 * Sets the character that killed this character.
	 * @param killedBy the character.
	 */
	public void setKilledBy(Character killedBy)
	{
		this.killedBy = killedBy;
	}

	/**
	 * Returns the character that killed this character.
	 * @return the character.
	 */
	public Character getKilledBy()
	{
		return killedBy;
	}

	/**
	 * @return whether the character is dead.
	 */
	public boolean getDead()
	{
		return dead;
	}

	/**
	 * Sets this character view range.
	 * @param range the view range in grid cells.
	 */
	public void setViewRange(int range)
	{
		this.viewRange = range;
	}

	/**
	 * Returns the character view range in grid cells.
	 * @return the view range.
	 */
	public int getViewRange()
	{
		return viewRange;
	}

	/**
	 * Returns whether the target character is hostile.
	 * @param target the target character.
	 * @return whether the target is hostile.
	 */
	public abstract boolean isHostile(Character target);
}
