package gridwhack.gui.unit;

import java.awt.*;

import gridwhack.entity.unit.event.IUnitListener;
import gridwhack.entity.unit.event.UnitEvent;
import gridwhack.entity.unit.player.event.IPlayerListener;
import gridwhack.entity.unit.player.event.PlayerEvent;
import gridwhack.grid.GridUnit;

/**
 * Health status text class file.
 * Allows for displaying unit health values in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthText extends StatusText implements IUnitListener, IPlayerListener
{
	protected int current;
	protected int maximum;
	
	/**
	 * Constructs the health text.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param font the font to use.
	 * @param owner the unit the health belongs to.
	 */
	public HealthText(int x, int y, Font font, GridUnit owner)
	{
		super(x, y, font, owner);
		
		this.maximum = owner.getMaximumHealth();
		this.current = maximum;

		// set the text to listen to its owner.
		owner.addListener(this);
	}

	/**
	 * Refreshes the values.
	 */
	private void refresh()
	{
		maximum = owner.getMaximumHealth();
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

	/**
	 * Actions to be taken when the player gains experience.
	 * @param e the event.
	 */
	public void onPlayerGainExperience(PlayerEvent e) {}

	/**
	 * Actions to be taken when the player is gains a level.
	 * @param e the event.
	 */
	public void onPlayerGainLevel(PlayerEvent e)
	{
		refresh();
	}
}
