package gridwhack.gui.unit;

import java.awt.Color;

import gridwhack.entity.unit.event.UnitEvent;
import gridwhack.grid.GridUnit;

/**
 * Health status text class.
 * Allows for displaying unit health values in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
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
	public HealthText(int x, int y, GridUnit owner)
	{
		super(x, y, 11, owner);
		
		this.maximum = owner.getMaximumHealth();
		this.current = maximum;
		
		color = Color.white;
	}

	/**
	 * Refreshes the values.
	 */
	private void refresh()
	{
		current = owner.getCurrentHealth();
	}
	
	/**
	 * @return the health text.
	 */
	public String getText()
	{
		return current + " / " + maximum;
	}

	/**
	 * Actions to be taken when the unit dies.
	 * @param e the event.
	 */
	public synchronized void onUnitDeath(UnitEvent e) {}

	/**
	 * Actions to be taken when the unit is spawned.
	 * @param e the event.
	 */
	public synchronized void onUnitSpawn(UnitEvent e) {}

	/**
	 * Actions to be taken when the unit gains health.
	 * @param e the event.
	 */
	public synchronized void onUnitHealthGain(UnitEvent e)
	{
		refresh();
	}

	/**
	 * Actions to be taken when the unit loses health.
	 * @param e the event.
	 */
	public synchronized void onUnitHealthLoss(UnitEvent e)
	{
		refresh();
	}

	/**
	 * Actions to be taken when the unit moves.
	 * @param e the event.
	 */
	public synchronized void onUnitMove(UnitEvent e) {}
}
