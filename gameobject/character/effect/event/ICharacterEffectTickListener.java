package gridwhack.gameobject.character.effect.event;

/**
 * Character effect apply listener interface.
 * All character effect apply listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterEffectTickListener extends ICharacterEffectListener
{
	/**
	 * Actions to be taken when a character effect ticks.
	 * @param e the event.
	 */
	public void onCharacterEffectTick(CharacterEffectEvent e);
}
