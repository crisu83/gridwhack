package gridwhack;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class CScreenManager
{
	private GraphicsDevice gd;
	
	/**
	 * Constructs the screen manager.
	 */
	public CScreenManager()
	{
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = env.getDefaultScreenDevice();
	}
	
	/**
	 * @return all the compatible display modes for the graphics card.
	 */
	public DisplayMode[] getCompatibleDisplayModes()
	{
		return gd.getDisplayModes();
	}
	
	/**
	 * Returns the first display mode that is compatible with the graphics card.
	 * @param modes the display modes.
	 * @return the compatible display mode.
	 */
	public DisplayMode getCompatibleDisplayMode(DisplayMode modes[])
	{
		// get the modes supported by the graphics card.
		DisplayMode compatibleModes[] = gd.getDisplayModes();
		
		// loop through the modes and return the first compatible mode.
		for( int i=0; i<modes.length; i++ )
		{
			for( int j=0; i<compatibleModes.length; j++ )
			{
				if( displayModesMatch(modes[i], compatibleModes[j]) )
				{
					// compatible mode found, return it.
					return modes[i];
				}
			}
		}
		
		// no compatible display mode is available.
		return null;
	}
	
	/**
	 * @return the current display mode.
	 */
	public DisplayMode getCurrentDisplayMode()
	{
		return gd.getDisplayMode();
	}
	
	/**
	 * Compares two display modes.
	 * @param m1 the first display mode to compare.
	 * @param m2 the second display mode to compare.
	 * @return whether the display modes match.
	 */
	public boolean displayModesMatch(DisplayMode m1, DisplayMode m2)
	{
		// compare the resolution.
		if( m1.getWidth()!=m2.getWidth() && m1.getHeight()!=m2.getHeight() )
		{
			// resolutions do not match.
			return false;
		}
		
		// compare the bit depth.
		if( m1.getBitDepth()!=DisplayMode.BIT_DEPTH_MULTI 
			&& m1.getBitDepth()!=DisplayMode.BIT_DEPTH_MULTI 
			&& (m1.getBitDepth()!=m2.getBitDepth()) )
		{
			// bit depths do not match.
			return false;
		}
		
		// compare the refresh rates.
		if( m1.getRefreshRate()!=DisplayMode.REFRESH_RATE_UNKNOWN 
			&& m2.getRefreshRate()!=DisplayMode.REFRESH_RATE_UNKNOWN
			&& m1.getRefreshRate()!=m2.getRefreshRate() )
		{
			// refresh rates do not match.
			return false;
		}
			
		// display modes match.
		return true;
	}
	
	/**
	 * Sets the application to full screen mode.
	 * @param dm the display mode.
	 */
	public void setFullScreen(DisplayMode dm)
	{
		DisplayMode originalDisplayMode = getCurrentDisplayMode();
		
		// create a new frame and set it to full screen.
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setIgnoreRepaint(true);
		f.setResizable(false);
		gd.setFullScreenWindow(f);
		
		// make sure that we have a display mode
		// and that we actually can change it.
		if( dm!=null && gd.isDisplayChangeSupported() )
		{
			try
			{
				gd.setDisplayMode(dm);
			}
			finally
			{
				gd.setDisplayMode(originalDisplayMode);
			}
			
			f.createBufferStrategy(2);
		}
	}
	
	/**
	 * @return the graphics object.
	 */
	public Graphics2D getGraphics()
	{
		Window w = gd.getFullScreenWindow();
		
		// make sure that we have a window.
		if( w!=null )
		{
			BufferStrategy s = w.getBufferStrategy();
			return (Graphics2D)s.getDrawGraphics();
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Updates the display.
	 */
	public void update()
	{
		Window w = gd.getFullScreenWindow();
		
		if( w!=null )
		{
			BufferStrategy s = w.getBufferStrategy();
			
			// make sure that we have contents to display.
			if( !s.contentsLost() )
			{
				s.show();
			}
		}
	}
	
	/**
	 * @return the full screen window.
	 */
	public Window getFullScreenWindow()
	{
		return gd.getFullScreenWindow();
	}
	
	/**
	 * @return the width of the window.
	 */
	public int getWidth()
	{
		Window w = gd.getFullScreenWindow();
	
		// make sure that we have a window.
		if( w!=null )
		{
			return w.getWidth();
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * @return the height of the window.
	 */
	public int getHeight()
	{
		Window w = gd.getFullScreenWindow();
		
		// make sure that we have a window.
		if( w!=null )
		{
			return w.getHeight();
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * Exits from full screen mode.
	 */
	public void restoreScreen()
	{
		Window w = gd.getFullScreenWindow();
		
		// make sure that we have a window and dispose of it.
		if( w!=null )
		{
			w.dispose();
		}
		
		gd.setFullScreenWindow(null);
	}
	
	/**
	 * Creates a compatible image.
	 * @param width image width.
	 * @param height image height.
	 * @param transparency image transparency.
	 * @return the compatible image.
	 */
	public BufferedImage createCompatibleImage(int width, int height, int transparency)
	{
		Window w = gd.getFullScreenWindow();
		
		// make sure that we have a window.
		if( w!=null )
		{
			GraphicsConfiguration gc = w.getGraphicsConfiguration();
			return gc.createCompatibleImage(width, height, transparency);
		}
		// window is null.
		else
		{
			return null;
		}
	}
}
