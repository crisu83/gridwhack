package gridwhack.gameobject.map;

import gridwhack.gameobject.character.Character;
import gridwhack.gameobject.grid.GridCell;
import gridwhack.gameobject.tile.Tile;
import gridwhack.util.Vector2;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class DungeonTestMap extends Map
{
	/**
	 * Creates the map.
	 * @param width The map width in grid cells.
	 * @param height The map width in grid cells.
	 */
	public DungeonTestMap(int width, int height)
	{
		super(width, height);
	}

	/**
	 * Initializes the map.
	 */
	public void init()
	{
		/*
		// Spawn some terrain.
		grid.createTileRect(0, 0, 60, 30, Tile.TileType.FLOOR);

		for (int i = 0, wallCount = 100; i < wallCount; i++)
		{
			GridCell cell = grid.getRandomCell();

			if (cell != null)
			{
				Vector2 position = cell.getPosition();
				grid.createTile((int) position.x, (int) position.y, Tile.TileType.WALL_TOP);
			}
		}

		// Spawn some hostiles.
		grid.createUnit(gridwhack.gameobject.character.Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.ORC);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.KOBOLD);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON);
		grid.createUnit(Character.CharacterType.SKELETON); // 30 in total
		*/
	}
}
