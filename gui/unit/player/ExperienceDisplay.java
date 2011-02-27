package gridwhack.gui.unit.player;

import gridwhack.entity.character.Character;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;

/**
 * Experience display class file.
 * Allows for representing character health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ExperienceDisplay extends GuiElement
{
	/**
	 * Creates the display.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the character this bar belongs to.
	 */
	public ExperienceDisplay(int x, int y, Character owner)
	{
		super(x, y, 170, 20);

		// Create a experience bar and text to represent the player experience.
		addChild(Gui.PLAYER_EXPERIENCEBAR, new ExperienceBar(x, y, 100, 10, owner));
		addChild(Gui.PLAYER_EXPERIENCETEXT, new ExperienceText(x + getChild(Gui.PLAYER_EXPERIENCEBAR).getWidth() + 10, y, width, height, owner));
	}
}
