package gridwhack.entity.sprite;

import java.awt.*;

public class CSprite 
{
	protected Image image;
	protected CAnimation animation;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	/**
	 * Constructs the sprite.
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
	 * @param width the width of the sprite.
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	/**
	 * @return the height of the sprite.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * @param height the height of the sprite.
	 */
	public void setHeight(int height)
	{
		this.height = height;
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
	 * @param the offset on the x-axis.
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * @param the offset on the y-axis.
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * @return the sprite image.
	 */
	public Image getImage()
	{
		return image;
	}
	
	/**
	 * Updates the sprite.
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
	 * Renders the sprite.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g, int x, int y)
	{
		g.drawImage(getImage(), x, y, x+width, y+height, getX(), getY(), getX()+width, getY()+height, null);
	}
}
