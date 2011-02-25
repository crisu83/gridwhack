package gridwhack.gui;

import gridwhack.CComponent;

import java.awt.*;

/**
 * Gui element base class file.
 * Allows for adding elements to panels in the gui.
 * All gui elements must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GuiElement extends CComponent
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	protected Font font;
	protected Color textColor;

	protected GuiPanel parent;

	/**
	 * Creates the element.
	 * @param x the element x-coordinate.
	 * @param y the element y-coordinate.
	 * @param width the element width.
	 * @param height the element height.
	 */
	public GuiElement(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Returns the absolute x-coordinate of this element
	 * including the parent panels offset.
	 * @return the x-coordinate.
	 */
	public int getX()
	{
		int x = this.x;
		
		GuiPanel panel = getParent();
		
		if( panel!=null )
		{
			x += panel.getX();
		}
		
		return x;
	}
	
	/**
	 * Returns the absolute y-coordinate of this element
	 * including the parent panels offset.
	 * @return the y-coordinate.
	 */
	public int getY()
	{
		int y = this.y;
		
		GuiPanel panel = getParent();
		
		if( panel!=null )
		{
			y += panel.getY();
		}
		
		return y;
	}

	/**
	 * Sets the width of this element.
	 * @param width the width.
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * Sets the height of this element.
	 * @param height the height.
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * Returns the panel this elements belongs to.
	 * @return the panel.
	 */
	public GuiPanel getParent()
	{
		return parent;
	}

	/**
	 * Sets the panel this element belongs to.
	 * @param parent the panel.
	 */
	public void setParent(GuiPanel parent)
	{
		this.parent = parent;
	}

	/**
	 * Sets the font.
	 * @param font the font.
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	/**
	 * Sets the text color.
	 * @param color the color.
	 */
	public void setTextColor(Color color)
	{
		this.textColor = color;
	}

	/**
	 * Updates this element.
	 * @param timePassed the time that has passed.
	 */
	public abstract void update(long timePassed);
	
	/**
	 * Renders this element.
	 * @param g the graphics context.
	 */
	public abstract void render(Graphics2D g);
}
