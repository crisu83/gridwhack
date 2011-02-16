package gridwhack.path;

/**
 * A* heuristic interface file.
 * All heuristics must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IAStarHeuristic
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
	public float getCost(int x, int y, int tx, int ty, IMover mover);
}
