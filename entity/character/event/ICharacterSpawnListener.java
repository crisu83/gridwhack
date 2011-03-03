package gridwhack.entity.character.event;

import gridwhack.event.IEventListener;

/**
 * Character listener interface file.
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
