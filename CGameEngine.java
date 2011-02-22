package gridwhack;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;

import javax.swing.JFrame;

/**
 * Core game engine class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class CGameEngine extends JFrame implements Runnable 
{	
	protected int pWidth, pHeight;
	
	private Thread animator; // for the animation
	private volatile boolean running = false; // stops the animation
	private volatile boolean ended = false; // ensures that the application is not terminated multiple times

	private volatile boolean gameOver = false; // for game termination

	private long period; // period in between drawing in nanoseconds

	protected CScreenManager screen;

	private static final int NUM_BUFFERS = 2; // number of buffers to use with the buffer strategy

	private static final int NO_DELAYS_PER_YIELD = 16;
	private static final int MAX_FRAME_SKIPS = 5;
	
	private static final long MAX_STATS_INTERVAL = 1000L; // record stats every second
	private static final int NUM_FPS = 10; // number of FPS values sorted to get an average
	
	private long statsInterval = 0L; // in nanoseconds
	private long prevStatsTime;   
	private long totalElapsedTime = 0L;
	private long gameStartTime;
	protected int timeSpentInGame = 0; // in seconds
	
	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	protected double averageFPS = 0.0;
	
	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];
	protected double averageUPS = 0.0;
	
	private long prevUpdateTime; // in nanoseconds
	
	protected DecimalFormat df = new DecimalFormat("0.##"); // 2 decimal precision
	protected DecimalFormat timedf = new DecimalFormat("0.####"); // 4 decimal precision

	protected Font font;
	protected FontMetrics metrics;

	/**
	 * Creates the game engine.
	 * @param title the application frame title.
	 * @param period the period (in nanoseconds).
	 */
	public CGameEngine(String title, long period)
	{
		super(title);
		
		// Create the screen manager.
		this.screen = new CScreenManager(this);
		
		this.period = period;
		
		// Initialize the full-screen mode.
		screen.initFullScreen(NUM_BUFFERS);
		
		// Set an appropriate display mode.
		//screen.setDisplayMode(1920, 1080, 32);
		
		// Get the width and height after changing the display mode.
		pWidth = screen.getWidth();
		pHeight = screen.getHeight();

		// Allow for terminating the application.
		readyForTermination();
		
		// Set up message font.
		font = new Font("SansSerif", Font.BOLD, 12);
		metrics = this.getFontMetrics(font);
		
		// Initialize the timing elements.
		fpsStore = new double[NUM_FPS];
		upsStore = new double[NUM_FPS];
		
		for( int i=0; i<NUM_FPS; i++ )
		{
			fpsStore[i] = 0.0;
			upsStore[i] = 0.0;
		}
		
		gameInit(); // initialize the game
		gameStart(); // start the thread
	}
	
	/**
	 * Allows for terminating the application.
	 */
	private void readyForTermination() 
	{
		// Add a key listener to listen for termination key presses.
		addKeyListener(
			new KeyAdapter()
			{
				/**
				 * Actions to be taken when a key is pressed.
				 * @param e the key event.
				 */
				public void keyPressed(KeyEvent e)
				{
					if( e.getKeyCode()==KeyEvent.VK_C && e.isControlDown() )
					{
						gameStop();
					}

					e.consume();
				}
			}
		);
		
		// Add a shutdown hook to be called when game is terminated.
		Runtime.getRuntime().addShutdownHook(
			new Thread()
			{
				public void run()
				{
					gameStop();
					endClean();
				}
			}
		);
	}

	/**
	 * Starts the game.
	 */
	private void gameStart()
	{
		// Start the game if it is not already started.
		if( animator==null || !running )
		{
			animator = new Thread(this);
			animator.start();
		}
	}
	
	/**
	 * Stops the game.
	 */
	public void gameStop()
	{
		running = false; // exit the game loop
	}

	/**
	 * Runs the game.
	 */
	public void run() 
	{
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;
		
		gameStartTime = System.nanoTime();
		prevStatsTime = gameStartTime;
		beforeTime = gameStartTime;
		
		running = true;
		
		while( running )
		{
			stateUpdate(); // game state is updated
			screenUpdate(); // screen is updated
			
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;
			
			// We have time to sleep.
			if( sleepTime>0 )
			{
				try
				{
					Thread.sleep(sleepTime/1000000L); // ns -> ms
				}
				catch( InterruptedException e ) {}
				
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			}
			// We do not have time to sleep, frame took longer than the period.
			else 
			{
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;
				
				// Let other threads run if necessary.
				if( ++noDelays>=NO_DELAYS_PER_YIELD )
				{
					Thread.yield();
					noDelays = 0;
				}
			}
			
			beforeTime = System.nanoTime();
			
			int skips = 0;
			while( excess>period && skips<MAX_FRAME_SKIPS )
			{
				excess -= period;
				stateUpdate(); // update the state but do not render
				skips++;
			}
			
			framesSkipped += skips;
			
			storeStats();
		}
	
		// End the application.
		endClean();
	}
	
	/**
	 * Updates the game state.
	 */
	private void stateUpdate()
	{
		// Make sure that the game is not over.
		if( !gameOver )
		{
			// Get the amount of time passed since the last update.
			long timePassed = getTimeSinceLastUpdate();

			// Update the game state.
			gameUpdate(timePassed);
		}
	}
	
	/**
	 * Returns the time passed since the last update.
	 * @return the time passed in nanoseconds.
	 */
	private long getTimeSinceLastUpdate()
	{
		long timePassed, timeNow;
		
		timeNow = System.nanoTime();
		
		// Calculate the time passed based on
		// when the last previous update time.
		prevUpdateTime = prevUpdateTime>0 ? prevUpdateTime : timeNow;
		timePassed = timeNow - prevUpdateTime;
		prevUpdateTime = timeNow;
		
		return timePassed;		
	}
	
	/**
	 * Updates the screen.
	 */
	private void screenUpdate() 
	{
		try
		{
			BufferStrategy bs = getBufferStrategy();
			
			// Get the content from the buffer strategy,
			// render it dispose of it after its been rendered.
			Graphics2D g = (Graphics2D) bs.getDrawGraphics();
			gameRender(g);
			g.dispose();
			
			// Make sure that the content of the buffer strategy is not lost.
			if( !bs.contentsLost() )
			{
				bs.show();
			}
			else
			{
				// Content was lost. This might happen if the OS overwrites
				// the content in the buffer strategy.
				System.out.println("Buffer strategy content lost");
			}
			
			Toolkit.getDefaultToolkit().sync(); // sync the display on *nix OS
		}
		catch( Exception e )
		{
			// Could not update the screen, print the exception
			// and terminate the game loop.
			e.printStackTrace();
			running = false;
		}
	}
	
	/**
	 * Ends the application properly.
	 */
	protected void endClean() 
	{
		// Make sure that we do not call this multiple times.
		if( !ended )
		{
			ended = true;
			printStats();
			screen.restoreScreen();
			System.exit(0);
		}
	}

	/**
	 * Stores the runtime statistics.
	 */
	private void storeStats() 
	{
		frameCount++;
		statsInterval += period;
		
		// Make sure we should collect the stats.
		if( statsInterval>=MAX_STATS_INTERVAL ) 
		{
			long timeNow = System.nanoTime();
			timeSpentInGame = (int) ((timeNow - gameStartTime) / 1000000000L); // ns -> seconds
			
			long realElapsedTime = timeNow - prevStatsTime; // time since last stats collection
			totalElapsedTime += realElapsedTime;
			
			//double timingError = (double) ((realElapsedTime - statsInterval) / statsInterval) * 100.0;
			
			totalFramesSkipped += framesSkipped;
			
			// Calculate the latest FPS and UPS.
			double actualFPS = 0;
			double actualUPS = 0;
			
			if( totalElapsedTime>0 ) 
			{
				actualFPS = ((double) frameCount / totalElapsedTime) * 1000000000L; // ns -> seconds
				actualUPS = ((double) (frameCount + totalFramesSkipped) / totalElapsedTime) * 1000000000L;
			}

			// Store the latest FPS and UPS.
			fpsStore[ (int) statsCount % NUM_FPS ] = actualFPS;
			upsStore[ (int) statsCount % NUM_FPS ] = actualUPS;
			
			statsCount++;
			
			double totalFPS = 0.0;
			double totalUPS = 0.0;
			
			for( int i=0; i<NUM_FPS; i++ )
			{
				totalFPS += fpsStore[i];
				totalUPS += upsStore[i];
			}
			
			if( statsCount<NUM_FPS )
			{
				averageFPS = totalFPS/statsCount;
				averageUPS = totalUPS/statsCount;
			}
			else
			{
				averageFPS = totalFPS/NUM_FPS;
				averageUPS = totalUPS/NUM_FPS;
			}
			
			// For debugging purposes only.
			/*
			System.out.println(
					timedf.format( (double) statsInterval/1000000000L) + " " +
					timedf.format( (double) realElapsedTime/1000000000) + "s " +
					df.format(timingError) + "% " +
					frameCount + "c " +
					framesSkipped + "/" + totalFramesSkipped + " skip; " +
					df.format(actualFPS) + " " + df.format(averageFPS) + " afps; " +
					df.format(actualUPS) + " " + df.format(averageUPS) + " aups");
			*/
			
			framesSkipped = 0;
			prevStatsTime = timeNow;
			statsInterval = 0L; // reset
		}
	}
	
	/**
	 * Prints the runtime statistics.
	 */
	private void printStats()
	{
		System.out.println("Frame Count/Loss: " + frameCount + " / " + totalFramesSkipped);
		System.out.println("Average FPS: " + df.format(averageFPS));
		System.out.println("Average UPS: " + df.format(averageUPS));
		System.out.println("Time Spent: " + timeSpentInGame + " secs");
	}

	/**
	 * Initializes the game.
	 */
	public abstract void gameInit();

	/**
	 * Updates the game state.
	 * @param timePassed the time passed since the last update.
	 */
	public abstract void gameUpdate(long timePassed);

	/**
	 * Renders the game.
	 * @param g the graphics context.
	 */
	public abstract void gameRender(Graphics2D g);
}
