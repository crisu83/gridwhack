package gridwhack.entity.sprite;

import java.util.ArrayList;

/**
 * Core animation class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CAnimation 
{
	private ArrayList<Frame> frames;
	private int frameIndex;
	private long currentTime;
	private long totalTime;
	
	/**
	 * Creates the animation.
	 */
	public CAnimation()
	{
		frames = new ArrayList<Frame>();
		totalTime = 0;
		start();
	}
	
	/**
	 * Adds a frame to the animation.
	 * @param offset the offset on the x-axis.
	 * @param duration the time the frame will be displayed.
	 */
	public synchronized void addFrame(int offset, long duration)
	{
		totalTime += duration;
		frames.add(new Frame(offset, totalTime));
	}
	
	/**
	 * Starts the animation from the beginning.
	 */
	public synchronized void start()
	{
		currentTime = 0;
		frameIndex = 0;
	}
	
	/**
	 * Changes the frame in the animation.
	 * @param timePassed the time that has passed.
	 */
	public synchronized void update(long timePassed)
	{
		// make sure we have more than one frame.
		if( frames.size()>1 )
		{
			currentTime += timePassed;
			
			// check if we have reached the end of the animation
			// and need to restart it.
			if( currentTime>=totalTime )
			{
				start();
			}
			
			// display each scene as long as necessary
			// and move on to the next scene.
			while( currentTime>getFrame(frameIndex).endTime )
			{
				frameIndex++;
			}
		}
	}
	
	/**
	 * Returns the current offset on the x-axis.
	 * @return the offset.
	 */
	public synchronized int getFrameOffset()
	{
		// make sure we have scenes.
		if( frames.size()>0 )
		{
			// return the image for the current scene.
			return getFrame(frameIndex).offset;
		}
		// we do not have any scenes.
		else
		{
			return 0;
		}
	}
	
	/**
	 * Returns the frame with the given index.
	 * @param index the frame index.
	 * @return the frame object.
	 */
	private Frame getFrame(int index)
	{
		return (Frame)frames.get(index);
	}
	
	/**
	 * Private inner class that represents a single frame.
	 */
	private class Frame
	{
		int offset;
		long endTime;
		
		/**
		 * Creates the scene.
		 * @param offset the offset on the x-axis.
		 * @param endTime the runtime for the scene.
		 */
		public Frame(int offset, long endTime)
		{
			this.offset = offset;
			this.endTime = endTime;
		}
	}
}