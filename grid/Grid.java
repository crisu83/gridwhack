package gridwhack.grid;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import gridwhack.RandomProvider;
import gridwhack.entity.*;
import gridwhack.entity.unit.*;
import gridwhack.entity.unit.event.*;
import gridwhack.entity.tile.*;
import gridwhack.entity.unit.player.Player;
import gridwhack.fov.IViewer;
import gridwhack.path.*;

/**
 * Grid class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
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
	private GridUnit player;
	private Random rand;
	private boolean[][] visible;
	
	/**
	 * Creates the grid.
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

		// initialize the visible matrix.
		visible = new boolean[widthInCells][heightInCells];
		
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
	 * Returns a specific cell in this grid.
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
	 * Creates a path on this grid for a specific unit
	 * from a starting position to a target position.
	 * @param sgx the starting grid x-coordinate.
	 * @param sgy the starting grid y-coordinate.
	 * @param tgx the target grid x-coordinate.
	 * @param tgy the target grid y-coordinate.
	 * @param maxPathLength the maximum allowed path length.
	 * @param mover the unit for which to create the path.
	 * @return the path, or null if no path available.
	 */
	public GridPath getPath(int sgx, int sgy, int tgx, int tgy, int maxPathLength, IMover mover)
	{
		return pf.getPath(sgx, sgy, tgx, tgy, maxPathLength, mover);
	}
	
	/**
	 * Creates tiles and places them on this grid.
	 * @param sgx the starting grid x-coordinate.
	 * @param sgy the starting grid y-coordinate.
	 * @param widthInCells the rectangle width in cells.
	 * @param heightInCells the rectangle height in cells.
	 * @param type the type of tile to create.
	 */
	public void createTileRect(int sgx, int sgy, int widthInCells, int heightInCells, GridTile.Type type)
	{
		// loop through the cells within the area and set the tiles.
		for( int gx=sgx, xmax=(sgx + widthInCells); gx<xmax; gx++ )
		{
			for( int gy=sgy, ymax=(sgy + heightInCells); gy<ymax; gy++ )
			{
				GridTile tile = null;

				try
				{
					// request the tile from the tile factory.
					tile = TileFactory.factory(type, this);
				}
				// TODO: Create an entity not found exception and throw that instead.
				catch( Exception e )
				{
					System.out.println(e.getMessage());
					System.exit(1);
				}
				
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
	 * Adds loot to this grid.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @param loot the loot to add.
	 */
	public synchronized void addLoot(int gx, int gy, GridLoot loot)
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
	 * Creates an unit and adds it in a random cell on this grid.
	 * @param type the type of unit to create.
	 */
	public synchronized void createUnit(GridUnit.Type type)
	{
		GridUnit unit = null;

		try
		{
			// request the unit from the unit factory.
			unit = UnitFactory.factory(type, this);
		}
		// TODO: Create an entity not found exception and throw that instead.
		catch( Exception e )
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		// get a random cell on the grid.
		GridCell cell = getRandomCell();
		
		int gx = cell.getGridX();
		int gy = cell.getGridY();
		
		// add the unit to that random cell.
		addUnit(gx, gy, unit);
	}
	
	/**
	 * Adds an unit to this grid.
	 * @param gx the target grid x-coordinate.
	 * @param gy the target grid y-coordinate.
	 * @param unit the unit to add.
	 */
	public synchronized void addUnit(int gx, int gy, GridUnit unit)
	{
		GridCell cell = getCell(gx, gy);
		
		// make sure the cell exists.
		if( cell!=null && !cell.isBlocked(unit) )
		{
			cell.setUnit(unit);
			units.addEntity(unit);
		}

		unit.addListener(this);
	}

	/**
	 * Returns all units on this grid.
	 * @return the units.
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
	 * @return the units.
	 */
	public ArrayList<GridUnit> getVisibleUnits(GridUnit unit)
	{
		ArrayList<GridUnit> units = new ArrayList<GridUnit>();
		
		// get a matrix representation of which cells on the grid
		// are visible to the unit.
		boolean[][] visible = unit.getFov().getVisible();
		
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
						
						if( target!=null && !units.contains(target) )
						{
							units.add(target);
						}
					}
				}
			}
		}
		
		return units;
	}
	
	/**
	 * Moves a specific unit on this grid.
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
					// engage the target if hostile.
					unit.attack(target);
				}
				else
				{					
					// move the unit to the destination cell.
					source.removeUnit();
					destination.setUnit(unit);
					
					if( unit instanceof Player)
					{
						updateVisible();
					}
				}
			}
		}
	}

	/**
	 * Updates the visible matrix based on the players field of view.
	 */
	public void updateVisible()
	{
		boolean[][] fv = player.getFov().getVisible();

		for( int gx=0; gx<fv.length; gx++ )
		{
			for( int gy=0; gy<fv[gx].length; gy++ )
			{
				if( fv[gx][gy] && !visible[gx][gy] )
				{
					visible[gx][gy] = true;
				}
			}
		}
	}

	/**
	 * Sets the player for this grid.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @param player the player.
	 */
	public void setPlayer(int gx, int gy, Player player)
	{
		this.player = player;

		GridCell cell = getCell(gx, gy);

		// make sure the cell exists.
		if( cell!=null && !cell.isBlocked(player) )
		{
			cell.setUnit(player);
		}
	}
	
	/**
	 * Returns a random non-blocked cell on this grid.
	 * @return the cell.
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
	 * Returns whether a specific cell is blocked.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @param mover the moving unit.
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
	 * Returns whether a specific cell can be seen through.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @param viewer the viewing entity.
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
	 * Returns the movement cost to a specific cell.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @param mover the mover.
	 * @return the cost.
	 */
	public int getMovementCost(int gx, int gy, IMover mover)
	{
		//GridCell cell = getCell(gx, gy);

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
		return (int) (Math.round(offset) / getCellSize());
	}

	/**
	 * Returns the size of one cell in pixels.
	 * @return the cell size.
	 */
	public int getCellSize() 
	{
		return CELL_SIZE;
	}
	
	/**
	 * Returns the width of this grid in cells.
	 * @return the width.
	 */
	public int getGridWidth()
	{
		return widthInCells;
	}
	
	/**
	 * Returns the height of this grid in cells.
	 * @return the height.
	 */
	public int getGridHeight()
	{
		return heightInCells;
	}

	/**
	 * Returns the width of this grid in cells.
	 * @return the width.
	 */
	public int getWidth()
	{
		return widthInCells * getCellSize();
	}

	/**
	 * Returns the height of this grid in cells.
	 * @return the height.
	 */
	public int getHeight()
	{
		return heightInCells * getCellSize();
	}
	
	/**
	 * Updates the entities on this grid.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		tiles.update(timePassed);
		items.update(timePassed);
		units.update(timePassed);
	}
	
	/**
	 * Renders this grid.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		ArrayList<ArrayList<CEntity>> allEntities = new ArrayList<ArrayList<CEntity>>();
		
		allEntities.add(tiles.getEntities());
		allEntities.add(items.getEntities());
		allEntities.add(units.getEntities());

		for( ArrayList<CEntity> entities : allEntities )
		{
			for( CEntity entity : entities )
			{
				GridEntity ge = (GridEntity) entity;

				if( ge instanceof GridTile || ge instanceof GridLoot )
				{
					if( visible[ ge.getGridX() ][ ge.getGridY() ] )
					{
						ge.render(g);
					}
				}
				else
				{
					if( player.getFov().isVisible(ge.getGridX(), ge.getGridY()) )
					{
						ge.render(g);
					}
				}
			}
		}

		player.render(g);

		/*
		tiles.render(g);
		items.render(g);
		units.render(g);
		*/
	}

	/**
	 * Actions to be taken when the unit dies.
	 * @param e the event.
	 */
	public void onUnitDeath(UnitEvent e)
	{
		GridUnit unit = (GridUnit) e.getSource();
		GridCell cell = getCell(unit.getGridX(), unit.getGridY());

		// make sure the cell exists.
		if( cell!=null )
		{
			cell.removeUnit();
		}
	}

	/**
	 * Actions to be taken when the unit is spawned.
	 * @param e the event.
	 */
	public void onUnitSpawn(UnitEvent e) {}

	/**
	 * Actions to be taken when the unit gains health.
	 * @param e the event.
	 */
	public void onUnitHealthGain(UnitEvent e) {}

	/**
	 * Actions to be taken when the unit loses health.
	 * @param e the event.
	 */
	public void onUnitHealthLoss(UnitEvent e) {}

	/**
	 * Actions to be taken when the unit moves.
	 * @param e the event.
	 */
	public void onUnitMove(UnitEvent e) {}
}