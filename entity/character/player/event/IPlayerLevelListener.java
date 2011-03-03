package gridwhack.entity.character.player.event;

import gridwhack.event.IEventListener;

/**
 * Player level listener interface file.
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
