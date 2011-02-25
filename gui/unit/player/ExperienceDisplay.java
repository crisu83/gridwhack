package gridwhack.gui.unit.player;

import gridwhack.grid.GridUnit;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;

import java.awt.*;

/**
 * Experience display class file.
 * Allows for representing unit health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ExperienceDisplay extends GuiElement
{
	protected ExperienceBar experienceBar;
	protected ExperienceText experienceText;

	/**
	 * Creates the display.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the unit this bar belongs to.
	 */
	public ExperienceDisplay(int x, int y, GridUnit owner)
	{
		super(x, y, 170, 20);

		setFont( Gui.getInstance().getWindow().getFont() );
		setTextColor(Color.white);
		
		// create a health bar and text to represent the player health.
		experienceBar = new ExperienceBar(x, y, 100, 10, owner);
		experienceText = new ExperienceText(x+experienceBar.getWidth()+10, y, font, owner);
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
		experienceBar.render(g);
		experienceText.render(g);
	}
}
