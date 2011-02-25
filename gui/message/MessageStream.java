package gridwhack.gui.message;

import java.util.ArrayList;

/**
 * Message stream class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class MessageStream 
{
	protected ArrayList<String> stream;
	
	/**
	 * Creates the stream.
	 */
	public MessageStream()
	{
		stream = new ArrayList<String>();
	}
	
	/**
	 * Adds a message to the beginning of the stream.
	 * @param message the message.
	 */
	public void in(String message)
	{
		stream.add(0, message);
	}
	
	/**
	 * @return the stream.
	 */
	public ArrayList<String> out()
	{
		return stream;
	}
}
