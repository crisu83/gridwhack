package gridwhack.entity.character.effect.event;

import gridwhack.event.IEventListener;

/**
 * Character effect listener interface file.
 * All character effect listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterEffectListener extends IEventListener
{
	/**
	 * Actions to be taken when a character effect is removed.
	 * @param e the event.
	 */
	public void onCharacterEffectRemove(CharacterEffectEvent e);
}
