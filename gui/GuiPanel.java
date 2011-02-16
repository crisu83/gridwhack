package gridwhack.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.CComponent;

/**
 * Gui panel base class file.
 * Allows for adding panels to the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GuiPanel extends CComponent
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Color bgColor;
	protected ArrayList<GuiElement> elements;
	
	/**
	 * Creates the panel.
	 * @param x the panel x-coordinate.
	 * @param y the panel y-coordinate.
	 * @param width the panel width.
	 * @param height the panel height.
	 * @param bgColor the background bgColor.
	 */
	public GuiPanel(int x, int y, int width, int height, Color bgColor)
	{		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		// panel is black by default.
		this.bgColor = bgColor!=null ? bgColor : Color.black;
		
		elements = new ArrayList<GuiElement>();
	}
	
	/**
	 * Returns the x-coordinate of this panel.
	 * @return the x-coordinate.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Returns the y-coordinate of this panel.
	 * @return the y-coordinate.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Returns the width of this panel.
	 * @return the width.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of this panel.
	 * @return the height.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Adds an element to the panel.
	 * @param element the element.
	 */
	public void addElement(GuiElement element)
	{
		element.setParent(this);
		elements.add(element);
	}
	
	/**
	 * Removes an element from the panel.
	 * @param element the element.
	 */
	public void removeElement(GuiElement element)
	{
		elements.remove(element);
	}
	
	/**
	 * Updates all elements in the panel.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed) {}
	
	/**
	 * Renders the panel and its elements.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		g.setColor(bgColor);
		g.fillRect(x, y, width, height);
		
		// render all panel elements.
		for( GuiElement element : elements )
		{
			element.render(g);
		}
	}
}
