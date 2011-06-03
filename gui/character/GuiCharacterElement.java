package gridwhack.gui.character;

import gridwhack.gameobject.character.Character;
import gridwhack.gui.GuiElement;

public class GuiCharacterElement extends GuiElement
{
	private Character owner;

	/**
	 * Creates the element.
	 * @param x the element x-coordinate.
	 * @param y the element y-coordinate.
	 * @param width the element width.
	 * @param height the element height.
	 * @param owner the character this text belongs to.
	 */
	public GuiCharacterElement(int x, int y, int width, int height, Character owner)
	{
		super(x, y, width, height);

		this.owner = owner;
	}

	/**
	 * Returns the owner of this element.
	 * @return the owner.
	 */
	public Character getOwner()
	{
		return owner;
	}
}
