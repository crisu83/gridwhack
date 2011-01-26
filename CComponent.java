package gridwhack;

import java.util.ArrayList;

/**
 * Base component class.
 * Provides functionality for adding event listeners and firing events.
 */
public class CComponent 
{
	private ArrayList<IEventListener> listeners;
	
	/**
	 * Constructs the component.
	 */
	public CComponent()
	{
		// initialize the list of event listeners.
		listeners = new ArrayList<IEventListener>();
	}
	
	/**
	 * Adds an event listener to this component.
	 * @param listener the event listener to add.
	 */
	public void addListener(IEventListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Removes an event listener from this component.
	 * @param listener the event listener to remove.
	 */
	public void removeListener(IEventListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Fires an event from this component. 
	 * All event listeners will be notified.
	 * @param e the event.
	 */
	public void fireEvent(CEvent e)
	{
		// loop through and pass the event to all event listeners.
		for( int i=0, length=listeners.size(); i<length; i++ )
		{
			((IEventListener)listeners.get(i)).handleEvent(e);
		}
	}
}
