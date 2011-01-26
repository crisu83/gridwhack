package gridwhack.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.CEvent;
import gridwhack.IEventListener;

public class CEntityLayer implements IEventListener 
{
	private ArrayList<CEntity> entities;
	
	/**
	 * Constructs the entity layer.
	 */
	public CEntityLayer()
	{
		entities = new ArrayList<CEntity>();
	}
	
	/**
	 * Returns a specific entity.  
	 * @param index the index of the entity to get.
	 * @return the entity.
	 */
	public CEntity getEntity(int index)
	{
		return (CEntity)entities.get(index);
	}
	
	/**
	 * Adds an entity to the manager.
	 * @param entity the entity to add.
	 */
	public void addEntity(CEntity entity)
	{
		entity.addListener(this);
		entities.add(entity);
	}
	
	/**
	 * Removes an entity from the manager.
	 * @param entity the entity to remove.
	 */
	public void removeEntity(CEntity entity)
	{
		entity.removeListener(this);
		entities.remove(entity);
	}
	
	/**
	 * @return all the entities in the manager.
	 */
	public ArrayList<CEntity> getEntities()
	{
		return entities;
	}
	
	/**
	 * Removes all entities.
	 */
	public void empty()
	{
		entities.clear();
	}
	
	/**
	 * Updates the position of all entities.
	 * @param timePassed the time that has passed.
	 */
	public synchronized void update(long timePassed)
	{
		// loop through all the entities and update them.
		for( CEntity entity : entities )
		{
			entity.update(timePassed);
		}
	}
	
	/**
	 * Renders the entities.
	 * @param g the 2D graphics object.
	 */
	public synchronized void render(Graphics2D g)
	{
		// loop through all the entities and render them.
		for( CEntity entity : entities )
		{
			entity.render(g);
		}
	}

	/**
	 * Handles incoming events.
	 * @param e the event.
	 */
	public void handleEvent(CEvent e) 
	{
		String type = e.getType();
		Object source = e.getSource();
		
		// entity event handling.
		if( source instanceof CEntity )
		{
			// actions to be taken when an entity is removed.
			if( type=="remove" )
			{
				// mark the removed entity to be removed.
				removeEntity((CEntity)source);
			}
		}
	}
}
