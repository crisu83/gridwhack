package gridwhack.gui.unit;

import java.awt.Graphics2D;

import gridwhack.event.IEventListener;
import gridwhack.grid.GridUnit;
import gridwhack.gui.GuiElement;

/**
 * Status text class file.
 * Allows for representing unit values as text in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class StatusText extends GuiElement implements IEventListener
{
	protected GridUnit owner;
	
	/**
	 * Creates the status text.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the width.
	 * @param height the height.
	 * @param owner the unit this text belongs to.
	 */
	public StatusText(int x, int y, int width, int height, GridUnit owner)
	{
		super(x, y, width, height);

		this.owner = owner;

		owner.addListener(this); // set the text to listen to its owner.
	}
	
	/**
	 * Renders the status text.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		g.setFont(getFont());
		g.setColor(getTextColor());
		g.drawString(getText(), getX(), getY() + (int) Math.round(getFontSize() * 0.8));
	}
	
	/**
	 * @return the text.
	 */
	public abstract String getText();
}
