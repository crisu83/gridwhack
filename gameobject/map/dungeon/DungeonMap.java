package gridwhack.gameobject.map.dungeon;

import gridwhack.base.BaseCollection;
import gridwhack.fov.IViewer;
import gridwhack.gameobject.character.*;
import gridwhack.gameobject.character.hostile.HostileCharacter.HostileType;
import gridwhack.gameobject.character.hostile.HostileFactory;
import gridwhack.gameobject.character.player.Player;
import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.grid.GridGameObject;
import gridwhack.gameobject.map.Map;
import gridwhack.gameobject.tile.Tile.TileType;
import gridwhack.gameobject.tile.TileFactory;
import gridwhack.util.Vector2;

import java.util.Random;

/**
 * Dungeon map class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class DungeonMap extends Map
{
	// -------
	// Methods
	// -------

	/**
	 * Creates the map.
	 * @param width The map width.
	 * @param height The map height.
	 */
	public DungeonMap(int width, int height)
	{
		super(width, height);
	}

	/**
	 * Initializes the map.
	 */
	public void init()
	{
		Random rand = new Random();

		// Lets add some tiles...

		TileFactory tileFactory = TileFactory.getInstance();
		BaseCollection tiles = new BaseCollection();
		int width = getWidth() - 3;
		int height = getHeight() - 2;

		try
		{
			for (int x = 3; x < width; x++)
			{
				tiles.add(tileFactory.spawn(TileType.WALL, x, 3));

				for (int y = 4; y < height; y++)
				{
					tiles.add(tileFactory.spawn(TileType.FLOOR, x, y));
				}
			}

			tiles.add(tileFactory.spawn(TileType.ARCH, 4, 2));
			tiles.add(tileFactory.spawn(TileType.STAIRS_UP, 4, 3));
			tiles.add(tileFactory.spawn(TileType.ARCH, width - 2, 2));
			tiles.add(tileFactory.spawn(TileType.STAIRS_DOWN, width - 2, 3));
		}
		catch (InvalidGameObjectException e)
		{
			e.printStackTrace();
		}

		tiles.applyChanges();
		grid.addTiles(tiles);

		// Then lets add some hostiles...

		BaseCollection characters = new BaseCollection();
		HostileType[] hostileTypes = HostileType.values();
		int hostileCount = 20;

		try
		{
			for (int i = 0; i < hostileCount; i++)
			{
				// Determine the hostile type.
				int hostileType = rand.nextInt(hostileTypes.length);
				int gx, gy;
				GridGameObject character;

				// Determine the position.
				do
				{
					gx = rand.nextInt(width - 2) + 1;
					gy = rand.nextInt(height - 3) + 2;
					character = HostileFactory.getInstance().spawn(hostileTypes[hostileType], gx, gy, grid);
				}
				while (grid.isSolid(gx, gy, (IViewer) character));

				characters.add(character);
			}
		}
		catch (InvalidGameObjectException e)
		{
			e.printStackTrace();
		}

		characters.applyChanges();
		grid.addCharacters(characters);

		// Then lets spawn the player...

		Player player = new Player();
		player.setGrid(grid);
		player.init();

		int gx, gy;

		do
		{
			gx = rand.nextInt(width - 2) + 1;
			gy = rand.nextInt(height - 3) + 2;
			player.setGridPosition(new Vector2(gx, gy));
		}
		while (grid.isSolid(gx, gy, player));

		grid.setPlayer(player);
	}
}
