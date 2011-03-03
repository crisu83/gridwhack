package gridwhack.grid;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import gridwhack.exception.ComponentNotFoundException;
import gridwhack.RandomProvider;
import gridwhack.entity.*;
import gridwhack.entity.character.*;
import gridwhack.entity.character.Character;
import gridwhack.entity.character.event.*;
import gridwhack.entity.tile.*;
import gridwhack.entity.character.player.Player;
import gridwhack.fov.IViewer;
import gridwhack.path.*;

/**
 * Grid class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Grid implements ICharacterDeathListener, ICharacterMoveListener, ICharacterSpawnListener
{
	private static final int CELL_SIZE = 32;
	
	protected int widthInCells;
	protected int heightInCells;
	private GridCell[][] cells;
	private GridAStarPathFinder pf;
	private CEntityManager tiles;
	private CEntityManager loots;
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
		
		// initialize the cells as an init grid cell matrix.
		cells = new GridCell[widthInCells][heightInCells];
		
		// create a new path finder that uses the euclidean heuristic
		// that can be used to calculate paths for units on the grid.
		pf = new GridAStarPathFinder(new EuclideanHeuristic(), this);
		
		// create entity managers to handle 
		// tiles, loots and units on the grid.
		tiles = new CEntityManager();
		loots = new CEntityManager();
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
	public synchronized void createTileRect(int sgx, int sgy, int widthInCells, int heightInCells, GridTile.Type type)
	{
		// Loop through the cells within the area and set the tiles.
		for( int gx=sgx, xmax=(sgx + widthInCells); gx<xmax; gx++ )
		{
			for( int gy=sgy, ymax=(sgy + heightInCells); gy<ymax; gy++ )
			{
				GridTile tile = null;

				try
				{
					// Request the tile from the tile factory.
					tile = TileFactory.factory(type, this);
				}
				catch( ComponentNotFoundException e )
				{
					System.out.println(e.getMessage());
					System.exit(1);
				}
				
				GridCell cell = getCell(gx, gy);
				
				// Make sure the cell exists.
				if( cell!=null )
				{
					cell.setTile(tile);
					tiles.addEntity(tile);
				}
			}
		}
	}

	/**
	 * Adds loots to this grid.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @param loot the loots to add.
	 */
	public synchronized void addLoot(int gx, int gy, GridLoot loot)
	{
		GridCell cell = getCell(gx, gy);

		// Make sure the cell exists.
		if( cell!=null )
		{
			cell.addLoot(loot);
			loots.addEntity(loot);
		}
	}

	/**
	 * Creates an unit and adds it in a random cell on this grid.
	 * @param type the type of unit to create.
	 */
	public void createUnit(Character.Type type)
	{
		GridUnit unit = null;

		try
		{
			// Request the unit from the unit factory.
			unit = CharacterFactory.factory(type, this);
		}
		catch( ComponentNotFoundException e )
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		// Get a random cell on the grid
		// and add the unit to that cell.
		GridCell cell = getRandomCell();
		addUnit(cell.getGridX(), cell.getGridY(), unit);
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
		
		// Make sure the cell exists and is not blocked.
		if( cell!=null && !cell.isBlocked(unit) )
		{
			cell.setUnit(unit);
			units.addEntity(unit);
			unit.addListener(this); // let the grid listen to the unit.
		}
		// Could not add unit.
		else
		{
			System.out.println("Could not add unit to the grid. Target cell is blocked or does not exist!");
		}
	}
	
	/**
	 * Returns all units that a specific unit can see.
	 * @param unit the unit for which to get visible units.
	 * @return the units.
	 */
	public ArrayList<GridUnit> getVisibleUnits(GridUnit unit)
	{
		// Get a matrix representation for node the unit can see.
		boolean[][] visible = unit.getFov().getVisible();

		ArrayList<GridUnit> units = new ArrayList<GridUnit>();
		
		// Loop through all the nodes in the matrix.
		for( int gx=0, width=visible.length; gx<width; gx++ )
		{
			for( int gy=0, height=visible[gx].length; gy<height; gy++ )
			{
				// Check if the node is visible.
				if( visible[gx][gy] )
				{
					GridCell cell = getCell(gx, gy);
					
					// Make sure the cell exists.
					if( cell!=null )
					{
						GridUnit cellUnit = cell.getUnit();

						// Make sure that there is a unit in the cell
						// and that it is not already added to the units.
						if( cellUnit!=null && !units.contains(cellUnit) )
						{
							units.add(cellUnit);
						}
					}
				}
			}
		}
		
		return units;
	}
	
	/**
	 * Moves a specific unit on this grid.
	 * @param dgx the delta x in grid cells.
	 * @param dgy the delta y in grid cells.
	 * @param unit the unit to move.
	 */
	public void moveUnit(int dgx, int dgy, GridUnit unit)
	{
		// Get the unit's position on the grid.
		int gx = unit.getGridX();
		int gy = unit.getGridY();

		// Get the cell this unit is moving from.
		GridCell source = getCell(gx, gy);
		
		// Make sure that the source cell exists.
		if( source!=null )
		{
			// Get the cell the unit is moving to.
			GridCell destination = getCell(gx+dgx, gy+dgy);
			
			// Make sure that the destination cell exists
			// and that it is not blocked.
			if( destination!=null && !destination.isBlocked(unit) )
			{
				// Get the unit in the destination cell
				GridUnit target = destination.getUnit();

				// Check whether there is a unit in that cell.
				if( target==null )
				{
					// Move the unit to the destination cell.
					source.removeUnit();
					destination.setUnit(unit);
				}
				// Another unit is occupying the destination cell.
				else
				{
					handleUnitCollision(unit, target);
				}
			}
		}
	}

	/**
	 * Handles a collision between two units.
	 * @param unit the unit colliding with the other unit.
	 * @param other the unit other unit.
	 */
	private void handleUnitCollision(GridUnit unit, GridUnit other)
	{
		Character character = (Character) unit;
		Character target = (Character) other;

		if( character.isHostile(target) )
		{
			character.attack(target);
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
		GridCell cell = getCell(gx, gy);

		// Make sure the cell exists.
		if( cell!=null && !cell.isBlocked(player) )
		{
			this.player = player;
			cell.setUnit(player);
			player.addListener(this); // let the grid listen to the player.
			player.markSpawned();
		}
		else
		{
			System.out.println("Could not add player to the grid. Target cell is blocked or does not exist!");
		}
	}

	/**
	 * Updates the visible matrix based on the players field of view.
	 */
	public void updateVisible()
	{
		if( player instanceof Player )
		{
			boolean[][] playerVisible = player.getFov().getVisible();

			for( int gx=0; gx<playerVisible.length; gx++ )
			{
				for( int gy=0; gy<playerVisible[gx].length; gy++ )
				{
					if( !visible[gx][gy] && playerVisible[gx][gy] )
					{
						visible[gx][gy] = true;
					}
				}
			}
		}
	}

	/**
	 * Returns a random non-blocked cell on this grid.
	 * @return the cell.
	 */
	public GridCell getRandomCell()
	{
		int tryCount = 0;
		int maxTries = 100; // we allow 100 tries
		
		while( tryCount++<maxTries )
		{
			// Get a random cell on the grid.
			GridCell cell = getCell(rand.nextInt(getWidthInCells()), rand.nextInt(getHeightInCells()));
			
			// Make sure the cell exists and is not blocked.
			if( cell!=null && !cell.isBlocked(null) )
			{
				return cell;
			}
		}
		
		// All cells are blocked or no cells exist.
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
	// TODO: Implement some logic for this.
	public int getMovementCost(int gx, int gy, IMover mover)
	{
		return 1;
	}
	
	/**
	 * Updates the entities on this grid.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		tiles.update(timePassed);
		loots.update(timePassed);
		units.update(timePassed);
		player.update(timePassed);
	}

	/**
	 * Renders this grid.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		// Get a matrix representation of the player's field of view.
		boolean[][] playerVisible = player.getFov().getVisible();

		// Render the tiles.
		for( CEntity entity : tiles.getEntities() )
		{
			GridTile tile = (GridTile) entity;

			if( visible[ tile.getGridX() ][ tile.getGridY() ] )
			{
				tile.render(g);
			}
		}

		// Render the loots.
		for( CEntity entity : loots.getEntities() )
		{
			GridLoot loot = (GridLoot) entity;

			if( visible[ loot.getGridX() ][ loot.getGridY() ] )
			{
				loot.render(g);
			}
		}

		// Render the loots.
		for( CEntity entity : units.getEntities() )
		{
			GridUnit unit = (GridUnit) entity;

			if( playerVisible[ unit.getGridX() ][ unit.getGridY() ] )
			{
				unit.render(g);
			}
		}

		// Render the player.
		player.render(g);
	}

	/**
	 * Actions to be taken when the unit dies.
	 * @param e the event.
	 */
	public void onCharacterDeath(CharacterEvent e)
	{
		Character character = (Character) e.getSource();
		GridCell cell = getCell(character.getGridX(), character.getGridY());

		// Make sure the cell exists.
		if( cell!=null )
		{
			cell.removeUnit();
		}
	}

	/**
	 * Actions to be taken when the unit moves.
	 * @param e the event.
	 */
	public void onCharacterMove(CharacterEvent e)
	{
		Character character = (Character) e.getSource();

		// We only need to update the visible matrix when the player is moving.
		if( character instanceof Player )
		{
			updateVisible();
		}
	}

	/**
	 * Actions to be taken when the character spawns.
	 * @param e the event.
	 */
	public void onCharacterSpawn(CharacterEvent e)
	{
		Character character = (Character) e.getSource();

		// We only need to update the visible matrix when the player is moving.
		if( character instanceof Player )
		{
			updateVisible();
		}
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
		return (int) Math.round(offset) / getCellSize();
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
	public int getWidthInCells()
	{
		return widthInCells;
	}

	/**
	 * Returns the height of this grid in cells.
	 * @return the height.
	 */
	public int getHeightInCells()
	{
		return heightInCells;
	}

	/**
	 * Returns the width of this grid in pixels.
	 * @return the width.
	 */
	public int getWidth()
	{
		return widthInCells * getCellSize();
	}

	/**
	 * Returns the height of this grid in pixels.
	 * @return the height.
	 */
	public int getHeight()
	{
		return heightInCells * getCellSize();
	}
}