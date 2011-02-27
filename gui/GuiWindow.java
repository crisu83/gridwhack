package gridwhack.gui;

import java.awt.*;

/**
 * Gui window base class file.
 * Allows for adding windows to the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GuiWindow extends GuiPanel
{
	/**
	 * Creates the window.
	 * @param x the panel x-coordinate.
	 * @param y the panel y-coordinate.
	 * @param width the window width.
	 * @param height the window height.
	 */
	public GuiWindow(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}
}
