package gridwhack.gui.unit;

import java.awt.*;

import gridwhack.event.IEventListener;
import gridwhack.grid.GridUnit;
import gridwhack.gui.GuiElement;

/**
 * Status bar class.
 * Allows for visualizing unit values in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class StatusBar extends GuiElement implements IEventListener
{
	protected int barWidth;
	protected Color barColor;
	protected GridUnit owner;
	
	/**
	 * Creates the bar.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the width of the bar.
	 * @param height the height of the bar.
	 * @param empty whether the bar should be empty.
	 * @param owner the unit this bar belongs to.
	 */
	public StatusBar(int x, int y, int width, int height, boolean empty, GridUnit owner)
	{
		super(x, y, width, height);

		this.owner = owner;

		backgroundColor = Color.darkGray;

		barWidth = !empty ? width : 0;

		owner.addListener(this); // set the bar to listen to its owner.
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
		return Math.round(percent * width);
	}
	
	/**
	 * Updates the bar position.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 */
	public void move(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Renders the status bar.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		// render a background for the bar.
		g.setColor(getBackgroundColor());
		g.fillRect(getX(), getY(), width, height);
		
		// render the actual bar.
		g.setColor(barColor);
		g.fillRect(getX(), getY(), barWidth, height);
	}
}