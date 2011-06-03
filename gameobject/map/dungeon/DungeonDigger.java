package gridwhack.gameobject.map.dungeon;

import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.map.dungeon.DungeonFeature.DungeonFeatureType;
import gridwhack.base.BaseCollection;
import gridwhack.base.BaseObject;
import gridwhack.base.SortedCollection;
import gridwhack.gameobject.grid.Grid;
import gridwhack.util.SortedArrayList;
import gridwhack.util.Vector2;
import sun.plugin.dom.css.Rect;

import java.awt.*;
import java.util.Random;

/**
 * Dungeon digger class.
 * Allows for digging dungeons.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class DungeonDigger extends BaseObject
{
	// ----------
	// Properties
	// ----------

	private DungeonMap map;
	private BaseCollection rooms;
	private BaseCollection tiles;
	private Random rand;

	// -------
	// Methods
	// -------

	/**
	 * Creates the digger.
	 */
	public DungeonDigger(DungeonMap map)
	{
		super();

		this.map = map;

		tiles = new BaseCollection();
		rooms = new BaseCollection();
		rand = new Random();
	}

	public BaseCollection randomize()
	{
		digRooms();

		final int roomCount = rooms.getSize();

		if (roomCount > 0)
		{
			for (int i = 0; i < roomCount; i++)
			{
				DungeonRoom room = (DungeonRoom) rooms.get(i);
				BaseCollection roomTiles = room.getTiles();

				for (int j = 0, length = roomTiles.getSize(); j < length; j++)
				{
					tiles.add(roomTiles.get(j));
				}
			}
		}

		tiles.applyChanges();

		return tiles;
	}

	public void digRooms()
	{
		DungeonRoom room = createRoom(5, 5, 10, 10);
		rooms.add(room);

		DungeonRoom room2 = createRoom(30, 10, 10, 10);
		rooms.add(room2);

		rooms.applyChanges();

		System.out.println(isRectangleBlocked(2, 2, 1, 1));
	}

	public DungeonRoom createRoom(int x, int y, int width, int height)
	{
		DungeonRoom room = null;

		try
		{
			room = (DungeonRoom) DungeonFeatureFactory.getInstance()
					.spawn(DungeonFeatureType.ROOM, x, y, width, height);
		}
		catch (InvalidGameObjectException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		return room;
	}

	public boolean isRectangleBlocked(int x, int y, int width, int height)
	{
		int left = x;
		int right = left + width;
		int top = y;
		int bottom = top + height;

		for (int i = 0, count = rooms.getSize(); i < count; i++)
		{
			DungeonRoom room = (DungeonRoom) rooms.get(i);

			Vector2 position = room.getPosition();
			Vector2 dimension = room.getDimension();

			int roomLeft = (int) position.x - 1;
			int roomRight = (int) (roomLeft + dimension.x) + 1;
			int roomTop = (int) position.y - 1;
			int roomBottom = (int) (roomTop + dimension.y) + 1;

			if (left >= roomLeft || right <= roomRight || top >= roomTop || bottom <= roomBottom)
			{
				return true;
			}
		}

		return false;
	}
}
