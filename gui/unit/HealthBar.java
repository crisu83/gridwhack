package gridwhack.gui.unit;

import java.awt.*;

import gridwhack.entity.unit.NonPlayerUnit;
import gridwhack.entity.unit.Unit;
import gridwhack.entity.unit.event.UnitEvent;

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
	 * Updates the bar.
	 */
	private void update()
	{
		// calculate how many percent the units current health is of its maximum health.
		float healthPercent = (float)owner.getCurrentHealth() / (float)owner.getMaximumHealth();
		
		// calculate the new width for the health bar.
		float barWidth = healthPercent * width;
		
		// set the new current value.
		setBarWidth(Math.round(barWidth));
	}
	
	/**
	 * Moves the bar.
	 */
	private void move()
	{
		// calculate the new position for the bar.
		int x = (int)Math.round(owner.getX()+1);
		int y = (int)Math.round(owner.getY()+1);
		
		// move the bar with the unit.
		super.move(x, y);
	}

	public void onUnitDeath(UnitEvent e) {}

	public void onUnitSpawn(UnitEvent e) {}

	public void onUnitHealthGain(UnitEvent e)
	{
		update();
	}

	public void onUnitHealthLoss(UnitEvent e)
	{
		update();
	}

	public void onUnitMove(UnitEvent e)
	{		
		if( owner instanceof NonPlayerUnit )
		{
			move();
		}
	}
}
