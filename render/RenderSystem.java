package gridwhack.render;

import gridwhack.base.BaseObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class RenderSystem extends BaseObject
{
	private static final RenderSystem instance = new RenderSystem();
	private ArrayList<IDrawable> drawQueue;

	/**
	 * Creates the factory.
	 * Private to enforce the singleton pattern.
	 */
	private RenderSystem()
	{
		super();

		drawQueue = new ArrayList<IDrawable>();
	}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static RenderSystem getInstance()
	{
		return instance;
	}

	/**
	 * Queues a bitmap to for drawing.
	 */
	public void queueForDraw(IDrawable drawable)
	{
		drawQueue.add(drawable);
	}

	/**
	 * Draws a single frame.
	 * This method is called by the game thread.
	 * @param g The graphics object.
	 */
	public void drawFrame(Graphics2D g)
	{
		synchronized (this)
		{
			final int drawCount = drawQueue.size();

			if (drawCount > 0)
			{
				for (int i = 0; i < drawCount; i++)
				{
					drawQueue.get(i).draw(g);
				}
			}
		}
	}
}
