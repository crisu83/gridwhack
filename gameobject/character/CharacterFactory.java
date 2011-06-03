package gridwhack.gameobject.character;

import gridwhack.gameobject.character.Character.CharacterType;
import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.character.hostile.Imp;
import gridwhack.gameobject.character.hostile.Kobold;
import gridwhack.gameobject.character.hostile.Orc;
import gridwhack.gameobject.character.hostile.Skeleton;
import gridwhack.gameobject.character.player.Player;
import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.grid.*;
import gridwhack.gameobject.unit.Unit;
import gridwhack.util.Vector2;

/**
 * Character factory class.
 * Provides the functionality for spawning characters into the game.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CharacterFactory implements IGridGameObjectFactory
{
	// ----------
	// Properties
	// ----------

	private static final CharacterFactory instance = new CharacterFactory();

	// -------
	// Methods
	// -------

	/**
	 * Creates the factory.
	 * Private to enforce the singleton pattern.
	 */
	private CharacterFactory() {}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static CharacterFactory getInstance()
	{
		return instance;
	}

	/**
	 * Spawns a specific game object.
	 * @param type The game object type.
	 * @param grid The grid the game object belongs to.
	 * @return The game object.
	 * @throws InvalidGameObjectException If the game object could not be created.
	 */
	public GridGameObject spawn(IGameObjectType type, int gx, int gy, Grid grid) throws InvalidGameObjectException
	{
		GridGameObject object;

		switch ((CharacterType) type)
		{
			case IMP:
				object = new Imp();
				break;

			/*
			case KOBOLD:
				object = new Kobold();
				break;
			*/

			/*
			case ORC:
				object = new Orc();
				break;
			*/

			case PLAYER:
				object = new Player();
				break;

			case SKELETON:
				object = new Skeleton();
				break;

			default:
				throw new InvalidGameObjectException("Invalid game object type.");
		}

		object.setGrid(grid);
		object.setGridPosition(new Vector2(gx, gy));
		object.init();

		return object;
	}
}
