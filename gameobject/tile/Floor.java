package gridwhack.gameobject.tile;

import gridwhack.core.ImageLoader;
import gridwhack.fov.IViewer;
import gridwhack.gameobject.grid.Grid;
import gridwhack.path.IMover;

import java.util.Random;

/**
 * Floor tile class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Floor extends Tile
{
	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 */
	public Floor()
	{
		super();

		int[] probability = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2}; // 1 out of 15 for floor2 (cracked)
		Random rand = new Random();
		setImage(ImageLoader.getInstance().loadImage("floor" + probability[rand.nextInt(probability.length)] + ".png"));
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Returns whether a specific game object can exist on this tile.
	 * @param mover The game object.
	 * @return whether The tile is blocked.
	 */
	@Override
	public boolean isBlocked(IMover mover)
	{
		return false; // not blocked for any entities
	}

	/**
	 * Returns whether a specific game object can see through this tile.
	 * @param viewer The game object.
	 * @return whether The tile is solid.
	 */
	@Override
	public boolean isSolid(IViewer viewer)
	{
		return false; // not solid for any game objects.
	}
}