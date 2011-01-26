package gridwhack;

import java.util.EventListener;

/**
 * Interface that all event listeners must implement.
 */
public interface IEventListener extends EventListener 
{
	/**
	 * Handles incoming events.
	 * @param e the event.
	 */
	public void handleEvent(CEvent e);
}
