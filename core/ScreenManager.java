package gridwhack.core;

import java.awt.*;

import javax.swing.JFrame;

/**
 * Screen manager class.
 * Allows for running applications in full-screen mode.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ScreenManager
{
	// ----------
	// Properties
	// ----------

	private JFrame app;
	private GraphicsDevice gd;

	// -------
	// Methods
	// -------

	/**
	 * Creates the screen manager.
	 * @param app the application that this screen manager belongs to.
	 */
	public ScreenManager(JFrame app)
	{
		this.app = app;
		
		// Get the graphics environment.
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		// Get the graphics device.
		gd = ge.getDefaultScreenDevice();
	}
	
	/**
	 * Initializes the full screen mode.
	 * @param bufferCount the amount of buffers to use with the buffer strategy.
	 */
	public void initFullScreen(int bufferCount) 
	{
		app.setUndecorated(true);
		app.setIgnoreRepaint(true); // repaint is not needed due to active rendering
		app.setResizable(false);
		
		// Make sure the full screen mode is supported.
		if (!gd.isFullScreenSupported())
		{
			System.out.println("Full-screen exclusive mode not supported!");
			System.exit(0);
		}
		
		// Change to full-screen.
		gd.setFullScreenWindow(app);
		
		showCurrentMode(); // show the current display modes
		
		// Create the buffer strategy.
		setBufferStrategy(bufferCount);
	}
	
	/**
	 * Creates the buffer strategy for the application.
	 * @param bufferCount the desired amount of buffers.
	 */
	public void setBufferStrategy(int bufferCount) 
	{
		try
		{
			// Create a buffer strategy with the desired amount of buffers.
			app.createBufferStrategy(bufferCount);
		}
		catch (Exception e)
		{
			// Could not spawn buffer strategy.
			System.out.println("Error while creating buffer strategy");
		}
		
		try
		{
			// Sleep a bit to allow for creating the buffer strategy.
			Thread.sleep(500); // 0.5 seconds
		}
		catch (InterruptedException e) {}
	}

	/**
	 * Shows the current display mode.
	 */
	private void showCurrentMode()
	{
		// Get the current display mode.
		DisplayMode dm = gd.getDisplayMode();
		
		System.out.println("Current display mode: (" +
				dm.getWidth() + "," + dm.getHeight() + "," +
				dm.getBitDepth() + "," + dm.getRefreshRate() + ")");
	}
	
	/**
	 * Changes the display mode.
	 * @param width the desired width.
	 * @param height the desired height.
	 * @param bitDepth the desired bit depth.
	 */
	public void setDisplayMode(int width, int height, int bitDepth)
	{
		// Make sure that changing of display mode is supported.
		if (!gd.isDisplayChangeSupported())
		{
			System.out.println("Changing of display mode not supported");
			return;
		}
		
		// Make sure that the desired display mode is available.
		if (!isDisplayModeAvailable(width, height, bitDepth))
		{
			System.out.println("Display mode (" + width + "," + 
					height + "," + bitDepth + ") not supported");
		}
		
		// Create the display mode.
		DisplayMode dm = new DisplayMode(width, height, bitDepth,
				DisplayMode.REFRESH_RATE_UNKNOWN); // default refresh rate will do
		
		try
		{
			// Change the current display mode.
			gd.setDisplayMode(dm);
			
			System.out.println("Display mode set to: (" + width + "," +
					height + "," + bitDepth + ")");
		}
		catch (IllegalArgumentException e)
		{
			// Could not change the display mode.
			System.out.println("Error setting display mode (" + width + "," +
					height + "," + bitDepth + ")");
		}
		
		try
		{
			// Sleep a bit to allow for changing of the display mode.
			Thread.sleep(1000); // 1 second
		}
		catch (InterruptedException e) {}
	}

	/**
	 * Returns whether the given display mode is available.
	 * @param width the desired width.
	 * @param height the desired height.
	 * @param bitDepth the desired bit depth.
	 * @return boolean whether the display mode is available.
	 */
	private boolean isDisplayModeAvailable(int width, int height, int bitDepth) 
	{
		DisplayMode[] modes = gd.getDisplayModes();
		
		// For debugging purposes only.
		//printModes(modes);
		
		// Loop through the display modes and attempt 
		// to find the desired display mode.
		for (int i = 0; i < modes.length; i++)
		{
			if (width == modes[i].getWidth()
					&& height == modes[i].getHeight()
					&& bitDepth == modes[i].getBitDepth())
			{
				return true; // mode is available
			}
		}
		
		return false; // mode is not available
	}

	/**
	 * Displays the given display modes.
	 * Used for debugging purposes only.
	 * @param modes the modes.
	 */
	private void printModes(DisplayMode[] modes) 
	{
		System.out.println("Modes");
		
		// Loop through the display modes and print them.
		for (int i = 0; i < modes.length; i++)
		{
			System.out.print("(" + modes[i].getWidth() + "," + modes[i].getHeight() + "," + 
					modes[i].getBitDepth() + "," + modes[i].getRefreshRate() + ") ");
			
			// We display four display modes on each row.
			if ((i+1)%4 == 0)
			{
				System.out.println();
			}
		}
		
		System.out.println();
	}
	
	/**
	 * Exits full-screen mode and restores the screen to the OS.
	 */
	public void restoreScreen() 
	{
		Window w = getFullScreenWindow();
		
		// Dispose of the window if necessary.
		if (w != null)
		{
			w.dispose();
		}
		
		// Exit full-screen mode.
		gd.setFullScreenWindow(null);
	}
	
	/**
	 * Returns the screen width.
	 * @return the width.
	 */
	public int getWidth()
	{
		Window w = getFullScreenWindow();
		return w != null ? w.getWidth() : 0;
	}
	
	/**
	 * Returns the screen height.
	 * @return the height.
	 */
	public int getHeight()
	{
		Window w = getFullScreenWindow();
		return w != null ? w.getHeight() : 0;
	}
	
	/**
	 * Returns the full screen window.
	 * @return the window.
	 */
	public Window getFullScreenWindow()
	{
		return gd.getFullScreenWindow();
	}
}
