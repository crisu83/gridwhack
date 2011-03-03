package gridwhack.fov;

import gridwhack.exception.ComponentNotFoundException;
import gridwhack.grid.Grid;
import gridwhack.grid.GridFov;

/**
 * Field of view factory class file.
 * Allows for creating fields of view.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class FovFactory
{
	/**
	 * Returns new field of view of the given type.
	 * @param type the field of view type.
	 * @param radius the radius of the field of view.
	 * @param grid the grid the viewer belongs to.
	 * @param viewer the owner of the field of view.
	 * @return the field of view.
	 */
	public static GridFov factory(Fov.Type type, int radius, Grid grid, IViewer viewer) throws ComponentNotFoundException
	{
		GridFov fov = null;

		// return the requested type of character.
		switch( type )
		{
			case RAY_TRACING:
				fov = new RayTracing(radius, grid, viewer);
				break;

			case SHADOW_CASTING:
				fov = new ShadowCasting(radius, grid, viewer);
				break;

			default:
				throw new ComponentNotFoundException("Failed to create field of view, type '" + type + "' is invalid!");
		}

		// Initialize the character.
		fov.init();

		return fov;
	}
}
