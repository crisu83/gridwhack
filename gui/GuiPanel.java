package gridwhack.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.CComponent;

/**
 * Gui panel base class.
 * Provides functionality for gui panels.
 */
public class GuiPanel extends CComponent
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Color color;
	protected ArrayList<GuiElement> elements;
	
	/**
	 * Constructs the panel
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the panel width.
	 * @param height the panel height.
	 * @param color the background color.
	 */
	public GuiPanel(int x, int y, int width, int height, Color color)
	{		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		// panel is black by default.
		this.color = color!=null ? color : Color.black;
		
		elements = new ArrayList<GuiElement>();
	}
	
	/**
	 * @return the x-coordinate.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * @return the y-coordinate.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * @return the panel width.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * @return the panel height.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Adds and element to the panel.
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
	 * Updates all elements on the panel.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		// nothing at this time.
	}
	
	/**
	 * Renders the panel and all its elements.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		// render all panel elements.
		for( GuiElement element : elements )
		{
			element.render(g);
		}
	}
}
