package gridwhack.gameobject.character.event;

/**
 * Character move listener interface.
 * All character move listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterMoveListener extends ICharacterListener
{
	/**
	 * Actions to be taken when the character moves.
	 * @param e the event.
	 */
	public void onCharacterMove(CharacterEvent e);
}
