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

	protected HashMap<String, GuiPanel> panels = new HashMap<String, GuiPanel>();

	protected Window window;

	public static final String PLAYER_PANEL = "playerPanel";
	public static final String PLAYER_HEALTHDISPLAY = "healthDisplay";
	public static final String PLAYER_LOOTBOX = "lootBox";
	public static final String PLAYER_LOOTWINDOW = "lootWindow";
	public static final String GAME_MESSAGELOG = "messageLog";
	public static final String GAME_MESSAGELOGBOX = "messageLogBox";
	public static final String GAME_COMBATLOG = "combatLog";
	public static final String GAME_COMBATLOGBOX = "combatLogBox";

	/**
	 * Private constructor enforces the singleton pattern.
	 */
	private Gui() {}
	
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
	 * @param panel the panel.
	 */
	public synchronized void addPanel(String name, GuiPanel panel)
	{
		panel.addListener(instance);
		panels.put(name, panel);
	}

	/**
	 * Returns a specific panel in the gui.
	 * @param name the name of the panel.
	 * @return the panel.
	 */
	public GuiPanel getPanel(String name)
	{
		return panels.containsKey(name) ? panels.get(name) : null;
	}
	
	/**
	 * Removes a panel from the gui.
	 * @param name the name of the panel.
	 */
	public synchronized void removePanel(String name)
	{
		//panel.removeListener(instance);
		panels.remove(name);
	}
	
	/**
	 * Updates all panels in the gui.
	 * @param timePassed the time that has passed.
	 */
	public synchronized void update(long timePassed)
	{
		for( Map.Entry<String, GuiPanel> panel : panels.entrySet() )
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
		for( Map.Entry<String, GuiPanel> panel : panels.entrySet() )
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
		//removePanel( (GuiPanel) e.getSource() );
	}
}
