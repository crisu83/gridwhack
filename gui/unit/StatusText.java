package gridwhack.gui.unit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import gridwhack.IEventListener;
import gridwhack.entity.unit.Unit;

/**
 * Base status text class.
 * Provides functionality for displaying unit values.
 */
public abstract class StatusText implements IEventListener 
{
	protected int x;
	protected int y;
	protected int fontSize;
	protected Color color;
	protected Unit owner;
	
	/**
	 * Constructs the status text.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param fontSize the font size.
	 * @param owner the unit this text belongs to.
	 */
	public StatusText(int x, int y, int fontSize, Unit owner)
	{
		this.x = x;
		this.y = y;
		this.fontSize = fontSize;
		this.owner = owner;		
		
		// set the text to listen to its owner.
		owner.addListener(this);
	}
	
	/**
	 * Renders the status text.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		// render the text.
		g.setFont(new Font("Arial", Font.PLAIN, fontSize));
		g.setColor(color);
		g.drawString(getText(), x, y);
	}
	
	/**
	 * @return the text.
	 */
	public abstract String getText();
}
