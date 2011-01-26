package gridwhack.gui.unit;

import java.awt.*;

import gridwhack.CEvent;
import gridwhack.entity.unit.NonPlayerUnit;
import gridwhack.entity.unit.Unit;

/**
 * Health bar class.
 * Provides functionality for representing unit health visually.
 */
public class HealthBar extends StatusBar
{
	/**
	 * Constructs the bar.
	 * @param width the width of the bar.
	 * @param height the height of the bar.
	 * @param owner the unit this bar belongs to.
	 */
	public HealthBar(int x, int y, int width, int height, Unit owner)
	{
		super(x, y, width, height, false, owner);
		
		// health bars should be red.
		color = Color.red;
	}
	
	/**
	 * Handles incoming events.
	 * @param e the event.
	 */
	public void handleEvent(CEvent e) 
	{
		String type = e.getType();
		Object source = e.getSource();
		
		// handle unit events.
		if( source instanceof Unit )
		{
			Unit unit = (Unit)source;
			
			// actions to be taken when unit gains or loses health.
			if( type=="healthGain" || type=="healthLoss" )
			{
				// calculate how many percent the units current health is of its maximum health.
				float healthPercent = (float)unit.getCurrentHealth() / (float)unit.getMaximumHealth();
				
				// calculate the new width for the health bar.
				float barWidth = healthPercent * width;
				
				// set the new current value.
				setBarWidth(Math.round(barWidth));
			}
		}
		
		// handle non-player unit events.
		if( source instanceof NonPlayerUnit )
		{
			NonPlayerUnit unit = (NonPlayerUnit)source;
			
			// actions to be taken when unit moves.
			if( type=="move" )
			{
				// calculate the new position for the bar.
				int x = (int)Math.round(unit.getX()+1);
				int y = (int)Math.round(unit.getY()+1);
				
				// move the bar with the unit.
				move(x, y);
			}
		}
	}
}
