package gridwhack.gui.unit;

import java.awt.*;

import gridwhack.entity.unit.event.IUnitListener;
import gridwhack.grid.GridUnit;

/**
 * Status bar class.
 * Allows for visualizing unit values in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class StatusBar implements IUnitListener
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int barWidth;
	protected Color color;
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
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.owner = owner;
		
		// determine the bars initial width
		barWidth = !empty ? width : 0;
		
		// set the bar to listen to its owner.
		owner.addListener(this);
	}
	
	/**
	 * Updates the bar width.
	 * @param barWidth the new width of the bar. 
	 */
	public void setBarWidth(int barWidth)
	{
		this.barWidth = barWidth;
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
		g.setColor(Color.darkGray);
		g.fillRect(x, y, width, height);
		
		// render the actual bar.
		g.setColor(color);
		g.fillRect(x, y, barWidth, height);
	}
}