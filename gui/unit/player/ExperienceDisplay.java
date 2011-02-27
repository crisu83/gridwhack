package gridwhack.gui.unit.player;

import gridwhack.grid.GridUnit;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;

import java.awt.*;

/**
 * Experience display class file.
 * Allows for representing unit health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ExperienceDisplay extends GuiElement
{
	/**
	 * Creates the display.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the unit this bar belongs to.
	 */
	public ExperienceDisplay(int x, int y, GridUnit owner)
	{
		super(x, y, 170, 20);

		// Create a experience bar and text to represent the player experience.
		addChild(Gui.PLAYER_EXPERIENCEBAR, new ExperienceBar(x, y, 100, 10, owner));
		addChild(Gui.PLAYER_EXPERIENCETEXT, new ExperienceText(x + getChild(Gui.PLAYER_EXPERIENCEBAR).getWidth() + 10, y, width, height, owner));
	}
}
