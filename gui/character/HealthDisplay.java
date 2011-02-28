package gridwhack.gui.character;

import gridwhack.entity.character.Character;
import gridwhack.gui.GuiElement;

/**
 * Health display class file.
 * Allows for representing character health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthDisplay extends GuiCharacterElement
{
	/**
	 * Creates the display.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the character this bar belongs to.
	 */
	public HealthDisplay(int x, int y, Character owner)
	{
		super(x, y, 170, 20, owner);

		// Create a health bar and text to represent the character health.
		addChild(GuiElement.Type.CHARACTER_HEALTHBAR, new HealthBar(x, y, 100, 10, owner));
		addChild(GuiElement.Type.CHARACTER_HEALTHTEXT, new HealthText(
				x + getChild(GuiElement.Type.CHARACTER_HEALTHBAR).getWidth() + 10, y, getWidth(), getHeight(), owner));
	}
}
