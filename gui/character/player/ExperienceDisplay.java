package gridwhack.gui.character.player;

import gridwhack.gameobject.character.Character;
import gridwhack.gui.character.GuiCharacterElement;

/**
 * Experience display class file.
 * Allows for representing character health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ExperienceDisplay extends GuiCharacterElement
{
	/**
	 * Creates the display.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the character this bar belongs to.
	 */
	public ExperienceDisplay(int x, int y, Character owner)
	{
		super(x, y, 170, 20, owner);

		// Create a experience bar and text to represent the player experience.
		addChild(GuiElementType.PLAYER_EXPERIENCEBAR, new ExperienceBar(x, y, 100, 10, owner));
		addChild(GuiElementType.PLAYER_EXPERIENCETEXT, new ExperienceText(
				x + getChild(GuiElementType.PLAYER_EXPERIENCEBAR).getWidth() + 10, y, getWidth(), getHeight(), owner));
	}
}
