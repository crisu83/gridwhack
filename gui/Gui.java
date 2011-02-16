package gridwhack.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.event.IEventListener;

/**
 * Graphical user interface class file.
 * Allows for rendering panels.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Gui implements IEventListener
{
	protected static Gui instance = new Gui();

	protected static ArrayList<GuiPanel> panels = new ArrayList<GuiPanel>();
	
	/**
	 * Private constructor enforces the singleton pattern.
	 */
	private Gui() {}
	
	/**
	 * @return the single gui instance.
	 */
	private static Gui getInstance()
	{
		return instance;
	}
	
	/**
	 * Adds a panel to the gui.
	 * @param panel the panel.
	 */
	public static void addPanel(GuiPanel panel)
	{
		panel.addListener(Gui.getInstance());
		panels.add(panel);
	}
	
	/**
	 * Removes a panel from the gui.
	 * @param panel the panel.
	 */
	public void removePanel(GuiPanel panel)
	{
		panel.removeListener(Gui.getInstance());
		panels.remove(panel);
	}
	
	/**
	 * Updates all panels in the gui.
	 * @param timePassed the time that has passed.
	 */
	public static void update(long timePassed)
	{
		for( GuiPanel panel : panels )
		{
			panel.update(timePassed);
		}
	}
	
	/**
	 * Renders all panels in the gui.
	 * @param g the graphics context.
	 */
	public static void render(Graphics2D g)
	{
		for( GuiPanel panel : panels )
		{
			panel.render(g);
		}
	}
}
