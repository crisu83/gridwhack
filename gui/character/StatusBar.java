package gridwhack.gui.character;

import java.awt.*;

import gridwhack.entity.character.Character;
import gridwhack.event.IEventListener;
import gridwhack.gui.GuiElement;

/**
 * Status bar class.
 * Allows for visualizing character values in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class StatusBar extends GuiCharacterElement implements IEventListener
{
	private int barWidth;
	private Color barColor;

	/**
	 * Creates the bar.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the width of the bar.
	 * @param height the height of the bar.
	 * @param empty whether the bar should be empty.
	 * @param owner the character this bar belongs to.
	 */
	public StatusBar(int x, int y, int width, int height, boolean empty, Character owner)
	{
		super(x, y, width, height, owner);

		setBackgroundColor( Color.darkGray );

		barWidth = !empty ? width : 0;

		getOwner().addListener(this); // set the bar to listen to its owner.
	}

	/**
	 * Sets the bar color.
	 * @param color the color
	 */
	public void setBarColor(Color color)
	{
		this.barColor = color;
	}

	/**
	 * Sets the bar width.
	 * @param width the new width of the bar.
	 */
	public void setBarWidth(int width)
	{
		this.barWidth = width;
	}

	/**
	 * Calculates the bar width based on the current value.
	 * @param current the current value.
	 * @param maximum the maximum value.
	 * @return the width.
	 */
	protected int calculateBarWidth(int current, int maximum)
	{
		// calculate how many percent the current value is of the maximum value.
		float percent = (float) current / (float) maximum;

		// calculate the new width for the health bar.
		return Math.round(percent * getWidth());
	}
	
	/**
	 * Updates the bar position.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 */
	public void move(int x, int y)
	{
		setX(x);
		setY(y);
	}

	/**
	 * Renders the status bar.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		// render a background for the bar.
		g.setColor(getBackgroundColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		// render the actual bar.
		g.setColor(barColor);
		g.fillRect(getX(), getY(), barWidth, getHeight());
	}
}