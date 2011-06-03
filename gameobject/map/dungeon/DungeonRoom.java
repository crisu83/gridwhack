package gridwhack.gameobject.map.dungeon;

import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.tile.Tile.TileType;
import gridwhack.gameobject.tile.TileFactory;
import gridwhack.util.Vector2;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class DungeonRoom extends DungeonFeature
{
	/**
	 * Creates the feature.
	 */
	public DungeonRoom(int x, int y, int width, int height)
	{
		super();

		setPosition(new Vector2(x, y));
		setDimension(new Vector2(width, height));
	}

	public void init()
	{
		int sx = (int) position.x;
		int sy = (int) position.y;
		int mx = sx + (int) dimension.x;
		int my = sy + (int) dimension.y;

		try
		{
			for (int x = sx; x < mx; x++)
			{
				tiles.add(TileFactory.getInstance().spawn(TileType.WALL, x, sy - 1));

				for (int y = sy; y < my; y++)
				{
					tiles.add(TileFactory.getInstance().spawn(TileType.FLOOR, x, y));
				}
			}
		}
		catch (InvalidGameObjectException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		tiles.applyChanges();
	}
}
