package gridwhack.grid;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import gridwhack.RandomProvider;
import gridwhack.entity.*;
import gridwhack.entity.unit.*;
import gridwhack.entity.unit.UnitFactory.UnitType;
import gridwhack.entity.unit.event.*;
import gridwhack.entity.item.Item;
import gridwhack.entity.item.Loot;
import gridwhack.entity.tile.*;
import gridwhack.entity.tile.TileFactory.TileType;
import gridwhack.fov.IViewer;
import gridwhack.path.*;

public class Grid implements IUnitListener
{
	private static final int CELL_SIZE = 32;
	
	protected int widthInCells;
	protected int heightInCells;
	private GridCell[][] cells;
	private GridAStarPathFinder pf;
	private CEntityManager tiles;
	private CEntityManager items;
	private CEntityManager units;
	private Random rand;
	
	/**
	 * Constructs the grid.
	 * @param widthInCells the width of the grid in cells.
	 * @param heightInCells the height of the grid in cells.
	 */
	public Grid(int widthInCells, int heightInCells)
	{
		this.widthInCells = widthInCells;
		this.heightInCells = heightInCells;
		
		// initialize the cells as an empty grid cell matrix.
		cells = new GridCell[widthInCells][heightInCells];
		
		// create a new path finder that uses the euclidean heuristic
		// that can be used to calculate paths for units on the grid.
		pf = new GridAStarPathFinder(new EuclideanHeuristic(), this);
		
		// create entity managers to handle 
		// tiles, items and units on the grid.
		tiles = new CEntityManager();
		items = new CEntityManager();
		units = new CEntityManager();
		
		// get random from the random provider. 
		rand = RandomProvider.getRand();
		
		// add cells to the grid.
		for( int gx=0; gx<widthInCells; gx++ )
		{
			for( int gy=0; gy<heightInCells; gy++ )
			{
				cells[gx][gy] = new GridCell(gx, gy);
			}
		}
	}
	
	/**
	 * Returns a specific cell in the grid.
	 * @param gx the grid x-coordinates of the cell.
	 * @param gy the grid y-coordinates of the cell.
	 * @return the cell.
	 */
	public GridCell getCell(int gx, int gy)
	{
		if( gx>=0 && gy>=0 && gx<widthInCells && gy<heightInCells )
		{
			return cells[gx][gy];
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns a path on the grid from a starting point to the target.
	 * @param sgx the starting grid x-coordinate.
	 * @param sgy the starting grid y-coordinate.
	 * @param tgx the target grid x-coordinate.
	 * @param tgy the target grid y-coordinate.
	 * @param maxPathLength the maximum length of the path.
	 * @param mover the unit moving.
	 * @return the path or null if no path available.
	 */
	public GridPath getPath(int sgx, int sgy, int tgx, int tgy, int maxPathLength, IMover mover)
	{
		return pf.getPath(sgx, sgy, tgx, tgy, maxPathLength, mover);
	}
	
	/**
	 * Sets the tile in the specific coordinates.
	 * @param sgx the grid x-coordinate to start at.
	 * @param sgy the grid y-coordinate to start at.
	 * @param widthInCells the rectangle width in cells.
	 * @param heightInCells the rectangle height in cells.
	 * @param type the type of tile to use.
	 */
	public void createTileRect(int sgx, int sgy, int widthInCells, int heightInCells, TileType type)
	{
		// loop through the cells within the area and set the tiles.
		for( int gx=sgx, xmax=(sgx + widthInCells); gx<xmax; gx++ )
		{
			for( int gy=sgy, ymax=(sgy + heightInCells); gy<ymax; gy++ )
			{
				// request the tile from the tile factory.
				Tile tile = TileFactory.factory(type, this);
				
				GridCell cell = getCell(gx, gy);
				
				// make sure the cell exists.
				if( cell!=null )
				{
					cell.setTile(tile);
					tiles.addEntity(tile);
				}
			}
		}
	}
	
	/**
	 * Adds an unit to the grid.
	 * @param type the type of unit to create.
	 */
	public synchronized void createUnit(UnitType type)
	{		
		// request the unit from the unit factory.
		GridUnit unit = (GridUnit)UnitFactory.factory(type, this);
		
		// get a random cell on the grid.
		GridCell cell = getRandomCell();
		
		int gx = cell.getGridX();
		int gy = cell.getGridY();
		
		// add the unit to that random cell.
		createUnit(gx, gy, unit);
	}
	
	/**
	 * Adds an unit to the grid.
	 * @param gx the grid x-coordinate where to add the unit.
	 * @param gy the grid y-coordinate where to add the unit.
	 * @param unit the unit to add.
	 */
	public synchronized void createUnit(int gx, int gy, GridUnit unit)
	{
		GridCell cell = getCell(gx, gy);
		
		// make sure the cell exists.
		if( cell!=null )
		{
			cell.addUnit(unit);
			units.addEntity(unit);
		}

		unit.addListener(this);
	}
	
	/**
	 * @return all units on the grid.
	 */
	public ArrayList<GridUnit> getUnits()
	{
		// get all entities in the grid entity manager.
		ArrayList<CEntity> entities = units.getEntities();
		
		// initialize an array for the units.
		ArrayList<GridUnit> units = new ArrayList<GridUnit>();

		// loop through the entities and collect all units.
		for( CEntity entity : entities )
		{
			// make sure the entity is an unit.
			if( entity instanceof GridUnit )
			{
				units.add((GridUnit) entity);
			}
		}
		
		return units;
	}
	
	/**
	 * Returns all visible units for the given unit.
	 * @param unit the unit for which to get visible units.
	 * @return the visible units.
	 */
	public ArrayList<Unit> getVisibleUnits(GridUnit unit)
	{
		ArrayList<Unit> visibleUnits = new ArrayList<Unit>();
		
		// get a matrix representation of which cells on the grid
		// are visible to the unit.
		boolean[][] visible = unit.fov.getVisible();
		
		// loop through all the cells.
		for( int x=0, xmax=visible.length; x<xmax; x++ )
		{
			for( int y=0, ymax=visible[x].length; y<ymax; y++ )
			{
				// make sure the cell is visible to the unit.
				if( visible[x][y] )
				{
					GridCell cell = getCell(x, y);
					
					// make sure we have a cell.
					if( cell!=null )
					{
						GridUnit target = cell.getUnit();
						
						if( target!=null && !visibleUnits.contains(target) )
						{
							visibleUnits.add(target);
						}
					}
				}
			}
		}
		
		return visibleUnits;
	}
	
	/**
	 * Moves a specific unit on the grid.
	 * @param dgx the grid delta x. 
	 * @param dgy the grid delta y.
	 * @param unit the unit to move.
	 */
	public void moveUnit(int dgx, int dgy, GridUnit unit)
	{
		// get the unit position on the grid.
		int gx = unit.getGridX();
		int gy = unit.getGridY();

		// get the cell this unit is occupying.
		GridCell source = getCell(gx, gy);
		
		// make sure we have a cell.
		if( source!=null )
		{
			// get the cell the unit is moving to.
			GridCell destination = getCell(gx+dgx, gy+dgy);
			
			// make sure that we have a destination cell
			// and that it is not blocked.
			if( destination!=null && !destination.isBlocked(unit) )
			{
				// get the unit in the destination cell.
				GridUnit target = destination.getUnit();
				
				// check if there are potential targets.
				if( target!=null )
				{
					// attack the target if hostile.
					unit.attack(target);
				}
				else
				{					
					// move the unit to the destination cell.
					source.removeUnit();
					destination.addUnit(unit);
					
					if( unit instanceof Player )
					{
						destination.loot((Player) unit);
					}
				}
			}
		}
	}
	
	/**
	 * Adds loot to the given coordinate on the grid.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @param loot the loot to create.
	 */
	public synchronized void createLoot(int gx, int gy, Loot loot)
	{
		GridCell cell = getCell(gx, gy);
		
		// make sure the cell exists.
		if( cell!=null )
		{
			cell.addLoot(loot);			
			items.addEntity(loot);
		}
	}
	
	/**
	 * @return a random non-blocked cell on the grid.
	 */
	public GridCell getRandomCell()
	{
		int tryCount = 0;
		int maxTries = 100;
		
		while( tryCount++<maxTries )
		{
			// get a random position on the grid.
			int gx = rand.nextInt(getGridWidth());
			int gy = rand.nextInt(getGridHeight());
			
			GridCell cell = getCell(gx, gy);
			
			// make sure the cell exists and is not blocked.
			if( cell!=null && !cell.isBlocked(null) )
			{
				return cell;
			}
		}
		
		// all cells are blocked or no cells exist.
		return null;
	}
	
	/**
	 * Returns whether a cell is blocked.
	 * @param mover the moving entity.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @return whether the cell is blocked.
	 */
	public boolean isBlocked(int gx, int gy, IMover mover)
	{
		GridCell cell = getCell(gx, gy);
		
		if( cell!=null )
		{
			return cell.isBlocked(mover);
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Returns whether the cell can be seen through.
	 * @param viewer the viewing entity.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @return whether the cell can be seen through.
	 */
	public boolean isSolid(int gx, int gy, IViewer viewer)
	{
		GridCell cell = getCell(gx, gy);
		
		if( cell!=null )
		{
			return cell.isSolid(viewer);
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Returns the movement cost for the given cell.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @param mover the mover.
	 * @return the cost.
	 */
	public int getMovementCost(int gx, int gy, IMover mover)
	{
		return 1;
	}
	
	/**
	 * Converts grid cells into pixels.
	 * @param offset the offset in cells.
	 * @return the offset in pixels.
	 */
	public int getOffsetInPixels(int offset)
	{
		return offset * getCellSize();
	}
	
	/**
	 * Converts pixels into grid cells.
	 * @param offset the offset in pixels
	 * @return the offset in cells.
	 */
	public int getOffsetInCells(double offset)
	{
		return (int)(Math.round(offset) / getCellSize());
	}

	/**
	 * @return the size of one cell.
	 */
	public int getCellSize() 
	{
		return CELL_SIZE;
	}
	
	/**
	 * @return the width of the grid in cells.
	 */
	public int getGridWidth()
	{
		return widthInCells;
	}
	
	/**
	 * @return the height of the grid in cells.
	 */
	public int getGridHeight()
	{
		return heightInCells;
	}
	
	public int getWidth()
	{
		return widthInCells * getCellSize();
	}
	
	public int getHeight()
	{
		return heightInCells * getCellSize();
	}
	
	/**
	 * Updates the tiles in the grid.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		tiles.update(timePassed);
		items.update(timePassed);
		units.update(timePassed);
	}
	
	/**
	 * Renders the grid.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		tiles.render(g);
		items.render(g);
		units.render(g);
	}

	public void onUnitDeath(UnitEvent e)
	{
		GridUnit unit = (GridUnit) e.getSource();
		GridCell cell = getCell(unit.getGridX(), unit.getGridY());

		if( cell!=null )
		{
			cell.removeUnit();
		}
	}

	public void onUnitSpawn(UnitEvent e)
	{
	}

	public void onUnitHealthGain(UnitEvent e)
	{
	}

	public void onUnitHealthLoss(UnitEvent e)
	{
	}

	public void onUnitMove(UnitEvent e)
	{
	}
}