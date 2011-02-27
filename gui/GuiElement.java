package gridwhack.gui;

import gridwhack.CComponent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
	protected int lineHeight;

	protected Color textColor;
	protected Color backgroundColor;

	protected GuiElement parent;
	protected Map<String, GuiElement> children;

	protected volatile boolean visible = true; // elements are visible by default	

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

		// We use the gui font by default.
		this.font = font==null ? Gui.getInstance().getWindow().getFont() : font;

		// Line height is 1.5 times the font size.
		this.lineHeight = (int) Math.round(font.getSize() * 1.5);

		// We use white text by default.
		this.textColor = textColor==null ? Color.white : textColor;

		// Initialize the map for child elements.
		children = new HashMap<String, GuiElement>();
	}

	/**
	 * Adds a child element to this element.
	 * @param element the element.
	 */
	public synchronized void addChild(String name, GuiElement element)
	{
		element.setParent(this); // set this element as the child elements parent.
		children.put(name, element);
	}

	/**
	 * Returns a specific child element.
	 * @param name the name of the element.
	 * @return the element.
	 */
	public GuiElement getChild(String name)
	{
		// Make sure that the element exists.
		return children.containsKey(name) ? children.get(name) : null;
	}

	/**
	 * Removes a child element from this element.
	 * @param element the element.
	 */
	public synchronized void removeChild(GuiElement element)
	{
		children.remove(element);
	}

	
	/**
	 * Calculates this elements absolute x-coordinate
	 * taking into account the parent element if necessary.
	 * @return the x-coordinate.
	 */
	public int getX()
	{
		int cx = x;
		
		if( parent!=null )
		{
			cx += parent.getX();
		}
		
		return cx;
	}
	
	/**
	 * Calculates this elements absolute y-coordinate
	 * taking into account the parent element if necessary.
	 * @return the y-coordinate.
	 */
	public int getY()
	{
		int cy = y;
		
		if( parent!=null )
		{
			cy += parent.getY();
		}
		
		return cy;
	}

	/**
	 * Returns the width of this element.
	 * @return the width.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Returns the height of this element.
	 * @return the height.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Returns the parent element for this element.
	 * @return the parent element.
	 */
	public GuiElement getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent for this element.
	 * @param parent the parent element.
	 */
	public void setParent(GuiElement parent)
	{
		this.parent = parent;
	}

	/**
	 * Sets the font for this element.
	 * @param font the font.
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	/**
	 * Returns the font for this element.
	 * @return the font.
	 */
	public Font getFont()
	{
		return font;
	}

	/**
	 * Returns the font size for this element.
	 * @return the font size.
	 */
	public int getFontSize()
	{
		return font.getSize();
	}

	/**
	 * Returns the line height for this element.
	 * @return the line height.
	 */
	public int getLineHeight()
	{
		return lineHeight;
	}

	/**
	 * Sets the text color for this element.
	 * @param color the color.
	 */
	public void setTextColor(Color color)
	{
		this.textColor = color;
	}

	/**
	 * Returns the text color for this element.
	 * @return the color.
	 */
	public Color getTextColor()
	{
		return textColor;
	}

	/**
	 * Sets the background color for this element.
	 * @param color the color.
	 */
	public void setBackgroundColor(Color color) 
	{
		this.backgroundColor = color;
	}

	/**
	 * Returns the background color for this element.
	 * @return the color.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Updates this element and its children if necessary.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		if( visible )
		{
			// Update the children if necessary.
			if( !children.isEmpty() )
			{
				// Update the children.
				for( Map.Entry<String, GuiElement> element : children.entrySet() )
				{
					element.getValue().update(timePassed);
				}
			}
		}
	}
	
	/**
	 * Renders this element and its children if necessary.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		if( visible )
		{
			// Set a background color if necessary.
			if( backgroundColor!=null )
			{
				g.setColor(backgroundColor);
				g.fillRect(getX(), getY(), width, height);
			}

			// Render the children if necessary.
			if( !children.isEmpty() )
			{
				for( Map.Entry<String, GuiElement> element : children.entrySet() )
				{
					element.getValue().render(g);					
				}
			}
		}
	}
}
