package gridwhack.gui.message;

import java.util.ArrayList;

/**
 * Combat log message box class.
 * Provides functionality for rendering the combat log in the gui.
 */
public class CombatLogBox extends MessageBox
{
	/**
	 * Constructs the combat log.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the combat log width.
	 * @param height the combat log height.
	 */
	public CombatLogBox(int x, int y, int width, int height) 
	{
		super(x, y, width, height, 5, 11);
	}

	/**
	 * Returns the latest combat log messages.
	 */
	public ArrayList<String> getMessages() 
	{
		return CombatLog.getMessages();
	}
}