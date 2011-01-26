package gridwhack.gui.unit;

import java.awt.Color;

import gridwhack.CEvent;
import gridwhack.entity.unit.Unit;

/**
 * Health status text class.
 * Provides functionality for displaying unit health values.
 */
public class HealthText extends StatusText
{
	protected int current;
	protected int maximum;
	
	/**
	 * Constructs the health text.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param owner the unit the health belongs to.
	 */
	public HealthText(int x, int y, Unit owner) 
	{
		super(x, y, 11, owner);
		
		this.maximum = owner.getMaximumHealth();
		this.current = maximum;
		
		color = Color.white;
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
				current = unit.getCurrentHealth();
			}
		}
	}
	
	/**
	 * @return the health text.
	 */
	public String getText()
	{
		return current + " / " + maximum;
	}
}
