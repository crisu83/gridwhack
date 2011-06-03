package gridwhack.util;

import gridwhack.base.BaseObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Sorted array list class file.
 * This class provides funcationality for sorted array lists.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class SortedArrayList<E extends BaseObject> extends ArrayList<E>
{
	// ----------
	// Properties
	// ----------

	private Comparator comparator;
	private boolean sorted = false;

	// -------
	// Methods
	// -------

	/**
	 * Creates the array.
	 */
	public SortedArrayList()
	{
		super();
	}

	/**
	 * Creates the array with a predefined size.
	 * @param capacity the size of the array.
	 */
	public SortedArrayList(int capacity)
	{
		super(capacity);
	}

	/**
	 * Sorts this array.
	 */
	// TODO: Use another algorithm for sorting to improve performance.
	public void sort()
	{
		if (!sorted)
		{
			if (comparator != null)
			{
				//noinspection unchecked
				Arrays.sort(toArray(), comparator);
			}
			else
			{
				Arrays.sort(toArray(), 0, size());
			}

			sorted = true;
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param comparator The comparator to use for sorting.
	 */
	public void setComparator(Comparator comparator)
	{
		this.comparator = comparator;
		sorted = false;
	}
}
