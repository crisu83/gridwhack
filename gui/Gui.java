package gridwhack.gui;

import gridwhack.base.BaseObject;
import gridwhack.gui.GuiPanel.GuiPanelType;
import gridwhack.render.IDrawable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Graphical user interface class file.
 * Allows for rendering panels.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Gui extends BaseObject implements IDrawable
{
	// ----------
	// Properties
	// ----------

	protected static Gui instance = new Gui();
	protected Map<GuiPanelType, GuiPanel> panels;
	protected Window window;

	// -------
	// Methods
	// -------

	/**
	 * Creates the factory.
	 * Private to enforce the singleton pattern.
	 */
	private Gui()
	{
		panels = new HashMap<GuiPanelType, GuiPanel>();
	}
	
	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static Gui getInstance()
	{
		return instance;
	}

	/**
	 * Adds a panel to the gui.
	 * @param type The panel type.
	 * @param panel The panel to add.
	 */
	public synchronized void addPanel(GuiPanel.GuiPanelType type, GuiPanel panel)
	{
		panels.put(type, panel);
	}

	/**
	 * Returns a panel in the gui.
	 * @param type The panel type.
	 * @return The panel or null if the panel was not found.
	 */
	public GuiPanel getPanel(GuiPanel.GuiPanelType type)
	{
		return panels.containsKey(type) ? panels.get(type) : null;
	}

	/**
	 * Removes a panel from the gui.
	 * @param type the panel type.
	 */
	public synchronized void removePanel(GuiPanel.GuiPanelType type)
	{
		panels.remove(type);
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
		for(GuiPanel panel : panels.values())
		{
			panel.update(this);
		}
	}

	/**
	 * Draws the object.
	 * @param g The graphics context.
	 */
	@Override
	public void draw(Graphics2D g)
	{
		for(GuiPanel panel : panels.values())
		{
			panel.draw(g);
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Sets a new window for this gui.
	 * @param window The window.
	 */
	public void setWindow(Window window)
	{
		instance.window = window;
	}

	/**
	 * Returns the window for this gui.
	 * @return The window.
	 */
	public Window getWindow()
	{
		return window;
	}
}
