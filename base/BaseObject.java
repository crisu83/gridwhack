package gridwhack.base;

/**
 * Base object class.
 * All objects should be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
abstract public class BaseObject
{
	/**
	 * Creates the object.
	 */
	public BaseObject()
	{
		// Base object does nothing.
	}

	/**
	 * Updates this object
	 * @param parent The parent object.
	 */
	public void update(BaseObject parent)
	{
		// Base object does nothing.
	}
}
