package gridwhack.entity.character.effect;

import gridwhack.CComponent;
import gridwhack.entity.character.Character;
import gridwhack.entity.character.effect.event.*;
import gridwhack.gui.message.MessageLog;
import gridwhack.event.IEventListener;

import java.awt.*;

/**
 * Character effect class file.
 * All character effects must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class CharacterEffect extends CComponent
{
	// Character effect types.
	public static enum Type {
		DOUBLE_DAMAGE,
	};

	private String name;
	private Type type;
	private int duration; // milliseconds
	private Character subject;

	private long startTime;
	private long endTime;

	private int tickCooldown = 0; // milliseconds
	private long nextTickTime;

	private volatile boolean active = true; // effects are active by default

	/**
	 * Creates the character effect.
	 * @param name the name of the effect.
	 * @param type the effect type.
	 * @param duration the duration fo the effect.
	 * @param subject the subject to apply the effect to.
	 */
	public CharacterEffect(String name, Type type, int duration, Character subject)
	{
		this.name = name;
		this.type = type;
		this.duration = duration;
		this.subject = subject;

		addListener(subject); // let the subject listen to this effect

		// Calculate the start- and end type.
		startTime = System.currentTimeMillis();
		endTime = startTime + duration;

		// Calculate the next time the effect should tick.
		nextTickTime = startTime + getTickCooldown();
	}

	/**
	 * Initializes the character effect.
	 */
	public void init()
	{
		apply(); // apply this effect to the subject

		// Let all listeners know that the effect has been applied.
		fireCharacterEffectEvent(new CharacterEffectEvent(CharacterEffectEvent.Type.APPLY, this));
	}

	/**
	 * Marks the character effect to have ticked.
	 */
	public void markTicked()
	{
		tick(); // let the effect tick.

		// Let all listeners know that the effect has ticked.
		fireCharacterEffectEvent(new CharacterEffectEvent(CharacterEffectEvent.Type.TICK, this));
	}

	/**
	 * Marks the character effect faded.
	 */
	public void markFaded()
	{

		fade(); // fade this effect from the subject

		// Let all listeners know that the effect has faded.
		fireCharacterEffectEvent(new CharacterEffectEvent(CharacterEffectEvent.Type.FADE, this));

		MessageLog.addMessage(getName() + " fades.");
	}

	/**
	 * Updates the character effect.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		// Make sure that the effect is active.
		if( active )
		{
			long nowTime = System.currentTimeMillis();

			// Check whether the effect should tick.
			if( tickCooldown>0 && nextTickTime<nowTime )
			{
				markTicked();
				nextTickTime += tickCooldown;
			}

			// Check if we should fade this effect.
			if( nowTime>=endTime )
			{
				active = false; // effect is no longer active
				markFaded();
			}
		}
	}

	/**
	 * Fires an event for this character effect.
	 * @param e the event.
	 */
	private synchronized void fireCharacterEffectEvent(CharacterEffectEvent e)
	{
		// Loop through the listeners and notify them.
		for( IEventListener listener : getListeners() )
		{
			// Make sure we only notify character effect listeners.
			if( listener instanceof ICharacterEffectListener )
			{
				switch( (CharacterEffectEvent.Type) e.getType() )
				{
					// Character effect has been applied.
					case APPLY:
						if( listener instanceof ICharacterEffectApplyListener )
						{
							( (ICharacterEffectApplyListener) listener ).onCharacterEffectApply(e);
						}
						break;

					// Character effect has ticked.
					case TICK:
						if( listener instanceof ICharacterEffectTickListener )
						{
							( (ICharacterEffectTickListener) listener ).onCharacterEffectTick(e);
						}
						break;

					// Character effect has faded.
					case FADE:
						if( listener instanceof ICharacterEffectFadeListener )
						{
							( (ICharacterEffectFadeListener) listener ).onCharacterEffectFade(e);
						}
						break;

					// Unknown event.
					default:
						System.out.println("Failed to fire character effect event, type '" + e.getType() + "' is invalid!");
				}
			}
		}
	}

	/**
	 * Returns the name of this character effect.
	 * @return the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the type of this character effect.
	 * @return the type.
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * Returns the duration of this character effect.
	 * @return the duration.
	 */
	public int getDuration()
	{
		return duration;
	}

	/**
	 * Returns the subject that is affected by this character effect.
	 * @return the subject.
	 */
	public Character getSubject()
	{
		return subject;
	}

	/**
	 * Sets the tick cooldown for this character effect.
	 * @param cooldown the cooldown in milliseconds.
	 */
	public void setTickCooldown(int cooldown)
	{
		this.tickCooldown = cooldown;
	}

	/**
	 * Returns the tick cooldown for this character effect.
	 * @return the cooldown in milliseconds.
	 */
	public int getTickCooldown()
	{
		return tickCooldown;
	}

	/**
	 * Sets the character effect active/inactive.
	 * @param active whether the effect should be active.
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}

	/**
	 * Actions to take when the character effect is applied.
	 */
	public abstract void apply();

	/**
	 * Actions to take when the character effect ticks.
	 */
	public abstract void tick();

	/**
	 * Actions to take when the character effect faces.
	 */
	public abstract void fade();

	/**
	 * Renders the character effect.
	 * @param g the graphics context.
	 */
	public abstract void render(Graphics2D g);
}
