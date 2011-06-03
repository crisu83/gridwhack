package gridwhack.sprite;

import gridwhack.core.ImageLoader;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Core sprite manager class file.
 * Allows for caching of sprites in a map.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class SpriteManager
{
	// ----------
	// Properties
	// ----------

	private static Map<String, Sprite> sprites = new HashMap<String, Sprite>();

	// -------
	// Methods
	// -------

	/**
	 * Returns a specific sprite.
	 * @param filename The filename of the sprite.
	 * @return The sprite.
	 */
	public static Sprite getSprite(String filename)
	{
		Sprite sprite = sprites.get(filename);

		if (sprite == null)
		{
			Image image = ImageLoader.getInstance().loadImage(filename);
			sprite = new Sprite(image);
			sprites.put(filename, sprite);
		}

		return sprite;
	}
}
