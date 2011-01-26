package gridwhack;

import java.util.Random;

/**
 * Random provider class.
 * Provides functionality for using one and same random generator 
 * through out the whole application.
 */
public class RandomProvider 
{
	private static long seed = System.currentTimeMillis();
	private static Random rand = new Random(seed);
	
	/**
	 * @return the random generator.
	 */
	public static Random getRand()
	{
		return rand;
	}
	
	/**
	 * @return the seed used when creating the random generator.
	 */
	public static long getSeed()
	{
		return seed;
	}
}
