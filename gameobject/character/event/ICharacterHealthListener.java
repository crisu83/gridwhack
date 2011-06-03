package gridwhack.gameobject.character.event;

/**
 * Character health listener interface.
 * All character health listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterHealthListener extends ICharacterListener
{
	/**
	 * Actions to be taken when the character gains health.
	 * @param e the event.
	 */
	public void onCharacterHealthGain(CharacterEvent e);
	
	/**
	 * Actions to be taken when the character loses health.
	 * @param e the event.
	 */
	public void onCharacterHealthLoss(CharacterEvent e);
}
