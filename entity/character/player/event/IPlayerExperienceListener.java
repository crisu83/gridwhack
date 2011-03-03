package gridwhack.entity.character.player.event;

import gridwhack.event.IEventListener;

/**
 * Player experience listener interface file.
 * All player experience listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IPlayerExperienceListener extends IPlayerListener
{
	/**
	 * Actions to be taken when the player gains experience.
	 * @param e the event.
	 */
	public void onPlayerGainExperience(PlayerEvent e);
}
