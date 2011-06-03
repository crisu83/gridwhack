package gridwhack.gameobject.grid;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import gridwhack.base.BaseObject;
import gridwhack.base.BaseCollection;
import gridwhack.RandomProvider;
import gridwhack.gameobject.DrawableGameObject;
import gridwhack.gameobject.character.Character;
import gridwhack.gameobject.character.event.*;
import gridwhack.gameobject.character.player.Player;
import gridwhack.fov.IViewer;
import gridwhack.gameobject.GameObjectManager;
import gridwhack.gameobject.loot.Loot;
import gridwhack.gameobject.tile.Tile;
import gridwhack.gameobject.unit.Unit;
import gridwhack.path.*;
import gridwhack.util.Vector2;

/**
 * Grid class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Grid extends DrawableGameObject implements ICharacterDeathListener, ICharacterMoveListener, ICharacterSpawnListener
{
	// ----------
	// Properties
	// ----------

	private static final int CELL_SIZE = 32;

	protected int widthInCells;
	protected int heightInCells;
	private GridCell[][] cells;
	private GridAStarPathFinder pf;
	private GameObjectManager tiles;
	private GameObjectManager loots;
	private GameObjectManager characters;
	private GameObjectManager players;
	private Player player;
	private Random rand;
	private boolean[][] visible;

	// -------
	// Methods
	// -------

	/**
	 * Creates the grid.
	 * @param widthInCells The width of the grid in cells.
	 * @param heightInCells The height of the grid in cells.
	 */
	public Grid(int widthInCells, int heightInCells)
	{
		this.widthInCells = widthInCells;
		this.heightInCells = heightInCells;

		// initialize the cells as an init grid cell matrix.
		cells = new GridCell[widthInCells][heightInCells];

		// spawn a new path finder that uses the euclidean heuristic
		// that can be used to calculate paths for characters on the grid.
		pf = new GridAStarPathFinder(new EuclideanHeuristic(), this);

		// spawn entity managers to handle
		// tiles, loots, characters and players on the grid.
		tiles = new GameObjectManager();
		loots = new GameObjectManager();
		characters = new GameObjectManager();
		players = new GameObjectManager();

		// get random from the random provider. 
		rand = RandomProvider.getRand();

		// initialize the visible matrix.
		visible = new boolean[widthInCells][heightInCells];

		// add cells to the grid.
		for (int gx = 0; gx < widthInCells; gx++)
		{
			for (int gy = 0; gy < heightInCells; gy++)
			{
				cells[gx][gy] = new GridCell(gx, gy);
			}
		}
	}

	/**
	 * Returns a specific cell in this grid.
	 * @param gx The grid x-coordinates of the cell.
	 * @param gy The grid y-coordinates of the cell.
	 * @return The cell, or null if cell not found.
	 */
	public GridCell getCell(int gx, int gy)
	{
		if (gx >= 0 && gy >= 0 && gx < widthInCells && gy < heightInCells)
		{
			return cells[gx][gy];
		}

		return null;
	}

	/**
	 * Creates a path on this grid.
	 * @param sgx The starting grid x-coordinate.
	 * @param sgy The starting grid y-coordinate.
	 * @param tgx The target grid x-coordinate.
	 * @param tgy The target grid y-coordinate.
	 * @param maxPathLength The maximum allowed path length.
	 * @param mover The mover for which to create the path.
	 * @return The path, or null if no path available.
	 */
	public GridPath getPath(int sgx, int sgy, int tgx, int tgy, int maxPathLength, IMover mover)
	{
		return pf.getPath(sgx, sgy, tgx, tgy, maxPathLength, mover);
	}

	/**
	 * Adds multiple tiles to this grid.
	 * @param tiles The tiles to add.
	 */
	public void addTiles(BaseCollection tiles)
	{
		for (int i = 0, length = tiles.getSize(); i < length; i++)
		{
			addTile((Tile) tiles.get(i));
		}
	}

	/**
	 * Adds a single tile to this grid.
	 * @param tile The tille to add.
	 * @return Whether the tile was added.
	 */
	public boolean addTile(Tile tile)
	{
		Vector2 gp = tile.getGridPosition();

		if (gp != null)
		{
			GridCell cell = getCell((int) gp.x, (int) gp.y);

			if (cell != null)
			{
				cell.setTile(tile);
				tiles.add(tile);
				return true;
			}
		}

		return false;
	}

	/**
	 * Adds multiple characters to this grid.
	 * @param characters The characters to add.
	 */
	public void addCharacters(BaseCollection characters)
	{
		for (int i = 0, length = characters.getSize(); i < length; i++)
		{
			addCharacter((Character) characters.get(i));
		}
	}

	/**
	 * Adds a single character to this grid.
	 * @param character The character to add.
	 * @return Whether the character was added.
	 */
	public boolean addCharacter(Character character)
	{
		Vector2 gp = character.getGridPosition();

		if (gp != null)
		{
			GridCell cell = getCell((int) gp.x, (int) gp.y);

			if (cell != null)
			{
				cell.setUnit(character);
				character.addListener(this);
				characters.add(character);
				return true;
			}
		}

		return false;
	}

	/**
	 * Adds loot to this grid.
	 * @param loot The loots to add.
	 * @return Whether the loot was added.
	 */
	public boolean addLoot(Loot loot)
	{
		Vector2 gp = loot.getGridPosition();

		if (gp != null)
		{
			GridCell cell = getCell((int) gp.x, (int) gp.y);

			if (cell != null)
			{
				cell.addLoot(loot);
				loots.add(loot);
				return true;
			}
		}

		return false;
	}

	/**
	 * Sets the player on this grid.
	 * @param player The player.
	 * @return Whether the player was set.
	 */
	public boolean setPlayer(Player player)
	{
		Vector2 gp = player.getGridPosition();

		if (gp != null)
		{
			GridCell cell = getCell((int) gp.x, (int) gp.y);

			if (cell != null)
			{
				cell.setUnit(player);
				player.addListener(this);
				this.player = player;
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns all characters that a specific unit can see.
	 * @param unit the unit for which to get visible characters.
	 * @return the characters.
	 */
	public ArrayList<Unit> getVisibleUnits(Unit unit)
	{
		// Get a matrix representation for node the unit can see.
		boolean[][] visible = unit.getFov().getVisible();

		ArrayList<Unit> units = new ArrayList<Unit>();

		// Loop through all the nodes in the matrix.
		for (int gx = 0, width = visible.length; gx < width; gx++)
		{
			for (int gy = 0, height = visible[gx].length; gy < height; gy++)
			{
				// Check if the node is visible.
				if (visible[gx][gy])
				{
					GridCell cell = getCell(gx, gy);

					if (cell != null)
					{
						Unit other = cell.getUnit();

						// Make sure that there is a unit in the cell
						// and that it is not already added to the characters.
						if (other != null && !units.contains(other))
						{
							units.add(other);
						}
					}
				}
			}
		}

		return units;
	}

	/**
	 * Moves a specific unit on this grid.
	 * @param unit the unit to move.
	 */
	public void moveUnit(int gx, int gy, Unit unit)
	{
		Vector2 delta = new Vector2(gx, gy);

		// Get the unit's position on the grid.
		Vector2 position = unit.getGridPosition();

		// Get the cell this unit is moving from.
		GridCell source = getCell((int) position.x, (int) position.y);

		// Make sure that the source cell exists.
		if (source != null)
		{
			// Get the cell the unit is moving to.
			position.add(delta);
			GridCell destination = getCell((int) position.x, (int) position.y);

			// Make sure that the destination cell exists
			// and that it is not blocked.
			if (destination != null && !destination.isBlocked(unit))
			{
				// Get the unit in the destination cell
				Unit other = destination.getUnit();

				// Check whether there is a unit in that cell.
				if (other == null)
				{
					// Move the unit to the destination cell.
					source.setUnit(null);
					destination.setUnit(unit);
				}
				// Another unit is occupying the destination cell.
				else
				{
					handleUnitCollision(unit, other);
				}
			}
		}
	}

	/**
	 * Handles a collision between two characters.
	 * @param unit the unit colliding with the other unit.
	 * @param other the unit other unit.
	 */
	private void handleUnitCollision(Unit unit, Unit other)
	{
		Character character = (Character) unit;
		Character target = (Character) other;

		if (character.isHostile(target))
		{
			character.attack(target);
		}
	}

	/**
	 * Updates the visible matrix based on the players field of view.
	 */
	public void updateVisible()
	{
		if (player instanceof Player)
		{
			boolean[][] playerVisible = player.getFov().getVisible();

			for (int gx = 0; gx < playerVisible.length; gx++)
			{
				for (int gy = 0; gy < playerVisible[gx].length; gy++)
				{
					if (!visible[gx][gy] && playerVisible[gx][gy])
					{
						visible[gx][gy] = true;
					}
				}
			}
		}
	}

	/**
	 * Returns whether a specific cell is blocked.
	 * @param gx The grid x-coordinate of the cell.
	 * @param gy The grid y-coordinate of the cell.
	 * @param mover The mover.
	 * @return Whether the cell is blocked.
	 */
	public boolean isBlocked(int gx, int gy, IMover mover)
	{
		GridCell cell = getCell(gx, gy);

		if (cell != null)
		{
			return cell.isBlocked(mover);
		}

		return true;
	}

	/**
	 * Returns whether a specific cell can be seen through.
	 * @param gx The grid x-coordinate of the cell.
	 * @param gy The grid y-coordinate of the cell.
	 * @param viewer The viewer.
	 * @return Whether the cell can be seen through.
	 */
	public boolean isSolid(int gx, int gy, IViewer viewer)
	{
		GridCell cell = getCell(gx, gy);

		if (cell != null)
		{
			return cell.isSolid(viewer);
		}

		return true;
	}

	/**
	 * Returns the movement cost to a specific cell.
	 * @param gx The grid x-coordinate of the cell..
	 * @param gy The grid y-coordinate of the cell..
	 * @param mover The mover.
	 * @return the cost.
	 */
	// TODO: Implement some logic for this.
	public int getMovementCost(int gx, int gy, IMover mover)
	{
		return 1;
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Updates this object.
	 * @param parent The parent object.
	 */
	@Override
	public void update(BaseObject parent)
	{
		tiles.update(this);
		loots.update(this);
		characters.update(this);
		player.update(this);
	}

	/**
	 * Draws the object.
	 * @param g The graphics context.
	 */
	@Override
	public void draw(Graphics2D g)
	{
		// Get a matrix representation of the player's field of view.
		boolean[][] playerVisible = player.getFov().getVisible();

		BaseCollection tileCollection = tiles.getObjects();
		int tileCount = tileCollection.getSize();

		if (tileCount > 0)
		{
			for (int i = 0; i < tileCount; i++)
			{
				Tile tile = (Tile) tileCollection.get(i);

				if (visible[tile.getGridX()][tile.getGridY()])
				{
					tile.draw(g);
				}
			}
		}

		BaseCollection lootCollection = loots.getObjects();
		int lootCount = lootCollection.getSize();

		if (lootCount > 0)
		{
			for (int i = 0; i < lootCount; i++)
			{
				Loot loot = (Loot) lootCollection.get(i);

				if (visible[loot.getGridX()][loot.getGridY()])
				{
					loot.draw(g);
				}
			}
		}

		BaseCollection characterCollection = characters.getObjects();
		int characterCount = characterCollection.getSize();

		if (characterCount > 0)
		{
			for (int i = 0; i < characterCount; i++)
			{
				Character character = (Character) characterCollection.get(i);

				if (playerVisible[character.getGridX()][character.getGridY()])
				{
					character.draw(g);
				}
			}
		}

		player.draw(g);
	}

	// --------------
	// Event handlers
	// --------------

	/**
	 * Actions to be taken when the unit dies.
	 * @param e The event.
	 */
	public void onCharacterDeath(CharacterEvent e)
	{
		Character character = (Character) e.getSource();
		GridCell cell = getCell(character.getGridX(), character.getGridY());

		if (cell != null)
		{
			cell.setUnit(null);
		}
	}

	/**
	 * Actions to be taken when the unit moves.
	 * @param e The event.
	 */
	public void onCharacterMove(CharacterEvent e)
	{
		Character character = (Character) e.getSource();

		// We only need to update the visible matrix when the player is moving.
		if (character instanceof Player)
		{
			updateVisible();
		}
	}

	/**
	 * Actions to be taken when the character spawns.
	 * @param e The event.
	 */
	public void onCharacterSpawn(CharacterEvent e)
	{
		Character character = (Character) e.getSource();

		// We only need to update the visible matrix when the player is spawned.
		if (character instanceof Player)
		{
			updateVisible();
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

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

	public Player getPlayer()
	{
		return player;
	}
}