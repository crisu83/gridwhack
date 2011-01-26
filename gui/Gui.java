package gridwhack.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.CEvent;
import gridwhack.IEventListener;

/**
 * Graphical user interface class.
 * Provides functionality for rendering panels.
 */
public class Gui implements IEventListener
{
	protected static Gui instance = new Gui();
	protected static ArrayList<GuiPanel> panels = new ArrayList<GuiPanel>();
	
	/**
	 * Private constructor to enforce the singleton pattern.
	 */
	private Gui() {}
	
	/**
	 * @return the single gui instance.
	 */
	private static Gui get()
	{
		return instance;
	}
	
	/**
	 * Adds a panel to the gui.
	 * @param panel the panel.
	 */
	public static void addPanel(GuiPanel panel)
	{
		panel.addListener(Gui.get());
		panels.add(panel);
	}
	
	/**
	 * Removes a panel from the gui.
	 * @param panel the panel.
	 */
	public void removePanel(GuiPanel panel)
	{
		panel.removeListener(Gui.get());
		panels.remove(panel);
	}
	
	/**
	 * @return the panels.
	 */
	public static ArrayList<GuiPanel> getPanels()
	{
		return Gui.get().panels;
	}
	
	/**
	 * Updates all panels in the gui.
	 * @param timePassed the time that has passed.
	 */
	public static void update(long timePassed)
	{
		for( GuiPanel panel : Gui.getPanels() )
		{
			panel.update(timePassed);
		}
	}
	
	/**
	 * Renders all panels in the gui
	 * @param g the 2D graphics object.
	 */
	public static void render(Graphics2D g)
	{
		for( GuiPanel panel : Gui.getPanels() )
		{
			panel.render(g);
		}
	}

	/**
	 * Handles incoming events.
	 * @param e the event.
	 */
	public void handleEvent(CEvent e) 
	{
		String type = e.getType();
		Object source = e.getSource();
		
		// Handle panel events.
		if( source instanceof GuiPanel )
		{
			if( type=="remove" )
			{
				removePanel((GuiPanel)source);
			}
		}
	}
}
