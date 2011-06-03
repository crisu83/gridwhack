package gridwhack.base;

/**
 * Sorted collection class file.
 * This class provides functionality for sorted collections.
 * All sorted collections should be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class SortedCollection extends BaseCollection
{
	// ----------
	// Properties
	// ----------

	private boolean sorted = false;

	// -------
	// Methods
	// -------

	public SortedCollection()
	{
		super();
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Adds an object to this collection.
	 * @param object The object to add.
	 */
	@Override
	public void add(BaseObject object)
	{
		super.add(object);
		sorted = false;
	}

	/**
	 * Applies the pending additions and removals to this collections.
	 */
	@Override
	public void applyChanges()
	{
		super.applyChanges();

		if (!sorted)
		{
			getObjects().sort();
			sorted = true;
		}
	}
}
