package gridwhack.gui;

import java.awt.*;

/**
 * Gui panel class.
 * Panels are simple gui elements which holds child elements.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GuiPanel extends GuiElement
{
	public static enum GuiPanelType {
		PANEL_PLAYER_INFO,
		PANEL_MESSAGELOG,
		PANEL_COMBATLOG,
		WINDOW_PLAYER_LOOT,
	};

	/**
	 * Creates the panel.
	 * @param x The panel x-coordinate.
	 * @param y The panel y-coordinate.
	 * @param width The panel width.
	 * @param height The panel height.
	 */
	public GuiPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);

		Color backgroundColor = getBackgroundColor();

		if (backgroundColor == null)
		{
			setBackgroundColor(new Color(20, 20, 20)); // dark gray by default
		}
	}
}
