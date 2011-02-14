package gridwhack.entity;

import java.awt.*;
import java.util.Random;

import gridwhack.CComponent;
import gridwhack.RandomProvider;
import gridwhack.entity.event.EntityEvent;
import gridwhack.entity.event.IEntityListener;
import gridwhack.entity.sprite.CSprite;
import gridwhack.entity.sprite.CSpriteManager;
import gridwhack.event.IEventListener;

/**
 * Core entity base class.
 */
public abstract class CEntity extends CComponent
{	
	protected static final double VELOCITY = 1.0;
	
	protected CSprite sprite;
	protected Random rand;
	protected boolean removed;
	
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
	protected int tx;
	protected int ty;
	
	/**
	 * Constructs the entity.
	 * @param filename the sprite filename.
	 */
	public CEntity(String filename)
	{
		// get the sprite with the sprite manager.
		sprite = CSpriteManager.getSprite(filename);
		
		// get random from the random provider. 
		rand = RandomProvider.getRand();
		
		// the entity is not removed by default.
		removed = false;
	}
	
	/**
	 * @return whether this entity has been removed.
	 */
	public boolean getRemoved()
	{
		return removed;
	}
	
	/**
	 * Marks this entity as removed.
	 */
	public synchronized void markRemoved()
	{
		removed = true;

		// let all listeners know that this entity is removed.
		fireEntityEvent( new EntityEvent(EntityEvent.ENTITY_REMOVE, this) );
	}

	/**
	 * Sets a destination for the entity.
	 * @param tx the target x-coordinates.
	 * @param ty the target y-coordinates.
	 */
	public void setDestination(int tx, int ty)
	{
		if( tx>x )
		{
			vx = VELOCITY;
		}
		if( tx<x )
		{
			vx = -VELOCITY;
		}
		if( ty>y )
		{
			vy = VELOCITY;
		}
		if( ty<y )
		{
			vy = -VELOCITY;
		}
		
		this.tx = tx;
		this.ty = ty;
	}
	
	/**
	 * Updates the entity.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		// Make sure the entity is not removed.
		// Make sure the entity is not removed.
		if( !removed )
		{
			if( x==tx )
			{
				vx = 0;
			}
			if( y==ty )
			{
				vy = 0;
			}
			if( vx>0 )
			{
				x += vx * timePassed;
			}
			if( vy>0 )
			{
				y += vy * timePassed;
			}
		}
	}
	
	/**
	 * Renders the entity.
	 * @param g the 2d graphics object.
	 */
	public void render(Graphics2D g)
	{
		// Make sure the entity is not removed.
		if( !removed )
		{
			sprite.render(g, (int) Math.round(x), (int) Math.round(y));
		}
	}
	
	/**
	 * Fires an entity event.
	 * @param e the event.
	 */
	private synchronized void fireEntityEvent(EntityEvent e)
	{
		for( IEventListener listener : getListeners() )
		{
			// Make sure we only notify entity listeners.
			if( listener instanceof IEntityListener )
			{
				switch( e.getType() )
				{
					// Entity has been removed.
					case EntityEvent.ENTITY_REMOVE:
						( (IEntityListener) listener ).onEntityRemove(e);
						break;
					
					// Unknown event.
					default:
				}
			}
		}
	}

	/**
	 * @return the x-coordinate in pixels.
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * @param x the x-coordinate in pixels.
	 */
	public void setX(double x)
	{
		this.x = x;
	}
	
	/**
	 * @return the y-coordinate in pixels.
	 */
	public double getY()
	{
		return y;
	}
	
	/**
	 * @param y the y-coordinate in pixels.
	 */
	public void setY(double y)
	{
		this.y = y;
	}
	
	/**
	 * @return the width of the entity.
	 */
	public int getWidth()
	{
		return sprite.getWidth();
	}
	
	/**
	 * @return the height of the entity.
	 */
	public int getHeight()
	{
		return sprite.getHeight();
	}
}
