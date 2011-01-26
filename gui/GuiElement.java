package gridwhack.gui;

import java.awt.Graphics2D;

/**
 * Gui element base class.
 * Provides functionality for gui panel elements.
 */
public abstract class GuiElement 
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected GuiPanel parent;
	
	/**
	 * Constructs the element.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
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
	 * Returns the real x-coordinate by adding
	 * taking into account the parent panels offset.
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
	 * Returns the real y-coordinate by adding
	 * taking into account the parent panels offset.
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
	 * @return the panel this element belongs to.
	 */
	public GuiPanel getParent()
	{
		return parent;
	}
	
	/**
	 * @param parent the panel this element belongs to.
	 */
	public void setParent(GuiPanel parent)
	{
		this.parent = parent;
	}
	
	/**
	 * Renders the element.
	 * @param g the 2D graphics object.
	 */
	public abstract void render(Graphics2D g);
}
