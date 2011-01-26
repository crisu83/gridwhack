package gridwhack.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entity manager class.
 * Provides functionality to manage entities.
 */
public class CEntityManager
{
	public static enum LayerType {
		TILE,
		ITEM,
		UNIT,
	}
	
	private ArrayList<CEntityLayer> layers;
	
	/**
	 * Constructs the entity manager.
	 */
	public CEntityManager()
	{
		layers = new ArrayList<CEntityLayer>();
		
		// we need to initialize all the entity layers.
		for( LayerType type : LayerType.values() )
		{
			layers.add(new CEntityLayer());
		}
	}
	
	/**
	 * Returns a specific entity.  
	 * @param index the index of the entity to get.
	 * @return the entity.
	 */
	public CEntity getEntity(int index, int type)
	{
		return (CEntity)layers.get(type).getEntity(index);
	}
	
	/**
	 * Adds an entity to the manager.
	 * @param entity the entity to add.
	 */
	public synchronized void addEntity(CEntity entity, int type)
	{
		layers.get(type).addEntity(entity);
	}
	
	/**
	 * Removes an entity from the manager.
	 * @param entity the entity to remove.
	 */
	public void removeEntity(CEntity entity, int type)
	{
		layers.get(type).removeEntity(entity);
	}
	
	/**
	 * @return all the entities on a specific layer.
	 */
	public ArrayList<CEntity> getEntities(int type)
	{
		return layers.get(type).getEntities();
	}
	
	/**
	 * Removes all entities.
	 */
	public void empty()
	{
		// loop through all the entity layers and empty them.
		for( CEntityLayer layer : layers )
		{
			layer.empty();
		}
	}
	
	/**
	 * Updates the position of all entities
	 * accross the different layers.
	 * @param timePassed the time that has passed.
	 */
	public synchronized void update(long timePassed)
	{
		// loop through all the entity layers and update them.
		for( CEntityLayer layer : layers ) 
		{
			layer.update(timePassed);
		}
	}
	
	/**
	 * Renders the entity layers.
	 * @param g the 2D graphics object.
	 */
	public synchronized void render(Graphics2D g)
	{
		// loop through all the entity layers and render them.
		for( CEntityLayer layer : layers ) 
		{
			layer.render(g);
		}
	}
}
