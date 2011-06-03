package gridwhack.gameobject.tile;

import gridwhack.core.ImageLoader;
import gridwhack.fov.IViewer;
import gridwhack.gameobject.character.player.Player;
import gridwhack.path.IMover;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class StairsUp extends Tile
{
	// -------
	// Methods
	// -------

	/**
	 * Creates the tile.
	 */
	public StairsUp()
	{
		super();

		setImage(ImageLoader.getInstance().loadImage("StairsUp1.png"));
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
		if (mover instanceof Player)
		{
			return false;
		}

		return true;
	}

	/**
	 * Returns whether a specific game object can see through this tile.
	 * @param viewer The game object.
	 * @return whether The tile is solid.
	 */
	@Override
	public boolean isSolid(IViewer viewer)
	{
		return false; // solid for all game objects
	}
}
