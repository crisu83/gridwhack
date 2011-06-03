package gridwhack.gameobject.sprite;

import gridwhack.gameobject.DrawableGameObject;
import gridwhack.util.Vector2;

import java.awt.*;

/**
 * Sprite class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Sprite extends DrawableGameObject
{
	// ----------
	// Properties
	// ----------

	private Image image;

	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 */
	public Sprite()
	{
		super();
	}

	// ------------------
	// Overridden methods
	// ------------------
	
	/**
	 * Draws the object.
	 * @param g The graphics context.
	 */
	@Override
	public void draw(Graphics2D g)
	{
		if (image != null)
		{
			Vector2 position = getPosition();
			g.drawImage(image, (int) position.x, (int) position.y, null);
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param image The image that represents the sprite.
	 */
	public void setImage(Image image)
	{
		this.image = image;
	}

	/**
	 * @return The width of this sprite.
	 */
	public int getWidth()
	{
		return image != null ? image.getWidth(null) : 0;
	}

	/**
	 * @return The height of this sprite.
	 */
	public int getHeight()
	{
		return image != null ? image.getHeight(null) : 0;
	}
}
