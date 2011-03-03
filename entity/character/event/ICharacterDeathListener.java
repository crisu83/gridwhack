package gridwhack.entity.character.event;

/**
 * Character death listener interface file.
 * All character death listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterDeathListener extends ICharacterListener
{
	/**
	 * Actions to be taken when the character dies.
	 * @param e the event.
	 */
	public void onCharacterDeath(CharacterEvent e);
}
