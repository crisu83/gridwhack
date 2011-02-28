package gridwhack.gui;

import java.awt.*;

/**
 * Gui panel class file.
 * Panels are simple gui elements which holds child elements.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GuiPanel extends GuiElement
{
	public static enum Type {
		PANEL_PLAYER_INFO,
		PANEL_MESSAGELOG,
		PANEL_COMBATLOG,
		WINDOW_PLAYER_LOOT,
	};

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

		Color backgroundColor = getBackgroundColor();
		if( backgroundColor==null )
		{
			// Panels are dark dark gray by default.
			setBackgroundColor( new Color(20, 20, 20) );
		}
	}
}
