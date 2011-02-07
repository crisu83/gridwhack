package gridwhack.gui.unit;

import java.awt.Color;

import gridwhack.entity.unit.Unit;
import gridwhack.entity.unit.event.UnitEvent;

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
	 * @return the health text.
	 */
	public String getText()
	{
		return current + " / " + maximum;
	}
	
	private void update()
	{
		current = owner.getCurrentHealth();
	}

	@Override
	public void onUnitDeath(UnitEvent e) {}

	@Override
	public void onUnitSpawn(UnitEvent e) {}

	@Override
	public void onUnitHealthGain(UnitEvent e) 
	{
		update();
	}

	@Override
	public void onUnitHealthLoss(UnitEvent e) 
	{
		update();		
	}

	@Override
	public void onUnitMove(UnitEvent e) {}
}
