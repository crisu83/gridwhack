package gridwhack.path;

/**
 * Path finder interface file.
 * All path finders must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IPathFinder 
{
	/**
	 * Returns the path from the starting coordinates to the target coordinates.
	 * @param sx the starting x-coordinate.
	 * @param sy the starting y-coordinate.
	 * @param tx the target x-coordiante.
	 * @param ty the target y-coordinate.
	 * @param maxPathLength the maximum legth allowed for the path.
	 * @param mover the entity for which to get the path.
	 * @return the path.
	 */
	public Path getPath(int sx, int sy, int tx, int ty, int maxPathLength, IMover mover);
}