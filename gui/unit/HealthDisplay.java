package gridwhack.gui.unit;

import java.awt.*;

import gridwhack.grid.GridUnit;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;

/**
 * Health display class file.
 * Allows for representing unit health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthDisplay extends GuiElement 
{
	protected HealthBar healthBar;
	protected HealthText healthText;
	
	/**
	 * Creates the display.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the unit this bar belongs to.
	 */
	public HealthDisplay(int x, int y, GridUnit owner)
	{
		super(x, y, 170, 20);

		setFont( Gui.getInstance().getWindow().getFont() );
		setTextColor(Color.white);
		
		// create a health bar and text to represent the player health.
		healthBar = new HealthBar(x, y, 100, 10, owner);
		healthText = new HealthText(x+healthBar.getWidth()+10, y, font, owner);
	}

	/**
	 * Updates this element.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed) {}
	
	/**
	 * Renders this element.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		healthBar.render(g);
		healthText.render(g);
	}
}
