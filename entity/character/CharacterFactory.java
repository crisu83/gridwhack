package gridwhack.entity.character;

import gridwhack.entity.character.hostile.Kobold;
import gridwhack.entity.character.hostile.Orc;
import gridwhack.entity.character.hostile.Skeleton;
import gridwhack.entity.character.player.Player;
import gridwhack.grid.Grid;
import gridwhack.grid.GridUnit;

/**
 * Character factory class file.
 * Allows for creating characters.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterFactory
{
	/**
	 * Returns new characters of the given type.
	 * @param type the character type.
	 * @param grid the grid the character belongs to.
	 * @return the character.
	 */
	public static Character factory(Character.Type type, Grid grid) throws ClassNotFoundException
	{
		Character character = null;

		// return the requested type of character.
		switch( type )
		{
			case PLAYER:
				character = new Player(grid);
				break;

			case ORC:
				character = new Orc(grid);
				break;

			case KOBOLD:
				character = new Kobold(grid);
				break;

			case SKELETON:
				character = new Skeleton(grid);
				break;

			default:
				throw new ClassNotFoundException("Failed to create character, type '" + type + "' is invalid!");
		}

		// Initialize the character.
		character.init();

		return character;
	}
}
