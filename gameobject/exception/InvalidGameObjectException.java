package gridwhack.gameobject.exception;

/**
 * Invalid game object exception class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class InvalidGameObjectException extends Exception
{
	/**
	 * Creates the exception.
	 * @param message The message.
	 */
	public InvalidGameObjectException(String message)
	{
		super(message);
	}
}
