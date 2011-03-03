package gridwhack.grid;

import java.awt.Color;
import java.awt.Graphics2D;

import gridwhack.entity.character.Character;
import gridwhack.entity.character.event.CharacterEvent;
import gridwhack.entity.character.event.ICharacterMoveListener;
import gridwhack.entity.character.event.ICharacterSpawnListener;
import gridwhack.fov.Fov;
import gridwhack.fov.IViewer;

/**
 * Grid field of view class file.
 * Allows for generating a field of view for grid entities.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GridFov extends Fov implements ICharacterMoveListener, ICharacterSpawnListener
{
	private Grid grid;
	private IViewer viewer;

	/**
	 * Creates the field of view.
	 * @param radius the radius of this field of view.
	 * @param grid the grid the viewer belongs to.
	 * @param viewer owner of this field of view.
	 */
	public GridFov(int radius, Grid grid, IViewer viewer)
	{
		super(grid.getWidthInCells(), grid.getHeightInCells(), radius);

		this.viewer = viewer;
		this.grid = grid;
	}
	
	/**
	 * Renders this field of view. (used for debug purposes).
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		g.setColor(Color.gray);
		
		for( int x=0, width=getWidth(); x<width; x++ )
		{
			for( int y=0, height=getHeight(); y<height; y++ )
			{
				if( isVisible(x, y) )
				{
					g.drawRect(x*32, y*32, 32, 32);
				}
			}
		}
	}

	/**
	 * Actions to be taken when the character spawns.
	 * @param e the event.
	 */
	public void onCharacterSpawn(CharacterEvent e)
	{
		Character character = (Character) e.getSource();
		update(character.getGridX(), character.getGridY());
	}

	/**
	 * Actions to be taken when the character moves.
	 * @param e the event.
	 */
	public void onCharacterMove(CharacterEvent e)
	{
		Character character = (Character) e.getSource();
		update(character.getGridX(), character.getGridY());
	}

	/**
	 * Returns the grid the viewer belongs to.
	 * @return the grid.
	 */
	public Grid getGrid()
	{
		return grid;
	}

	/**
	 * Returns the viewer this field of view belongs to.
	 * @return the viewer.
	 */
	public IViewer getViewer()
	{
		return viewer;
	}
}

