package gridwhack.gameobject.character.effect;

import gridwhack.exception.InvalidObjectException;
import gridwhack.gameobject.character.Character;

/**
 * Character factory class.
 * Allows for creating character effects.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEffectFactory
{
	/**
	 * Returns new characters effects of the given type.
	 * @param type The character effect type.
	 * @param subject The character the effect belongs to.
	 * @return The character effect.
	 */
	public static CharacterEffect factory(CharacterEffect.Type type, Character subject) throws InvalidObjectException
	{
		CharacterEffect effect = null;

		// Return the requested type of character effect.
		switch( type )
		{
			case DOUBLE_DAMAGE:
				effect = new DoubleDamage(subject);
				break;

			default:
				throw new InvalidObjectException("Failed to spawn character effect, type '" + type + "' is invalid!");
		}

		// Initialize the effect.
		effect.init();

		return effect;
	}
}
