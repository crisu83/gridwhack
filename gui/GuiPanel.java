package gridwhack.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import gridwhack.CComponent;
import gridwhack.event.IEventListener;
import gridwhack.gui.event.IPanelListener;
import gridwhack.gui.event.PanelEvent;

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
	protected boolean removed;
	protected HashMap<String, GuiElement> elements;
	
	/**
	 * Creates the panel.
	 * @param x the panel x-coordinate.
	 * @param y the panel y-coordinate.
	 * @param width the panel width.
	 * @param height the panel height.
	 * @param bgColor the background color.
	 */
	public GuiPanel(int x, int y, int width, int height, Color bgColor)
	{		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		// Panel is almost black by default.
		this.bgColor = bgColor!=null ? bgColor : new Color(20, 20, 20);
		
		elements = new HashMap<String, GuiElement>();
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
	 * Marks this panel as removed.
	 */
	public synchronized void markRemoved()
	{
		removed = true;

		// Let all listeners know that this panel has been removed.
		firePanelEvent(new PanelEvent(PanelEvent.PANEL_REMOVE, this));
	}

	/**
	 * Adds an element to the panel.
	 * @param element the element.
	 */
	public synchronized void addElement(String name, GuiElement element)
	{
		element.setParent(this);
		elements.put(name, element);
	}

	/**
	 * Returns a specific element on this panel.
	 * @param name the name of the element.
	 * @return the element.
	 */
	public GuiElement getElement(String name)
	{
		// Make sure that the element exists.
		return elements.containsKey(name) ? elements.get(name) : null;
	}
	
	/**
	 * Removes an element from the panel.
	 * @param element the element.
	 */
	public synchronized void removeElement(GuiElement element)
	{
		elements.remove(element);
	}

	/**
	 * Fires a panel event.
	 * @param e the event.
	 */
	private synchronized void firePanelEvent(PanelEvent e)
	{
		for( IEventListener listener : getListeners() )
		{
			// Make sure we only notify panel listeners.
			if( listener instanceof IPanelListener )
			{
				switch( e.getType() )
				{
					// Panel has been removed.
					case PanelEvent.PANEL_REMOVE:
						( (IPanelListener) listener ).onPanelRemove(e);
						break;

					// Unknown event.
					default:
				}
			}
		}
	}
	
	/**
	 * Updates all elements in the panel.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		// Make sure that this panel is not removed.
		if( !removed )
		{
			// Update all panel elements.
			for( Map.Entry<String, GuiElement> element : elements.entrySet() )
			{
				element.getValue().update(timePassed);
			}
		}
	}
	
	/**
	 * Renders the panel and its elements.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		// Make sure that this panel is not removed.
		if( !removed )
		{
			g.setColor(bgColor);
			g.fillRect(x, y, width, height);

			// Render all panel elements.
			for( Map.Entry<String, GuiElement> element : elements.entrySet() )
			{
				element.getValue().render(g);
			}
		}
	}
}
