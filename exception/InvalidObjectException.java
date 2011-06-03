package gridwhack.exception;

/**
 * Invalid object exception class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class InvalidObjectException extends Exception
{
	/**
	 * Creates the exception.
	 * @param message The message.
	 */
	public InvalidObjectException(String message)
	{
		super(message);
	}
}
