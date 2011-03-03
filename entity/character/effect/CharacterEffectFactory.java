package gridwhack.entity.character.effect;

import gridwhack.exception.ComponentNotFoundException;
import gridwhack.entity.character.Character;

/**
 * Character factory class file.
 * Allows for creating characters.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEffectFactory
{
	/**
	 * Returns new characters effects of the given type.
	 * @param type the character effect type.
	 * @param subject the character the effect belongs to.
	 * @return the character effect.
	 */
	public static CharacterEffect factory(CharacterEffect.Type type, Character subject) throws ComponentNotFoundException
	{
		CharacterEffect effect = null;

		// Return the requested type of character effect.
		switch( type )
		{
			case DOUBLE_DAMAGE:
				effect = new DoubleDamage(subject);
				break;

			default:
				throw new ComponentNotFoundException("Failed to create character effect, type '" + type + "' is invalid!");
		}

		// Initialize the effect.
		effect.init();

		return effect;
	}
}
