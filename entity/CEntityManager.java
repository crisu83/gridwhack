package gridwhack.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.entity.event.EntityEvent;
import gridwhack.entity.event.IEntityListener;

public class CEntityManager implements IEntityListener 
{
	private ArrayList<CEntity> entities;
	
	/**
	 * Constructs the entity manager.
	 */
	public CEntityManager()
	{
		entities = new ArrayList<CEntity>();
	}
	
	/**
	 * Returns a specific entity.  
	 * @param index the index of the entity.
	 * @return the entity.
	 */
	public CEntity getEntity(int index)
	{
		return (CEntity) entities.get(index);
	}
	
	/**
	 * Adds an entity to the manager.
	 * @param entity the entity to add.
	 */
	public synchronized void addEntity(CEntity entity)
	{
		entity.addListener(this);
		entities.add(entity);
	}
	
	/**
	 * Removes an entity from the manager.
	 * @param entity the entity to remove.
	 */
	public synchronized void removeEntity(CEntity entity)
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
	 * Actions to be taken when an entity is removed.
	 * @param e the event.
	 */
	public synchronized void onEntityRemove(EntityEvent e) 
	{
		removeEntity( (CEntity) e.getSource() );
	}
}
