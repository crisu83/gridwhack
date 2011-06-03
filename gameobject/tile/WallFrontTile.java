package gridwhack.gameobject.tile;

import gridwhack.core.ImageLoader;

import java.util.Random;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class WallFrontTile extends Wall
{
	/**
	 * Creates the tile.
	 */
	public WallFrontTile()
	{
		super();

		int[] probability = {1, 1, 1, 1, 1, 1, 1, 1, 1, 2}; // 1 out of 10 for wall2 (torch)
		Random rand = new Random();
		setImage(ImageLoader.getInstance().loadImage("wall" + probability[rand.nextInt(probability.length)] + ".png"));
	}
}
