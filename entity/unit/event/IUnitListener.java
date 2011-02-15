package gridwhack.entity.unit.event;

import gridwhack.event.IEventListener;

/**
 * Unit listener interface file.
 * All unit listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IUnitListener extends IEventListener 
{
	/**
	 * Actions to be taken when the unit dies.
	 * @param e the event.
	 */
	public void onUnitDeath(UnitEvent e);
	
	/**
	 * Actions to be taken when the unit is spawned.
	 * @param e the event.
	 */
	public void onUnitSpawn(UnitEvent e);
	
	/**
	 * Actions to be taken when the unit gains health.
	 * @param e the event.
	 */
	public void onUnitHealthGain(UnitEvent e);
	
	/**
	 * Actions to be taken when the unit loses health.
	 * @param e the event.
	 */
	public void onUnitHealthLoss(UnitEvent e);
	
	/**
	 * Actions to be taken when the unit moves.
	 * @param e the event.
	 */
	public void onUnitMove(UnitEvent e);
}
