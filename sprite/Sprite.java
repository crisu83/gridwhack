package gridwhack.sprite;

import gridwhack.base.BaseObject;
import gridwhack.gameobject.GameObject;
import gridwhack.render.IDrawable;
import gridwhack.render.RenderSystem;
import gridwhack.util.Vector2;

import java.awt.*;

/**
 * Sprite class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Sprite extends BaseObject implements IDrawable
{
	// ----------
	// Properties
	// ----------

	protected GameObject owner;
	protected Image image;
	protected Vector2 offset;
	protected int width;
	protected int height;

	// -------
	// Methods
	// -------

	/**
	 * Creates the sprite.
	 * @param image The image that represent the sprite.
	 */
	public Sprite(Image image)
	{
		super();

		this.image = image;

		offset = new Vector2();
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	/**
	 * Draws the object.
	 * @param g The graphics object
	 */
	public void draw(Graphics2D g)
	{
		Vector2 position = owner.getPosition();
		g.drawImage(getImage(), (int) position.x, (int) position.y, null);
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Updates this object
	 * @param parent The parent object.
	 */
	@Override
	public void update(BaseObject parent)
	{
		RenderSystem.getInstance().queueForDraw(this);
	}

	// -------------------
	// Getters and setters
	// -------------------	

	/**
	 * @param owner The game object this sprite belongs to.
	 */
	public void setOwner(GameObject owner)
	{
		this.owner = owner;
	}

	/**
	 * @return The image for this sprite.
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * @return The current image offset.
	 */
	public Vector2 getOffset()
	{
		return offset;
	}

	/**
	 * @param offset The new image offset
	 */
	public void setOffset(Vector2 offset)
	{
		this.offset = offset;
	}

	/**
	 * @return the width of this sprite.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return the height of this sprite.
	 */
	public int getHeight()
	{
		return height;
	}
}
