package gridwhack.gameobject;

import gridwhack.base.BaseObject;
import gridwhack.event.EventSource;
import gridwhack.event.IEventListener;
import gridwhack.gameobject.character.event.ICharacterListener;
import gridwhack.gameobject.event.GameObjectEvent;
import gridwhack.gameobject.event.IGameObjectRemoveListener;
import gridwhack.util.Vector2;

/**
 * Game object class file.
 * All game objects must be extened from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GameObject extends EventSource
{
	public static enum GameObjectType implements IGameObjectType
	{
		CAMERA,
		DUNGEON_MAP,
		DUNGEON_FEATURE,
		GRID,
		MAP,
	}

	// ----------
	// Properties
	// ----------

	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 dimension;
	protected volatile boolean removed;

	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 */
	public GameObject()
	{
		super();

		position = new Vector2();
		velocity = new Vector2();
		dimension = new Vector2();
		removed = false; // game objects are naturally not removed by default
	}

	/**
	 * Initializes the object.
	 */
	public void init()
	{
		// Base object does nothing.
	}

	public synchronized void fireGameObjectRemoveEvent(GameObjectEvent event)
	{
		for (IEventListener listener : getListeners())
		{
			if (listener instanceof IGameObjectRemoveListener)
			{
				((IGameObjectRemoveListener) listener).onGameObjectRemove(event);
			}
		}
	}

	/**
	 * @return The x-position of this object.
	 */
	public int getX()
	{
		return (int) position.x;
	}

	/**
	 * @return The y-position of this object.
	 */
	public int getY()
	{
		return (int) position.y;
	}

	/**
	 * @return The width of this object.
	 */
	public int getWidth()
	{
		return (int) dimension.x;
	}

	/**
	 *
	 * @return The height of this object.
	 */
	public int getHeight()
	{
		return (int) dimension.y;
	}

	// ----------------
	// Overridden methods
	// ----------------

	/**
	 * Updates this object.
	 * @param parent The parent object.
	 */
	@Override
	public void update(BaseObject parent)
	{
		if (!removed)
		{
			position.add(velocity);
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The current position.
	 */
	public Vector2 getPosition()
	{
		return position;
	}

	/**
	 * @param position The new position.
	 */
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	/**
	 * @return The current dimension.
	 */
	public Vector2 getDimension()
	{
		return dimension;
	}

	/**
	 * @param dimension The new dimension.
	 */
	public void setDimension(Vector2 dimension)
	{
		this.dimension = dimension;
	}

	/**
	 * @return The current velocity.
	 */
	public Vector2 getVelocity()
	{
		return velocity;
	}

	/**
	 * @param velocity The new velocity.
	 */
	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity;
	}

	/**
	 * @return Whether this game object is removed.
	 */
	public boolean isRemoved()
	{
		return removed;
	}

	/**
	 * @param removed Whether this game object is removed.
	 */
	public void setRemoved(boolean removed)
	{
		this.removed = removed;

		// Let the listeners know that this game object has been removed.
		fireGameObjectRemoveEvent(new GameObjectEvent(this));
	}
}
