package gridwhack.base;

import gridwhack.util.SortedArrayList;

/**
 * Base collection class.
 * All the collections should be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class BaseCollection extends BaseObject
{
	// ----------
	// Properties
	// ----------

	private SortedArrayList<BaseObject> objects;
	private SortedArrayList<BaseObject> additions;
	private SortedArrayList<BaseObject> removals;

	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 */
	public BaseCollection()
	{
		super();
		
		objects = new SortedArrayList<BaseObject>();
		additions = new SortedArrayList<BaseObject>();
		removals = new SortedArrayList<BaseObject>();
	}

	/**
	 * Adds an object to this collection.
	 * @param object The object to add.
	 */
	public void add(BaseObject object)
	{
		additions.add(object);
	}

	/**
	 * Returns an object in this collection.
	 * @param index The index of the object.
	 * @return The object.
	 */
	public BaseObject get(int index)
	{
		return objects.get(index);
	}

	/**
	 * Removes an object from this collection.
	 * @param object The object to remove.
	 */
	public void remove(BaseObject object)
	{
		removals.add(object);
	}

	/**
	 * Returns the size of this collection.
	 * @return The size.
	 */
	public int getSize()
	{
		return objects.size();
	}

	/**
	 * @return Whether this collection is empty.
	 */
	public boolean isEmpty()
	{
		return objects.isEmpty() && additions.isEmpty();
	}

	/**
	 * Applies the pending additions and removals to this collections.
	 */
	public void applyChanges()
	{
		final int additionsCount = additions.size();

		if (additionsCount > 0)
		{
			for (int i = 0; i < additionsCount; i++)
			{
				objects.add( additions.get(i) );
			}

			additions.clear();
		}

		final int removalsCount = removals.size();

		if (removalsCount > 0)
		{
			for (int i = 0; i < removalsCount; i++)
			{
				objects.remove( removals.get(i) );
			}

			removals.clear();
		}
	}

	/**
	 * Updates this object.
	 * @param parent The parent object.
	 */
	public void update(BaseObject parent)
	{
		applyChanges();

		final int objectsCount = objects.size();

		if (objectsCount > 0)
		{
			for (int i = 0; i < objectsCount; i++)
			{
				objects.get(i).update(this);
			}
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The objects in this collection.
	 */
	public SortedArrayList<BaseObject> getObjects()
	{
		return objects;
	}

	/**
	 * @return The objects pending to be added.
	 */
	public SortedArrayList<BaseObject> getAdditions()
	{
		return additions;
	}
}
