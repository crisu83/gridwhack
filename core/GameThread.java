package gridwhack.core;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;

/**
 * Game thread class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GameThread implements Runnable
{
	// ----------
	// Properties
	// ----------

	private static final int NO_DELAYS_PER_YIELD = 16;
	private static final int MAX_FRAME_SKIPS = 5;
	private static final long MAX_STATS_INTERVAL = 1000L; // record stats every second
	private static final int NUM_FPS = 10; // number of FPS values sorted to get an average

	private volatile boolean finished = false;

	private Game game;
	private long period; // period in between drawing in nanoseconds

	private long statsInterval = 0L; // in nanoseconds
	private long prevStatsTime;
	private long totalElapsedTime = 0L;
	private long gameStartTime;
	private int timeSpentInGame = 0; // in seconds
	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];
	private double averageUPS = 0.0;
	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	private double averageFPS = 0.0;

	protected Font font;
	protected FontMetrics metrics;

	protected DecimalFormat df = new DecimalFormat("0.##"); // 2 decimal precision
	//protected DecimalFormat timedf = new DecimalFormat("0.####"); // 4 decimal precision

	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 * @param game The game.
	 * @param period The update period in nano seconds.
	 */
	public GameThread(Game game, long period)
	{
		this.game = game;
		this.period = period;

		gameStartTime = System.nanoTime();

		font = new Font("SansSerif", Font.BOLD, 12);
		metrics = game.getFontMetrics(font);

		fpsStore = new double[NUM_FPS];
		upsStore = new double[NUM_FPS];

		for (int i = 0; i < NUM_FPS; i++)
		{
			fpsStore[i] = 0.0;
			upsStore[i] = 0.0;
		}
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

		while (!finished)
		{
			game.updateLogic();
			drawFrame();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			// We have time to sleep.
			if (sleepTime > 0)
			{
				try
				{
					Thread.sleep(sleepTime/1000000L); // ns -> ms
				}
				catch( InterruptedException e )
				{
					// Doesn't matter.
				}

				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			}
			// We do not have time to sleep, frame took longer than the period.
			else
			{
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;

				// Let other threads run if necessary.
				if (++noDelays >= NO_DELAYS_PER_YIELD)
				{
					Thread.yield();
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();

			int skips = 0;
			while (excess > period && skips < MAX_FRAME_SKIPS)
			{
				excess -= period;
				game.updateLogic(); // update without drawing
				skips++;
			}

			framesSkipped += skips;

			saveStats();
		}
	}

	/**
	 * Draws a single frame of the game.
	 */
	private synchronized void drawFrame()
	{
		try
		{
			BufferStrategy bs = game.getBufferStrategy();

			// Get the content from the buffer strategy, render it dispose of it after its been rendered.
			Graphics2D g = (Graphics2D) bs.getDrawGraphics();

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			game.drawFrame(g);
			g.dispose();

			// Make sure that the content of the buffer strategy is not lost.
			if (!bs.contentsLost())
			{
				bs.show();
			}
			else
			{
				// Content was lost. This might happen if the OS overwrites the content in the buffer strategy.
				System.out.println("Buffer strategy content lost");
				game.gameStop();
			}

			Toolkit.getDefaultToolkit().sync(); // sync the display on *nix OS
		}
		catch (Exception e)
		{
			// Could not update the screen, print the exception and terminate the game loop.
			e.printStackTrace();
			game.gameStop();
		}
	}

	/**
	 * Stops the game.
	 */
	public void stopGame()
	{
		printStats();
		finished = true;
	}

	/**
	 * Saves the runtime statistics.
	 */
	private void saveStats()
	{
		frameCount++;
		statsInterval += period;

		// Make sure we should collect the stats.
		if (statsInterval >= MAX_STATS_INTERVAL)
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

			if (totalElapsedTime > 0)
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

			for (int i = 0; i < NUM_FPS; i++)
			{
				totalFPS += fpsStore[i];
				totalUPS += upsStore[i];
			}

			if (statsCount < NUM_FPS)
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
}
