package gridwhack.gui.unit.player;

import gridwhack.entity.unit.player.Player;
import gridwhack.entity.unit.player.event.IPlayerListener;
import gridwhack.entity.unit.player.event.PlayerEvent;
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
	protected Font font;
	protected Color color;
	private Player player;

	/**
	 * Creates the element.
	 * @param x the element x-coordinate.
	 * @param y the element y-coordinate.
	 */
	public PlayerDetails(int x, int y, Player player)
	{
		super(x, y, 200, 20);

		this.font = Gui.getInstance().getWindow().getFont();
		this.color = Color.white;
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
		g.setFont(font);
		g.setColor(color);
		g.drawString(player.getName() + " (Level " + player.getLevel() + ")", getX(), getY()+font.getSize());
	}
}
