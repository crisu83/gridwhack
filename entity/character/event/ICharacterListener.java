package gridwhack.entity.character.event;

import gridwhack.event.IEventListener;

/**
 * Character listener interface file.
 * All character listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface ICharacterListener extends IEventListener
{
	/**
	 * Actions to be taken when the character dies.
	 * @param e the event.
	 */
	public void onCharacterDeath(CharacterEvent e);
	
	/**
	 * Actions to be taken when the character is spawned.
	 * @param e the event.
	 */
	public void onCharacterSpawn(CharacterEvent e);
	
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
	
	/**
	 * Actions to be taken when the character moves.
	 * @param e the event.
	 */
	public void onCharacterMove(CharacterEvent e);
}
