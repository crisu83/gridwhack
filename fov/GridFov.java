package gridwhack.fov;

import java.awt.Color;
import java.awt.Graphics2D;

import gridwhack.gameobject.character.Character;
import gridwhack.gameobject.character.event.CharacterEvent;
import gridwhack.gameobject.character.event.ICharacterMoveListener;
import gridwhack.gameobject.character.event.ICharacterSpawnListener;
import gridwhack.fov.Fov;
import gridwhack.fov.IViewer;
import gridwhack.gameobject.grid.Grid;
import gridwhack.util.Vector2;

/**
 * Grid field of view class.
 * Allows for generating a field of view for grid game objects.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GridFov extends Fov implements ICharacterMoveListener, ICharacterSpawnListener
{
	// ----------
	// Properties
	// ----------

	private Grid grid;
	private IViewer viewer;

	// -------
	// Methods
	// -------

	/**
	 * Creates the field of view.
	 * @param radius The radius of this field of view.
	 * @param grid The grid the viewer belongs to.
	 * @param viewer The owner of this field of view.
	 */
	public GridFov(int radius, Grid grid, IViewer viewer)
	{
		super(grid.getWidthInCells(), grid.getHeightInCells(), radius);

		this.viewer = viewer;
		this.grid = grid;
	}
	
	/**
	 * Draws this field of view (used for debug purposes).
	 * @param g The graphics object.
	 */
	public void draw(Graphics2D g)
	{
		g.setColor(Color.gray);
		
		for (int x = 0, width = getWidth(); x < width; x++)
		{
			for (int y = 0, height = getHeight(); y < height; y++)
			{
				if (isVisible(x, y))
				{
					g.drawRect(x*32, y*32, 32, 32);
				}
			}
		}
	}

	// --------------
	// Event handlers
	// --------------

	/**
	 * Actions to be taken when the character spawns.
	 * @param e The event.
	 */
	public void onCharacterSpawn(CharacterEvent e)
	{
		Character character = (Character) e.getSource();
		refresh(character.getGridX(), character.getGridY());
	}

	/**
	 * Actions to be taken when the character moves.
	 * @param e The event.
	 */
	public void onCharacterMove(CharacterEvent e)
	{
		Character character = (Character) e.getSource();
		refresh(character.getGridX(), character.getGridY());
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Returns the grid the viewer belongs to.
	 * @return The grid.
	 */
	public Grid getGrid()
	{
		return grid;
	}

	/**
	 * Returns the viewer this field of view belongs to.
	 * @return The viewer.
	 */
	public IViewer getViewer()
	{
		return viewer;
	}
}

