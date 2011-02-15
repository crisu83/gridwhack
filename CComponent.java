package gridwhack;

import gridwhack.event.IEventListener;

import java.util.ArrayList;

/**
 * Core component class file.
 * Allows for adding event listeners and firing events.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
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
	public synchronized void addListener(IEventListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Removes an event listener from this component.
	 * @param listener the event listener to remove.
	 */
	public synchronized void removeListener(IEventListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Returns all the listeners.
	 * @return the listeners.
	 */
	public synchronized ArrayList<IEventListener> getListeners()
	{
		return listeners;
	}
}
