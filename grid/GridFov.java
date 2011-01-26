package gridwhack.grid;

import java.awt.Color;
import java.awt.Graphics2D;

import gridwhack.fov.IViewer;

/**
 * Grid field of view class.
 * Used to generate field of view for grid entities.
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
	 * Constructs the field of view.
	 * @param grid the grid the viewer exists on.
	 * @param viewer owner of the FoV.
	 */
	public GridFov(Grid grid, IViewer viewer)
	{
		this.width = grid.getGridWidth();
		this.height = grid.getGridHeight();
		this.grid = grid;
		this.viewer = viewer;
		
		empty();
	}
	
	public void empty()
	{
		visible = new boolean[width][height];
		complete = new boolean[width][height];
	}
	
	/**
	 * @return boolean matrix representation of which cells on the grid
	 * are visible and which are not in the field of view.
	 */
	public boolean[][] getVisible()
	{
		return visible;
	}
	
	/**
	 * Returns whether the cell in the given coordinates
	 * is visible to the owner of the FoV.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @return whether the cell in the coordinate is visible.
	 */
	public boolean isVisible(int gx, int gy)
	{
		return visible[gx][gy];
	}
	
	/**
	 * @return boolean matrix representation of all cells
	 * in the field of view not limited by the view range. 
	 */
	public boolean[][] getComplete()
	{
		return complete;
	}
	
	/**
	 * Updates the field of view by raytracing 
	 * the every cell on the edge of the grid.
	 * @param gx the grid x-coordinates.
	 * @param gy the grid y-coordinates.
	 * @param range the view range in grid cells.
	 */
	public void update(int gx, int gy, int range)
	{
		// empty the visible list.
		empty();
		
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
	 * if the target can be seen from the specified position.
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
			if( n>(n - range) && visible[x][y]==false )
			{
				visible[x][y] = true;
			}
			
			// add visible fields to the complete field of view.
			if( complete[x][y]==false )
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
	 * Renders the field of view (used for debug purposes).
	 * @param g the 2d graphics object.
	 */
	public void render(Graphics2D g)
	{
		g.setColor(Color.white);
		
		boolean[][] visible = this.getVisible();
		
		for( int x=0; x<width; x++ )
		{
			for( int y=0; y<height; y++ )
			{
				if( visible[x][y]==true )
				{
					g.drawRect(x*32, y*32, 32, 32);
				}
			}
		}
	}
}

