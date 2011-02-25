package gridwhack.gui.message;

import java.util.ArrayList;

/**
 * Combat log class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CombatLog
{
	private static MessageStream stream = new MessageStream();

	/**
	 * Adds a message to the combat log.
	 * @param message the message.
	 */
	public static void addMessage(String message)
	{
		stream.in(message);
	}

	/**
	 * Returns all the messages in the combat log.
	 * @return the messages.
	 */
	public static ArrayList<String> getMessages()
	{
		return stream.out();
	}
}