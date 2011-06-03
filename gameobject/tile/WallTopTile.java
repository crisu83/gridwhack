package gridwhack.gameobject.tile;

import gridwhack.core.ImageLoader;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class WallTopTile extends Wall
{
	/**
	 * Creates the tile.
	 */
	public WallTopTile()
	{
		super();

		setImage(ImageLoader.getInstance().loadImage("walltop.png"));
	}
}
