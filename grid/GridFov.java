package gridwhack.grid;

import java.awt.Color;
import java.awt.Graphics2D;

import gridwhack.fov.IViewer;

/**
 * Grid field of view class file.
 * Allows for generating a field of view for grid entities.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GridFov 
{
	protected int width;
	protected int height;
	protected Grid grid;
	protected IViewer viewer;
	protected boolean[][] visible;
	protected boolean[][] complete;
	
	/**
	 * Creates the field of view.
	 * @param grid the grid the viewer belongs to.
	 * @param viewer owner of the FoV.
	 */
	public GridFov(Grid grid, IViewer viewer)
	{
		this.width = grid.getGridWidth();
		this.height = grid.getGridHeight();
		this.grid = grid;
		this.viewer = viewer;
		
		reset();
	}

	/**
	 * Resets the field of view.
	 */
	public void reset()
	{
		visible = new boolean[width][height];
		complete = new boolean[width][height];
	}
	
	/**
	 * Returns a matrix representation of which cells are visible
	 * taking into account the owner view range.
	 * @return the visible matrix.
	 *
	 */
	public boolean[][] getVisible()
	{
		return visible;
	}
	
	/**
	 * Returns whether a specific cell is visible to the owner.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @return whether the cell is visible.
	 */
	public boolean isVisible(int gx, int gy)
	{
		return visible[gx][gy];
	}
	
	/**
	 * Returns a matrix representation of which cells are visible
	 * not taking into account the owner view range.
	 * @return the complete matrix.
	 */
	public boolean[][] getComplete()
	{
		return complete;
	}
	
	/**
	 * Updates this FoV by raytracing every cell on the edge of the grid.
	 * @param gx the grid x-coordinates.
	 * @param gy the grid y-coordinates.
	 * @param range the view range in grid cells.
	 */
	public void update(int gx, int gy, int range)
	{
		// reset the field of view.
		reset();
		
		// loop through all coordinates on the grid.
		for( int x=0; x<width; x++ )
		{
			for( int y=0; y<height; y++ )
			{
				// skip all but the bordering cells.
				if( (x==0 || y==0) || (x==width-1 || y==height-1) )
				{	
					raytrace(gx, gy, x, y, range);
				}
			}
		}
	}
	
	/**
	 * Raytracing method that uses an integer only version
	 * of "Bresenham line-drawing algorithm" to determine 
	 * if the target can be seen from a specific position.
	 * @param sx the starting x-coordinate.
	 * @param sy the starting y-coordinate.
	 * @param tx the target x-coordinate.
	 * @param ty the target y-coordinate.
	 * @param range the view range in grid cells.
	 */
	public void raytrace(int sx, int sy, int tx, int ty, int range)
	{
		// calculate the deltas.
		int dx = Math.abs(tx - sx);
		int dy = Math.abs(ty - sy);

		int x = sx;
		int y = sy;

		// calculate how many moves are required 
		// to reach the target.
		int n = 1 + dx + dy;

		// calculate x-increment and y-increment.
		int xi = (tx > sx) ? 1 : -1;
		int yi = (ty > sy) ? 1 : -1;

		// determine in which direction to set off.
		int error = dx - dy;
		
		dx *= 2;
		dy *= 2;

		// we need to remember where we started
		// in order to check the range.
		int ns = n;
		
		// loop while we have gird cells left.
		while( n>0 )
		{
			// brake if the ray hits something
			// that the viewer cannot see through.
			if( grid.isSolid(x, y, viewer) )
			{
				break;
			}

			// mark grid cell within the range as visible.
			if( n>(ns - range) && !visible[x][y] )
			{
				visible[x][y] = true;
			}
			
			// add visible fields to the complete field of view.
			if( !complete[x][y] )
			{
				complete[x][y] = true;
			}

			// move horizontally.
			if( error>0 )
			{
				x += xi;
				error -= dy;
			}
			// move vertically.
			else if( error<0 )
			{
				y += yi;
				error += dx;
			}
			// line is passing through both a vertical 
			// and horizontal grid line.
			else
			{
				x += xi;
				error -= dy;
				y += yi;
				error += dx;
				n--;
			}
			
			n--;
		}
	}
	
	/**
	 * Renders this FoV (used for debug purposes).
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		g.setColor(Color.gray);
		
		boolean[][] visible = this.getVisible();
		
		for( int x=0; x<width; x++ )
		{
			for( int y=0; y<height; y++ )
			{
				if( visible[x][y] )
				{
					g.drawRect(x*32, y*32, 32, 32);
				}
			}
		}
	}
}

