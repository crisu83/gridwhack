package gridwhack.gui.message;

import java.util.ArrayList;

/**
 * Message log class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class MessageLog
{
	private static MessageStream stream = new MessageStream();

	/**
	 * Adds a message to the message log.
	 * @param message the message.
	 */
	public static void addMessage(String message)
	{
		stream.in(message);
	}

	/**
	 * Returns all the messages in the message log.
	 * @return the messages.
	 */
	public static ArrayList<String> getMessages()
	{
		return stream.out();
	}
}