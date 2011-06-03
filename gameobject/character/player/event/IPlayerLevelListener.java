package gridwhack.gameobject.character.player.event;

/**
 * Player level listener interface.
 * All player level listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IPlayerLevelListener extends IPlayerListener
{
	/**
	 * Actions to be taken when the player is gains a level.
	 * @param e the event.
	 */
	public void onPlayerGainLevel(PlayerEvent e);
}
