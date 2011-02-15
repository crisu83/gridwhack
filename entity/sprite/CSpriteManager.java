package gridwhack.entity.sprite;

import java.awt.*;
import java.util.HashMap;

import gridwhack.CImageLoader;

/**
 * Core sprite manager class file.
 * Allows for caching of sprites in a map.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CSpriteManager 
{
	/**
	 * Hash map to cache the sprite in.
	 */
	private static HashMap<String, CSprite> sprites = new HashMap<String, CSprite>();
	
	/**
	 * Returns the specified sprite.
	 * @param filename the name of the file to use for the sprite.
	 * @return the sprite.
	 */
	public static CSprite getSprite(String filename)
	{
		// check if we need to load the sprite.
		if( sprites.get(filename)==null )
		{
			Image image = CImageLoader.getInstance().getImage(filename);
			CSprite sprite = new CSprite(image);
			sprites.put(filename, sprite);
			return sprite;
		}
		// sprite is already loaded.
		else
		{
			return sprites.get(filename);
		}
	}
}
