package gridwhack.gui.unit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import gridwhack.entity.unit.event.IUnitListener;
import gridwhack.grid.GridUnit;
import gridwhack.gui.Gui;

/**
 * Status text class file.
 * Allows for representing unit values as text in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class StatusText implements IUnitListener
{
	protected int x;
	protected int y;
	protected Font font;
	protected Color color;
	protected GridUnit owner;
	
	/**
	 * Constructs the status text.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param font the font to use.
	 * @param owner the unit this text belongs to.
	 */
	public StatusText(int x, int y, Font font, GridUnit owner)
	{
		this.x = x;
		this.y = y;
		this.font = font;
		this.owner = owner;

		this.color = Color.white;
		
		// set the text to listen to its owner.
		owner.addListener(this);
	}
	
	/**
	 * Renders the status text.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		g.setFont(font);
		g.setColor(color);
		g.drawString(getText(), x, y);
	}
	
	/**
	 * @return the text.
	 */
	public abstract String getText();
}
