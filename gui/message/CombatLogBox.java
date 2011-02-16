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
	 * Constructs the combat log.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the box width.
	 * @param height the box height.
	 * @param font the font.
	 * @param textColor the text bgColor.
	 */
	public CombatLogBox(int x, int y, int width, int height, Font font, Color textColor)
	{
		super(x, y, width, height, font, textColor, 5);
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