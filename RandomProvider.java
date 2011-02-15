package gridwhack;

import java.util.Random;

/**
 * Random provider class file.
 * Allows for generating random context based on the current time.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class RandomProvider 
{
	private static long seed = System.currentTimeMillis();
	private static Random rand = new Random(seed);
	
	/**
	 * @return the random context.
	 */
	public static Random getRand()
	{
		return rand;
	}
	
	/**
	 * @return the seed used when creating the random context.
	 */
	public static long getSeed()
	{
		return seed;
	}
}
