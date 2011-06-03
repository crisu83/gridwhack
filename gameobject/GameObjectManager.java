package gridwhack.gameobject;

import gridwhack.base.BaseObject;
import gridwhack.base.BaseCollection;
import gridwhack.gameobject.event.GameObjectEvent;
import gridwhack.gameobject.event.IGameObjectRemoveListener;

import java.awt.Graphics2D;

/**
 * Game object manager class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class GameObjectManager extends BaseObject implements IGameObjectRemoveListener
{
	// ----------
	// Properties
	// ----------

	private BaseCollection objects;

	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 */
	public GameObjectManager()
	{
		super();
		
		objects = new BaseCollection();
	}

	/**
	 * Adds a game object to this manager.
	 * @param object The game object to add.
	 */
	public void add(GameObject object)
	{
		object.addListener(this);
		objects.add(object);
	}

	/**
	 * Removes a game object from this manager.
	 * @param object The game object to remove.
	 */
	public void remove(GameObject object)
	{
		//object.removeListener(this);
		objects.remove(object);
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Updates the game objects managed by this manager.
	 * @param parent The parent object.
	 */
	@Override
	public void update(BaseObject parent)
	{
		objects.update(this);
	}

	/**
	 * Draws the game objects managed by this manager.
	 * @param g The graphics context.
	 */
	public void draw(Graphics2D g)
	{
		final int objectsCount = objects.getSize();

		if (objectsCount > 0)
		{
			for (int i = 0; i < objectsCount; i++)
			{
				GameObject object = (GameObject) objects.get(i);

				if (object instanceof DrawableGameObject)
				{
					((DrawableGameObject) object).draw(g);
				}
			}
		}
	}

	// --------------
	// Event handlers
	// --------------

	/**
	 * Actions to be taken when a game object is removed.
	 * @param event the event.
	 */
	public void onGameObjectRemove(GameObjectEvent event)
	{
		remove((GameObject) event.getSource());
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The objects in this collection.
	 */
	public BaseCollection getObjects()
	{
		return objects;
	}
}
