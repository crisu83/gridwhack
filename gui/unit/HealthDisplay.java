package gridwhack.gui.unit;

import gridwhack.grid.GridUnit;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;

/**
 * Health display class file.
 * Allows for representing unit health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthDisplay extends GuiElement 
{
	/**
	 * Creates the display.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the unit this bar belongs to.
	 */
	public HealthDisplay(int x, int y, GridUnit owner)
	{
		super(x, y, 170, 20);

		// Create a health bar and text to represent the unit health.
		addChild(Gui.UNIT_HEALTHBAR, new HealthBar(x, y, 100, 10, owner));
		addChild(Gui.UNIT_HEALTHTEXT, new HealthText(x + getChild(Gui.UNIT_HEALTHBAR).getWidth() + 10, y, width, height, owner));
	}
}
