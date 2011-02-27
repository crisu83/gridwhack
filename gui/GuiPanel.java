package gridwhack.gui;

import java.awt.*;

/**
 * Gui panel class file.
 * Panels are simple gui elements which holds child elements.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GuiPanel extends GuiElement
{
	/**
	 * Creates the panel.
	 * @param x the panel x-coordinate.
	 * @param y the panel y-coordinate.
	 * @param width the panel width.
	 * @param height the panel height.
	 */
	public GuiPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);

		// Panels are dark dark gray by default.
		backgroundColor = backgroundColor==null ? new Color(20, 20, 20) : backgroundColor;
	}
}
