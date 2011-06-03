package gridwhack.gameobject.tile;

import gridwhack.core.ImageLoader;
import gridwhack.fov.IViewer;
import gridwhack.path.IMover;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Arch extends Tile
{
	// -------
	// Methods
	// -------

	/**
	 * Creates the tile.
	 */
	public Arch()
	{
		super();

		setImage(ImageLoader.getInstance().loadImage("Arch1.png"));
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
		return true; // blocked for all game objects
	}

	/**
	 * Returns whether a specific game object can see through this tile.
	 * @param viewer The game object.
	 * @return whether The tile is solid.
	 */
	@Override
	public boolean isSolid(IViewer viewer)
	{
		return true; // solid for all game objects
	}
}
