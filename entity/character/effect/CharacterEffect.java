package gridwhack.entity.character.effect;

import gridwhack.CComponent;
import gridwhack.entity.character.Character;
import gridwhack.gui.message.MessageLog;
import gridwhack.entity.character.effect.event.CharacterEffectEvent;
import gridwhack.entity.character.effect.event.ICharacterEffectListener;
import gridwhack.event.IEventListener;

import java.awt.*;

public abstract class CharacterEffect extends CComponent
{
	public static enum Type {
		DOUBLE_DAMAGE,
	};

	private String name;
	private CharacterEffect.Type type;
	private long startTime;
	private long endTime;
	private int duration;

	private volatile boolean active = true; // effects are active by default.

	private Character subject;

	public CharacterEffect(String name, CharacterEffect.Type type, int duration, Character subject)
	{
		this.name = name;
		this.type = type;
		this.duration = duration;
		this.subject = subject;

		startTime = System.currentTimeMillis();
		endTime = startTime + (duration * 1000);
	}

	public void init()
	{
		// Add this effect to the subject.
		subject.addEffect(getType(), this);

		// Let this effect affect the subject.
		affect();
	}

	public void markRemoved()
	{
		// Fade this effect from the subject.
		fade();

		// Remove this effect from the subject
		subject.removeEffect(getType());

		MessageLog.addMessage(getName() + " fades.");
	}

	public void update(long timePassed)
	{
		if( active )
		{
			long nowTime = System.currentTimeMillis();

			if( nowTime>=endTime )
			{
				active = false;

				markRemoved();
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
			// Make sure we only notify character listeners.
			if( listener instanceof ICharacterEffectListener)
			{
				switch( (CharacterEffectEvent.Type) e.getType() )
				{
					// Character effect has been removed.
					case FADE:
						( (ICharacterEffectListener) listener ).onCharacterEffectRemove(e);
						break;

					// Unknown event.
					default:
				}
			}
		}
	}

	public void render(Graphics2D g) {}

	public String getName()
	{
		return name;
	}

	public Type getType()
	{
		return type;
	}

	public int getDuration()
	{
		return duration;
	}

	public Character getSubject()
	{
		return subject;
	}

	public abstract void affect();

	public abstract void fade();
}
