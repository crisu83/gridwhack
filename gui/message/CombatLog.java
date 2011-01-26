package gridwhack.gui.message;

import java.util.ArrayList;

public class CombatLog
{
	protected static MessageStream stream = new MessageStream();
	
	public static void addMessage(String message)
	{
		stream.in(message);
	}
	
	public static ArrayList<String> getMessages()
	{
		return stream.out();
	}
}