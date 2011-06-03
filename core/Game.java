package gridwhack.core;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

/**
 * Game class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Game extends JFrame
{
	// ----------
	// Properties
	// ----------

	private static final int NUM_BUFFERS = 2; // number of buffers to use with the buffer strategy

	//private int width;
	//private int height;
	private volatile boolean running = false;
	private volatile boolean gameOver = false; // for game termination
	private GameThread gameThread;
	private ScreenManager screen;
	private Thread game;

	// -------
	// Methods
	// -------

	/**
	 * Creates the game.
	 * @param title The game title.
	 * @param period The update period in nanoseconds.
	 */
	public Game(String title, long period)
	{
		super(title);
		
		// Create the screen manager.
		this.screen = new ScreenManager(this);
		
		// Initialize the full-screen mode.
		screen.initFullScreen(NUM_BUFFERS);
		
		// Set an appropriate display mode.
		//screen.setDisplayMode(1920, 1080, 32);
		
		// Get the width and height after changing the display mode.
		//width = screen.getWidth();
		//height = screen.getHeight();

		// Allow for terminating the application.
		readyForTermination();

		gameThread = new GameThread(this, period);

		init(); // initialize the game
		gameStart(); // start the game thread
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
					if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown())
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
				/**
				 * Runs the thread.
				 */
				public void run()
				{
					gameStop();
				}
			}
		);
	}

	/**
	 * Starts this game.
	 */
	public void gameStart()
	{
		if (!running)
		{
			Runtime r = Runtime.getRuntime();
			r.gc(); // Run garbage collection

			game = new Thread(gameThread);
			game.setName("Game");
			game.start();
			running = true;
		}
	}

	/**
	 * Stops this game.
	 */
	public void gameStop()
	{
		if (running)
		{
			gameThread.stopGame();
			game = null;
			running = false;
			screen.restoreScreen();
			System.exit(0);
		}
	}

	/**
	 * @return The game window.
	 */
	public Window getGameWindow()
	{
		return screen.getFullScreenWindow();
	}

	// ----------------
	// Abstract methods
	// ----------------

	/**
	 * Initializes the game.
	 */
	public abstract void init();

	/**
	 * Updates the game logic.
	 */
	public abstract void updateLogic();

	/**
	 * Draws a single frame of the game.
	 * @param g The graphics context.
	 */
	public abstract void drawFrame(Graphics2D g);
}
