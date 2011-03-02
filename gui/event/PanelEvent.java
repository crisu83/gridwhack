package gridwhack.gui.event;

import gridwhack.event.CEvent;

/**
 * Panel event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class PanelEvent extends CEvent
{
	private Type type;

	// Panel event types.
	public static enum Type {
		REMOVE,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public PanelEvent(Type type, Object source)
	{
		super(source);

		this.type = type;
	}

	/**
	 * Returns the event type.
	 * @return the type.
	 */
	public Type getType()
	{
		return type;
	}
}
