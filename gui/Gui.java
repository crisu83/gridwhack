package gridwhack.gui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import gridwhack.gui.event.IPanelListener;
import gridwhack.gui.event.PanelEvent;

/**
 * Graphical user interface class file.
 * Allows for rendering panels.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Gui implements IPanelListener
{
	protected static Gui instance = new Gui();

	protected HashMap<GuiPanel.Type, GuiPanel> panels;

	protected Window window;

	/**
	 * Private constructor enforces the singleton pattern.
	 */
	private Gui()
	{
		// Initialize the map for the panels.
		panels = new HashMap<GuiPanel.Type, GuiPanel>();
	}
	
	/**
	 * @return the single gui instance.
	 */
	public static Gui getInstance()
	{
		return instance;
	}

	/**
	 * Sets the window for this gui.
	 * @param window the window.
	 */
	public void setWindow(Window window)
	{
		instance.window = window;
	}

	/**
	 * Returns the window for this gui.
	 * @return the window.
	 */
	public Window getWindow()
	{
		return window;
	}

	/**
	 * Adds a panel to the gui.
	 * @param type the panel type.
	 * @param panel the panel.
	 */
	public synchronized void addPanel(GuiPanel.Type type, GuiPanel panel)
	{
		panel.addListener(instance);
		panels.put(type, panel);
	}

	/**
	 * Returns a specific panel in the gui.
	 * @param type the panel type.
	 * @return the panel.
	 */
	public GuiPanel getPanel(GuiPanel.Type type)
	{
		return panels.containsKey(type) ? panels.get(type) : null;
	}
	
	/**
	 * Removes a panel from the gui.
	 * @param type the panel type.
	 */
	public synchronized void removePanel(GuiPanel.Type type)
	{
		// TODO: Figure out how to get the Gui to remove panels properly.
		//panel.removeListener(instance);
		panels.remove(type);
	}
	
	/**
	 * Updates all panels in the gui.
	 * @param timePassed the time that has passed.
	 */
	public synchronized void update(long timePassed)
	{
		for( Map.Entry<GuiPanel.Type, GuiPanel> panel : panels.entrySet() )
		{
			panel.getValue().update(timePassed);
		}
	}
	
	/**
	 * Renders all panels in the gui.
	 * @param g the graphics context.
	 */
	public synchronized void render(Graphics2D g)
	{
		for( Map.Entry<GuiPanel.Type, GuiPanel> panel : panels.entrySet() )
		{
			panel.getValue().render(g);
		}
	}

	/**
	 * Actions to be taken when a panel is removed.
	 * @param e the event.
	 */
	public synchronized void onPanelRemove(PanelEvent e)
	{
		//removePanel( (GuiPanel2) e.getSource() );
	}
}
