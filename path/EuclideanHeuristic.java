package gridwhack.path;

/**
 * Euclidean heuristic class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class EuclideanHeuristic implements IAStarHeuristic 
{
	/**
	 * Returns the heuristic cost of moving to the target-coordinates.
	 * @param x the x-coordinates to be evaluated.
	 * @param y the y-coordinates to be evaluated.
	 * @param tx the target x-coordinates.
	 * @param ty the target y-coordinates.
	 * @param mover the entity moving.
	 * @return the heuristic cost.
	 */
	public float getCost(int x, int y, int tx, int ty, IMover mover) 
	{
		float dx = tx - x;
		float dy = ty - y;
		
		// use pythagorean theorem to determine the distance. (a^2 + b^2 = c^2)
		// might not be the perfect solution but good enough for now.
		float heuristic = (float) Math.sqrt( (dx*dx) + (dy*dy) );
		
		return heuristic;
	}
}
