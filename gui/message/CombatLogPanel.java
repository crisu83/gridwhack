package gridwhack.gui.message;

import gridwhack.gui.GuiPanel;

/**
 * Combat log panel class.
 * Gui panel for the combat log.
 */
public class CombatLogPanel extends GuiPanel
{
	/**
	 * Constructs the panel.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 */
	public CombatLogPanel(int x, int y) 
	{
		super(x, y, 640, 90, null);
		
		// add a combat log box element to the panel.
		addElement(new CombatLogBox(5, 15, 290, 10));
	}
}
