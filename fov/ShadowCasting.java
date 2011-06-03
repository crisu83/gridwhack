package gridwhack.fov;

import gridwhack.gameobject.grid.Grid;

// TODO: Implement checking of boundaries.
public class ShadowCasting extends GridFov
{
    // Multipliers for transforming the coordinates into sections.
    protected int[][] multipliers = {
        {1, 0, 0, -1, -1, 0, 0, 1},
        {0, 1, -1, 0, 0, -1, 1, 0},
        {0, 1, 1, 0, 0, -1, -1, 0},
        {1, 0, 0, 1, -1, 0, 0, -1}
    };

    public ShadowCasting(int radius, Grid grid, IViewer viewer)
    {
        super(radius, grid, viewer);
    }

    public void refresh(int cx, int cy)
    {
        int section = 0;

        while( section++<8 )
        {
            castShadow(cx, cy, 1, 1.0, 0.0, getRadius(),
                    multipliers[0][section], multipliers[1][section],
                    multipliers[2][section], multipliers[3][section], 0);
        }
    }

    private void castShadow(int cx, int cy, int row, double startSlope, double endSlope, int radius, int xx, int xy, int yx, int yy, int depth)
    {
        if( startSlope>=endSlope )
        {
	        Grid grid = getGrid();
	        IViewer viewer = getViewer();
            double radiusSquared = radius * radius;

            for( int i=row; i<=radius; i++ )
            {
                int dx = -i - 1;
                int dy = -i;
                boolean solid = false;
	            double newStartSlope = 0.0;

                while( dx<=0 )
                {
	                dx++;

                    // Translate the dx and dy coordinates.
                    int x = cx + dx * xx + dy * xy;
                    int y = cy + dx * yx + dy * yy;

                    double leftSlope = (dx - 0.5) / (dy + 0.5);
                    double rightSlope = (dx + 0.5) / (dy - 0.5);

                    if( startSlope<rightSlope )
                    {
                        continue;
                    }
                    else if( endSlope>leftSlope )
                    {
                        break;
                    }
					else
                    {
	                    // Mark visible squares.
	                    if( ( (dx * dx) + (dy * dy) )<radiusSquared )
	                    {
		                    visible[x][y] = true;
	                    }
	                    
	                    // Previous spot was solid.
	                    if( solid )
	                    {
		                    // We are scanning solid squares.
		                    if( grid.isSolid(x, y, viewer) )
		                    {
			                    newStartSlope = rightSlope;
			                    continue;
		                    }
		                    // We are scanning non-solid squares.
		                    else
		                    {
			                    solid = false;
			                    startSlope = newStartSlope;
		                    }
	                    }
	                    // Previous spot was non-solid.
	                    else
	                    {
		                    if( grid.isSolid(x, y, viewer) && i<radius )
		                    {
			                    solid = true;
			                    castShadow(cx, cy, i + 1, startSlope, leftSlope, radius, xx, xy, yx, yy, depth + 1);

			                    newStartSlope = rightSlope;
		                    }
	                    }
                    }
                }

	            if( solid )
	            {
		            break;
	            }
            }
        }
    }
}
