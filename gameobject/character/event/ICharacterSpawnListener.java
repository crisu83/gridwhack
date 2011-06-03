package gridwhack.gameobject.character.event;

/**
 * Character spawn listener interface.
 * All character listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterSpawnListener extends ICharacterListener
{
	/**
	 * Actions to be taken when the character spawns.
	 * @param e the event.
	 */
	public void onCharacterSpawn(CharacterEvent e);
}
