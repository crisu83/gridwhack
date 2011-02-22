package gridwhack.entity.unit.player.event;

import gridwhack.event.IEventListener;

/**
 * Player listener interface file.
 * All unit listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IPlayerListener extends IEventListener
{
	/**
	 * Actions to be taken when the player gains experience.
	 * @param e the event.
	 */
	public void onPlayerGainExperience(PlayerEvent e);
	
	/**
	 * Actions to be taken when the player is gains a level.
	 * @param e the event.
	 */
	public void onPlayerGainLevel(PlayerEvent e);
}
