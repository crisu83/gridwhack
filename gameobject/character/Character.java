package gridwhack.gameobject.character;

import gridwhack.base.BaseObject;
import gridwhack.event.IEventListener;
import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.character.attack.BattleScenario;
import gridwhack.gameobject.character.effect.event.CharacterEffectEvent;
import gridwhack.gameobject.character.effect.event.ICharacterEffectApplyListener;
import gridwhack.gameobject.character.effect.event.ICharacterEffectFadeListener;
import gridwhack.gameobject.character.event.*;
import gridwhack.gameobject.grid.Grid;
import gridwhack.gameobject.unit.Unit;
import gridwhack.gameobject.character.effect.CharacterEffect;
import gridwhack.util.Vector2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Character class.
 * All characters must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Character extends Unit implements ICharacterEffectApplyListener, ICharacterEffectFadeListener
{
	public static enum CharacterType implements IGameObjectType
	{
		PLAYER,

		IMP,
		//KOBOLD,
		//ORC,
		SKELETON,
	}

	// ----------
	// Properties
	// ----------

	protected static final int CRITICAL_MULTIPLIER = 2;

	protected String name;
	protected int level = 1; // characters are level 1 by default.
	protected int currentHealth;
	protected int maximumHealth;
	protected int minimumDamage;
	protected int maximumDamage;
	protected int attackCooldown = 0; // milliseconds
	protected int movementCooldown = 0; // milliseconds
	protected long nextAttackTime;
	protected long nextMoveTime;
	protected volatile boolean dead = false; // characters are obviously not dead by default
	protected Character killedBy;
	protected Map<CharacterEffect.Type, CharacterEffect> effects;

	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 */
	public Character()
	{
		super();

		// Calculate the next time the character can attack.
		nextAttackTime = System.currentTimeMillis() + getAttackCooldown();

		// Calculate the next time the character can move.
		nextMoveTime = System.currentTimeMillis() + getMovementCooldown();

		// Initialize the map for buffs and debuffs.
		effects = new HashMap<CharacterEffect.Type, CharacterEffect>();
	}

	/**
	 * Marks the character as spawned.
	 */
	public void markSpawned()
	{
		// Let all listeners know that this character has gained health.
		fireCharacterEvent(new CharacterEvent(CharacterEvent.Type.SPAWN, this));
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
		if (isAttackAllowed())
		{
			BattleScenario scenario = new BattleScenario(this, target);
			scenario.start();
		}
	}

	/**
	 * Returns whether the character is allowed to attack.
	 * @return whether attacking is allowed.
	 */
	public boolean isAttackAllowed()
	{
		long attackCooldown = getAttackCooldown();

		// Check if the character may attack.
		if (attackCooldown > 0 && nextAttackTime < System.currentTimeMillis())
		{
			// calculate the next time the character can engage.
			nextAttackTime += attackCooldown;
			return true;
		}
		
		// Character may not attack yet.
		return false;
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
		fireCharacterEvent(new CharacterEvent(CharacterEvent.Type.HEALTHGAIN, this));
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
		fireCharacterEvent(new CharacterEvent(CharacterEvent.Type.HEALTHLOSS, this));

		// Mark the character dead if its health is reduced to zero.
		if (currentHealth <= 0)
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
		fireCharacterEvent(new CharacterEvent(CharacterEvent.Type.DEATH, this));

		// Mark this character to be removed.
		setRemoved(true);
	}

	/**
	 * Actions to be taken when the character moves.
	 * @param direction the direction to move.
	 */
	public void move(Directions direction)
	{
		if (direction == Directions.LEFT)
		{
			grid.moveUnit(-1, 0, this);
		}
		else if (direction == Directions.RIGHT)
		{
			grid.moveUnit(1, 0, this);
		}
		else if (direction == Directions.UP)
		{
			grid.moveUnit(0, -1, this);
		}
		else if(direction == Directions.DOWN)
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
		if (movementCooldown > 0 && nextMoveTime < System.currentTimeMillis())
		{
			// Calculate the next time the character can move.
			nextMoveTime += movementCooldown;
			return true;
		}

		// Character may not move at this time.
		return false;
	}

	/**
	 * Mark this character to have moved.
	 */
	public synchronized void markMoved()
	{
		// Let all listeners know that the character has moved.
		fireCharacterEvent(new CharacterEvent(CharacterEvent.Type.MOVE, this));
	}

	/**
	 * Adds a character effect to this character.
	 * @param type the effect type.
	 * @param effect the effect.
	 */
	public synchronized void addEffect(CharacterEffect.Type type, CharacterEffect effect)
	{
		// Make sure that the character does not already have the effect.
		if (!hasEffect(type))
		{
			effects.put(type, effect);
		}
	}

	/**
	 * Returns whether this character is affected by a specific character effect.
	 * @param type the effect type.
	 * @return whether this character is affected by the effect.
	 */
	public synchronized boolean hasEffect(CharacterEffect.Type type)
	{
		return effects.containsKey(type);
	}

	/**
	 * Removes a character effect from this character.
	 * @param type the effect type.
	 */
	public synchronized void removeEffect(CharacterEffect.Type type)
	{
		// Make sure the character has the effect.
		if (hasEffect(type))
		{
			effects.remove(type);
		}
	}

	/**
	 * Fires an event for this character.
	 * @param e the event.
	 */
	private synchronized void fireCharacterEvent(CharacterEvent e)
	{
		// Loop through the listeners and notify them.
		for( IEventListener listener : getListeners() )
		{
			// Make sure we only notify character listeners.
			if( listener instanceof ICharacterListener )
			{
				switch( (CharacterEvent.Type) e.getType() )
				{
					// Character has died.
					case DEATH:
						if( listener instanceof ICharacterDeathListener )
						{
							( (ICharacterDeathListener) listener ).onCharacterDeath(e);
						}
						break;

					// Character has gained health.
					case HEALTHGAIN:
						if( listener instanceof ICharacterHealthListener )
						{
							( (ICharacterHealthListener) listener ).onCharacterHealthGain(e);
						}
						break;

					// Character has lost health.
					case HEALTHLOSS:
						if( listener instanceof ICharacterHealthListener )
						{
							( (ICharacterHealthListener) listener ).onCharacterHealthLoss(e);
						}
						break;

					// Character has moved.
					case MOVE:
						if( listener instanceof ICharacterMoveListener )
						{
							( (ICharacterMoveListener) listener ).onCharacterMove(e);
						}
						break;

					// Character has spawned.
					case SPAWN:
						if( listener instanceof ICharacterSpawnListener )
						{
							( (ICharacterSpawnListener) listener ).onCharacterSpawn(e);
						}
						break;

					// Unknown event.
					default:
						System.out.println("Failed to fire character event, type '" + e.getType() + "' is invalid!");
				}
			}
		}
	}

	// --------------
	// Event handlers
	// --------------

	/**
	 * Actions to be taken when a character effect starts affecting its subject.
	 * @param e the event.
	 */
	public synchronized void onCharacterEffectApply(CharacterEffectEvent e)
	{
		CharacterEffect effect = (CharacterEffect) e.getSource();
		addEffect(effect.getType(), effect);
	}

	/**
	 * Actions to be taken when a character effect fades.
	 * @param e the event.
	 */
	public synchronized void onCharacterEffectFade(CharacterEffectEvent e)
	{
		CharacterEffect effect = (CharacterEffect) e.getSource();
		removeEffect(effect.getType());
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Updates this object.
	 * @param parent The parent object.
	 */
	@Override
	public void update(BaseObject parent)
	{
		super.update(parent);

		// Make sure this character is not dead.
		if (!dead)
		{
			if (!effects.isEmpty())
			{
				for (CharacterEffect effect : this.effects.values())
				{
					effect.update(this);
				}
			}
		}
	}

	/**
	 * Draws this object.
	 * @param g The graphics context.
	 */
	@Override
	public void draw(Graphics2D g)
	{
		// Make sure this character is not dead.
		if (!dead)
		{
			super.draw(g);

			if (!effects.isEmpty())
			{
				for (CharacterEffect effect : this.effects.values())
				{
					effect.draw(g);
				}
			}

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

	// -------------------
	// Getters and setters
	// -------------------

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
	 * Returns whether the target character is hostile.
	 * @param target the target character.
	 * @return whether the target is hostile.
	 */
	public abstract boolean isHostile(Character target);
}
