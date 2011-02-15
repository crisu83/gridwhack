package gridwhack.gui.unit;

import java.awt.Graphics2D;

import gridwhack.grid.GridUnit;
import gridwhack.gui.GuiElement;

public class HealthDisplay extends GuiElement 
{
	protected HealthBar healthBar;
	protected HealthText healthText;
	protected GridUnit owner;
	
	/**
	 * Consturcts the health display.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the unit which health to display.
	 */
	public HealthDisplay(int x, int y, GridUnit owner)
	{
		super(x, y, 170, 20);
		
		// create a health bar and text 
		// to represent the player health.
		healthBar = new HealthBar(10, 10, 100, 10, owner);
		healthText = new HealthText(120, 18, owner);
	}
	
	/**
	 * Renders the element.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		healthBar.render(g);
		healthText.render(g);
	}
}
