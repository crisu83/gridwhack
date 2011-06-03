package gridwhack.gui;

import gridwhack.base.BaseObject;
import gridwhack.gameobject.GameObject;
import gridwhack.render.IDrawable;
import gridwhack.util.Vector2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Gui element base class file.
 * Allows for adding elements to panels in the gui.
 * All gui elements must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GuiElement extends GameObject implements IDrawable
{
	public static enum GuiElementType {
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

	// ----------
	// Properties
	// ----------

	private int width;
	private int height;
	private Font font;
	private int lineHeight;
	private	Color textColor;
	private	Color backgroundColor;
	private GuiElement parent;
	private Map<GuiElementType, GuiElement> children;

	protected volatile boolean visible = true; // elements are visible by default

	// -------
	// Methods
	// -------

	/**
	 * Creates the element.
	 * @param x the element x-coordinate.
	 * @param y the element y-coordinate.
	 * @param width the element width.
	 * @param height the element height.
	 */
	public GuiElement(int x, int y, int width, int height)
	{
		this.position = new Vector2(x, y);
		this.width = width;
		this.height = height;

		// We use the gui font by default.
		this.font = font == null ? Gui.getInstance().getWindow().getFont() : font;

		// Line height is 1.5 times the font size.
		this.lineHeight = (int) Math.round(font.getSize() * 1.5);

		// We use white text by default.
		this.textColor = textColor==null ? Color.white : textColor;

		// Initialize the map for child elements.
		children = new HashMap<GuiElementType, GuiElement>();
	}

	/**
	 * Adds a child element to this element.
	 * @param type The element type.
	 * @param element The element.
	 */
	public synchronized void addChild(GuiElementType type, GuiElement element)
	{
		element.setParent(this); // set this element as the child elements parent.
		children.put(type, element);
	}

	/**
	 * Returns a child element from this element.
	 * @param type The element type.
	 * @return The element.
	 */
	public GuiElement getChild(GuiElementType type)
	{
		// Make sure that the element exists.
		return children.containsKey(type) ? children.get(type) : null;
	}

	/**
	 * Removes a child element from this element.
	 * @param type The element type.
	 */
	public synchronized void removeChild(GuiElementType type)
	{
		children.remove(type);
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Updates this object.
	 * @param parent The parent object.
	 */
	@Override
	public void update(BaseObject parent)
	{
		if (visible)
		{
			if (!children.isEmpty())
			{
				for (GuiElement child : children.values())
				{
					child.update(this);
				}
			}
		}
	}

	/**
	 * Draws this object.
	 * @param g The graphics object
	 */
	@Override
	public void draw(Graphics2D g)
	{
		if (visible)
		{
			// Set the background color if necessary.
			if (backgroundColor != null)
			{
				g.setColor(backgroundColor);
				g.fillRect(getX(), getY(), width, height);
			}

			if (!children.isEmpty())
			{
				for (GuiElement child : children.values())
				{
					child.draw(g);
				}
			}
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Calculates this elements absolute x-coordinate
	 * taking into account the parent element if necessary.
	 * @return the x-coordinate.
	 */
	public int getX()
	{
		int cx = (int) position.x;

		if (parent != null)
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
		int cy = (int) position.y;

		if (parent != null)
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
	 * @return The parent element.
	 */
	public GuiElement getParent()
	{
		return parent;
	}

	/**
	 * Sets a new parent for this element.
	 * @param parent The parent element.
	 */
	public void setParent(GuiElement parent)
	{
		this.parent = parent;
	}

	/**
	 * Returns the font for this element.
	 * @return The font.
	 */
	public Font getFont()
	{
		return font;
	}

	/**
	 * Sets a new font for this element.
	 * @param font The font.
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	/**
	 * Returns the font size for this element.
	 * @return The font size.
	 */
	public int getFontSize()
	{
		return font.getSize();
	}

	/**
	 * Returns the line height for this element.
	 * @return The line height.
	 */
	public int getLineHeight()
	{
		return lineHeight;
	}

	/**
	 * Sets a new line height for this element.
	 * @param lineHeight The line height.
	 */
	public void setLineHeight(int lineHeight)
	{
		this.lineHeight = lineHeight;
	}

	/**
	 * Returns the text color for this element.
	 * @return The color.
	 */
	public Color getTextColor()
	{
		return textColor;
	}

	/**
	 * Sets a new text color for this element.
	 * @param color The color.
	 */
	public void setTextColor(Color color)
	{
		this.textColor = color;
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
	 * Sets a new background color for this element.
	 * @param backgroundColor The color.
	 */
	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}
}
