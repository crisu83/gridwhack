package gridwhack.gui.unit.player;

import gridwhack.entity.unit.player.Player;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;

import java.awt.*;

/**
 * Player details class file.
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
	 * Renders this element.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		g.setFont(getFont());
		g.setColor(getTextColor());
		g.drawString(player.getName() + " (Level " + player.getLevel() + ")", getX(), getY() + getFontSize());
	}
}
