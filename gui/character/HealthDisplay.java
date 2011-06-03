package gridwhack.gui.character;

import gridwhack.gameobject.character.Character;

/**
 * Health display class.
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
		addChild(GuiElementType.CHARACTER_HEALTHBAR, new HealthBar(x, y, 100, 10, owner));
		addChild(GuiElementType.CHARACTER_HEALTHTEXT, new HealthText(
				x + getChild(GuiElementType.CHARACTER_HEALTHBAR).getWidth() + 10, y, getWidth(), getHeight(), owner));
	}
}
