package gridwhack.gui.unit;

import java.awt.*;

import gridwhack.entity.unit.NPCUnit;
import gridwhack.entity.unit.event.IUnitListener;
import gridwhack.entity.unit.event.UnitEvent;
import gridwhack.entity.unit.player.event.IPlayerListener;
import gridwhack.entity.unit.player.event.PlayerEvent;
import gridwhack.grid.GridUnit;

/**
 * Health bar class.
 * Allows for visualizing unit health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthBar extends StatusBar implements IUnitListener, IPlayerListener
{
	/**
	 * Creates the bar.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the width of the bar.
	 * @param height the height of the bar.
	 * @param owner the unit this bar belongs to.
	 */
	public HealthBar(int x, int y, int width, int height, GridUnit owner)
	{
		super(x, y, width, height, false, owner);

		// Health bars should be red.
		setBarColor( new Color(205, 0, 0) );
	}
	
	/**
	 * Refreshes the bar.
	 */
	private void refresh()
	{
		int barWidth = calculateBarWidth(owner.getCurrentHealth(), owner.getMaximumHealth());
		
		// set the new current value.
		setBarWidth(barWidth);
	}
	
	/**
	 * Moves the bar.
	 */
	private void move()
	{
		// calculate the new position for the bar.
		int x = (int) Math.round(owner.getX()+1);
		int y = (int) Math.round(owner.getY()+1);
		
		// move the bar with the unit.
		super.move(x, y);
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
	public synchronized void onUnitMove(UnitEvent e)
	{		
		if( owner instanceof NPCUnit )
		{
			move();
		}
	}

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
		setBarWidth(width);
	}
}
