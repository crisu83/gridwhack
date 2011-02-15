package gridwhack.entity.sprite;

import java.awt.*;

/**
 * Core sprite class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CSprite 
{
	protected Image image;
	protected CAnimation animation;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	/**
	 * Creates the sprite.
	 * @param image the image that represent the sprite.
	 */
	public CSprite(Image image)
	{
		this.image = image;
		this.x = 0;
		this.y = 0;
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}

	/**
	 * @return the width of the sprite.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * @return the height of the sprite.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @return the offset on the x-axis.
	 */
	public int getX()
	{
		// check if we have an animation.
		if( animation!=null )
		{
			return animation.getFrameOffset();
		}
		// we do not have an animation.
		else
		{
			return x;
		}
	}
	
	/**
	 * @return the offset on the y-axis.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Returns the image for this sprite.
	 * @return the image.
	 */
	public Image getImage()
	{
		return image;
	}
	
	/**
	 * Updates this sprite.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		// update the animation if necessary.
		if( animation!=null )
		{
			animation.update(timePassed);
		}
	}
	
	/**
	 * Renders this sprite.
	 * @param g the graphics context.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 */
	public void render(Graphics2D g, int x, int y)
	{
		g.drawImage(getImage(), x, y, x+width, y+height, getX(), getY(), getX()+width, getY()+height, null);
	}
}
