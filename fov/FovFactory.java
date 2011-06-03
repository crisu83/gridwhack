package gridwhack.fov;

import gridwhack.base.BaseObject;
import gridwhack.fov.Fov.FovType;
import gridwhack.exception.InvalidObjectException;
import gridwhack.gameobject.grid.Grid;

/**
 * Field of view factory class.
 * Allows for creating fields of views.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class FovFactory extends BaseObject
{
	// ----------
	// Properties
	// ----------

	private static final FovFactory instance = new FovFactory();

	// -------
	// Methods
	// -------

	/**
	 * Creates the factory.
	 * Private to enforce the singleton pattern.
	 */
	private FovFactory() {}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static FovFactory getInstance()
	{
		return instance;
	}

	/**
	 * Creates a field of view of the given type.
	 * @param type The field of view type.
	 * @param radius The radius of the field of view.
	 * @param grid The grid the viewer belongs to.
	 * @param viewer The owner of the field of view.
	 * @return The field of view.
	 */
	public GridFov create(FovType type, int radius, Grid grid, IViewer viewer) throws InvalidObjectException
	{
		GridFov object;

		// return the requested type of character.
		switch (type)
		{
			case RAY_TRACING:
				object = new RayTracing(radius, grid, viewer);
				break;

			case SHADOW_CASTING:
				object = new ShadowCasting(radius, grid, viewer);
				break;

			default:
				throw new InvalidObjectException("Invalid object type.");
		}

		object.init();

		return object;
	}
}
