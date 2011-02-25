package gridwhack.gui.message;

import java.awt.*;
import java.util.ArrayList;

/**
 * Combat log message box class.
 * Allows for rendering the combat log in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CombatLogBox extends MessageBox
{
	/**
	 * Creates the combat log.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the box width.
	 * @param height the box height.
	 */
	public CombatLogBox(int x, int y, int width, int height)
	{
		super(x, y, width, height, 5);
	}

	/**
	 * Returns the messages in the stream.
	 * @return the messages.
	 */
	public ArrayList<String> getMessages() 
	{
		return CombatLog.getMessages();
	}
}