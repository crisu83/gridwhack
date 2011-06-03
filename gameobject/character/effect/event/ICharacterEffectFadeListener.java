package gridwhack.gameobject.character.effect.event;

/**
 * Character effect fade listener interface.
 * All character effect fade listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterEffectFadeListener extends ICharacterEffectListener
{
	/**
	 * Actions to be taken when a character effect fades.
	 * @param e the event.
	 */
	public void onCharacterEffectFade(CharacterEffectEvent e);
}
