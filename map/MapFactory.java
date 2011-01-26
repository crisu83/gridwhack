package gridwhack.map;

public class MapFactory 
{
	public static enum MapType {
		DUNGEON,
		RANDOM_DUNGEON
	}
	
	public static GridMap factory(MapType type)
	{
		// return the requested type of unit.
		switch( type )
		{
		case DUNGEON:
			return new DungeonMap();
		case RANDOM_DUNGEON:
			return new RandomDungeonMap();
		default:
			return null;
		}
	}
}
