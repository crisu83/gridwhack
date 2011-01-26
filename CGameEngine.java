package gridwhack;

import java.awt.*;

public abstract class CGameEngine //implements Runnable
{
	private volatile boolean running;
	
	protected CScreenManager screen;
	
	private Thread animator;
	
	/**
	 * List of supported display modes.
	 */
	private static final DisplayMode modes[] = {
		new DisplayMode(1280, 800, 32, 0),
		new DisplayMode(1280, 800, 24, 0),
		new DisplayMode(1280, 800, 16, 0),
		new DisplayMode(1280, 720, 32, 0),
		new DisplayMode(1280, 720, 24, 0),
		new DisplayMode(1280, 720, 16, 0),
	};
	
	/**
	 * Initializes the core.
	 */
	public void init()
	{
		// create the screen manager and set display mode.
		screen = new CScreenManager();
		DisplayMode dm = screen.getCompatibleDisplayMode(modes);
		screen.setFullScreen(dm);
		
		// initialize the game.
		gameInit();
		
		// start the game.
		//startGame();
	}
	
	/**
	 * Starts the game.
	 */
	/*
	private void startGame()
	{
		if( animator==null || !running ) 
		{
			animator = new Thread(this);
			animator.start();
	    }
	}
	*/
	
	/**
	 * Runs the game.
	 */
	public void run()
	{
		try
		{
			// initialize the core.
			init();
			
			// run the game.
			gameLoop();
		}
		finally
		{
			// restore the screen after the game is exited.
			screen.restoreScreen();
		}
	}
	
	/**
	 * Runs the game.
	 */
	public void gameLoop()
	{		
		// save the starting time.
		long startTime = System.nanoTime();
		long cumulativeTime = startTime;
		
		// the game is now running.
		running = true;
		
		// loop while the game is running.
		while( running )
		{
			// calculate time passed and add that to the cumulative time.
			long timePassed = System.nanoTime() - cumulativeTime;
			cumulativeTime += timePassed;
			
			// update the animations.
			gameUpdate(timePassed);
			
			// render the game.
			Graphics2D g = screen.getGraphics();
			gameRender(g);
			g.dispose();			
			
			// update the screen.
			screen.update();
			
			// sleep a while in between each loop.
			try
			{
				Thread.sleep(20);
			}
			catch( InterruptedException ex ) 
			{
				
			}
		}
	}
	
	/**
	 * Stops the game.
	 */
	protected void stopGame()
	{
		running = false;
	}
	
	/**
	 * Initializes the game.
	 */
	public abstract void gameInit();
	
	/**
	 * Updates the animations.
	 * @param timePassed the time that has passed.
	 */
	public abstract void gameUpdate(long timePassed);
	
	/**
	 * Renders the game.
	 * @param g the 2D graphics object.
	 */
	public abstract void gameRender(Graphics2D g);
}
