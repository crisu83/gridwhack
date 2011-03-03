package gridwhack.entity.character.effect.event;

/**
 * Character effect apply listener interface file.
 * All character effect apply listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterEffectApplyListener extends ICharacterEffectListener
{
	/**
	 * Actions to be taken when a character effect is applied.
	 * @param e the event.
	 */
	public void onCharacterEffectApply(CharacterEffectEvent e);
}
