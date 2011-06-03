package gridwhack.event;

import gridwhack.base.BaseObject;

import java.util.ArrayList;

/**
 * EventSource class file.
 * Allows for adding event listeners and firing events.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
abstract public class EventSource extends BaseObject
{
	// ----------
	// Properties
	// ----------

	private ArrayList<IEventListener> listeners;

	// -------
	// Methods
	// -------
	
	/**
	 * Creates the object.
	 */
	public EventSource()
	{
		super();

		listeners = new ArrayList<IEventListener>();
	}
	
	/**
	 * Adds an event listener to this object.
	 * @param listener The event listener to add.
	 */
	public synchronized void addListener(IEventListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Removes an event listener from this object.
	 * @param listener The event listener to remove.
	 */
	public synchronized void removeListener(IEventListener listener)
	{
		listeners.remove(listener);
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Returns all the listeners.
	 * @return The listeners.
	 */
	public synchronized ArrayList<IEventListener> getListeners()
	{
		return listeners;
	}
}
