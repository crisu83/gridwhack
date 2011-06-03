package gridwhack.gameobject.character.player.event;

/**
 * Player experience listener interface.
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
