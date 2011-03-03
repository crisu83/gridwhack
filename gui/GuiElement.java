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
	public static enum Type {
		PLAYER_DETAILS,
		PLAYER_HEALTHDISPLAY,
		PLAYER_EXPERIENCEDISPLAY,
		PLAYER_EXPERIENCEBAR,
		PLAYER_EXPERIENCETEXT,
		PLAYER_LOOTBOX,
		CHARACTER_HEALTHBAR,
		CHARACTER_HEALTHTEXT,
		GAME_MESSAGELOGBOX,
		GAME_COMBATLOGBOX,
	};

	private int x;
	private int y;
	private int width;
	private int height;
	private Font font;
	private int lineHeight;
	private	Color textColor;
	private	Color backgroundColor;
	private GuiElement parent;
	private Map<GuiElement.Type, GuiElement> children;

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
		children = new HashMap<GuiElement.Type, GuiElement>();
	}

	/**
	 * Adds a child element to this element.
	 * @param type the element type.
	 * @param element the element.
	 */
	public synchronized void addChild(GuiElement.Type type, GuiElement element)
	{
		element.setParent(this); // set this element as the child elements parent.
		children.put(type, element);
	}

	/**
	 * Returns a specific child element.
	 * @param type the element type.
	 * @return the element.
	 */
	public GuiElement getChild(GuiElement.Type type)
	{
		// Make sure that the element exists.
		return children.containsKey(type) ? children.get(type) : null;
	}

	/**
	 * Removes a child element from this element.
	 * @param type the element type.
	 */
	public synchronized void removeChild(GuiElement.Type type)
	{
		children.remove(type);
	}

	/**
	 * Sets the x-coordinate for this element.
	 * @param x the x-coordinate.
	 */
	public void setX(int x)
	{
		this.x = x;
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
	 * Sets the y-coordinate for this element.
	 * @param y the y-coordinate.
	 */
	public void setY(int y)
	{
		this.y = y;
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
	 * Sets the line height for this element.
	 * @param lineHeight the line height.
	 */
	public void setLineHeight(int lineHeight)
	{
		this.lineHeight = lineHeight;
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
	public Color getBackgroundColor()
	{
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
				for( Map.Entry<GuiElement.Type, GuiElement> element : children.entrySet() )
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
				for( Map.Entry<GuiElement.Type, GuiElement> element : children.entrySet() )
				{
					element.getValue().render(g);					
				}
			}
		}
	}
}
