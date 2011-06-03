package gridwhack.gui.character.player;

import gridwhack.gameobject.character.player.Player;
import gridwhack.gui.GuiElement;

import java.awt.*;

/**
 * Player details class.
 * Allows for visualizing player experience in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class PlayerDetails extends GuiElement
{
	private Player player;

	/**
	 * Creates the element.
	 * @param x the element x-coordinate.
	 * @param y the element y-coordinate.
	 */
	public PlayerDetails(int x, int y, Player player)
	{
		super(x, y, 200, 20);

		this.player = player;
	}

	/**
	 * Updates this element.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed) {}

	/**
	 * Draws this element.
	 * @param g The graphics context.
	 */
	public void draw(Graphics2D g)
	{
		g.setFont(getFont());
		g.setColor(getTextColor());
		g.drawString(player.getName() + " (Level " + player.getLevel() + ")", getX(), getY() + getFontSize());
	}
}
