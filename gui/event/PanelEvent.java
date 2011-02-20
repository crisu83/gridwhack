package gridwhack.gui.event;

import gridwhack.event.CEvent;

/**
 * Panel event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class PanelEvent extends CEvent
{
	public static final int PANEL_REMOVE = 200;

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public PanelEvent(int type, Object source)
	{
		super(type, source);
	}
}
