package gridwhack.exception;

/**
 * Component not found exception class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ComponentNotFoundException extends Exception
{
	/**
	 * Creates the exception.
	 * @param message the message.
	 */
	public ComponentNotFoundException(String message)
	{
		super(message);
	}
}
