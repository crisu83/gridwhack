package gridwhack.entity.unit;

import java.util.ArrayList;

import gridwhack.entity.item.HealthOrb;
import gridwhack.entity.item.Loot;
import gridwhack.grid.Grid;
import gridwhack.grid.GridUnit;
import gridwhack.gui.unit.HealthBar;
import gridwhack.path.Path.Step;

public abstract class NPCUnit extends GridUnit
{	
	protected GridUnit target;
	
	/**
	 * Constructs the non-player unit.
	 * @param filename the sprite filename.
	 * @param grid the grid the unit exists on.
	 */
	public NPCUnit(String filename, Grid grid)
	{
		super(filename, grid);

		// Create a health bar to represent the unit health.
		healthBar = new HealthBar(0, 0, 30, 2, this);
	}
	
	/**
	 * @return the closest hostile unit.
	 */
	public GridUnit getClosestVisibleHostileUnit()
	{		
		GridUnit closest = null;
		int lowestCost= 0;
		
		ArrayList<GridUnit> units = grid.getVisibleUnits(this);
		
		// loop through all the visible units.
		for( GridUnit target : units )
		{
			if( target!=this )
			{
				int cost = getDistanceCost(target);
				
				// make sure the unit is hostile.
				if( isHostile(target) )
				{
					// check if we have no closest unit or if the unit is closer 
					// than the unit currently marked as the closest unit.
					if( closest==null || cost<lowestCost ) 
					{
						lowestCost = cost;
						closest = target;
					}
				}
			}
		}
		
		return closest;
	}
	
	/**
	 * Calculates the distance to a specific unit.
	 * @param target the unit to get the distance to.
	 * @return the distance in grid cells.
	 */
	public int getDistanceCost(GridUnit target)
	{
		// calculate delta between the unit and the target.
		int dgx = this.getGridX() - target.getGridX();
		int dgy = this.getGridY() - target.getGridY();
		
		// use pythagorean theorem to determine the distance. (a^2 + b^2 = c^2)
		// might not be the perfect solution but good enough for now.
		int cost = (int)Math.round( Math.sqrt( (dgx*dgx) + (dgy*dgy) ) );
		
		return cost;
	}
	
	/**
	 * Moves the unit across its path.
	 */
	public void moveAlongPath()
	{
		// make sure we have a path.
		if( path!=null )
		{
			// make sure there is a next step.
			if( path.hasNextStep() )
			{
				Step step = path.getNextStep();
			
				// calculate the deltas.
				int dgx = step.getX() - this.getGridX();
				int dgy = step.getY() - this.getGridY();
				
				grid.moveUnit(dgx, dgy, this);
			}
			// clear the path once its moved across.
			else
			{
				path = null;
			}
		}
	}
	
	public boolean isPathValid()
	{
		int pathLength = path.getLength();
		Step step = path.getStep(pathLength-1);
		
		GridUnit target = getTarget();
		
		// compare the coordinates of the last step in the path
		// to the target coordinates.
		// TODO: Improve GridPath to take into account cell size automatically.
		if( (step.getX()*grid.getCellSize()==target.getGridX()) 
			&& (step.getY()*grid.getCellSize()==target.getGridY()) )
		{
			return true;
		}

		// path is invalid.
		return false;
	}
	
	/**
	 * Actions to be taken when the unit moves.
	 */
	public void move()
	{
		// move unit if it is allowed to move.
		if( movementAllowed() )
		{
			// check if the unit has a path.
			if( path!=null )
			{
				moveAlongPath();
				
				// mark the unit to have moved.
				markMoved();
			}
			// unit has no path.
			else
			{
				// determine in which direction the unit should move
				// by simply randomizing the direction without any further logic.
				int d = rand.nextInt(Directions.values().length);
				Directions direction = Directions.values()[d];
				
				// move in the random direction.
				move(direction);
			}
		}
	}
	
	/**
	 * Update the non-player unit.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		// always engage the closest visible hostile unit.
		GridUnit target = getClosestVisibleHostileUnit();
		
		// we found a target.
		if( target!=null )
		{
			setTarget(target);
			
			// create a path to the target unless the unit already has a valid path.
			if( path==null || !isPathValid() )
			{
				path = getPath(target.getGridX(), target.getGridY(), this.getViewRange());
			}
		}
		
		// move the unit.
		move();
	}
	
	/**
	 * Marks the unit dead.
	 */
	public synchronized void markDead()
	{
		this.createLoot();
		
		super.markDead();
	}
	
	/**
	 * Creates loot.
	 */
	protected void createLoot()
	{
		Loot loot = new Loot(grid);
		loot.addItem(new HealthOrb());
		grid.addLoot(this.getGridX(), this.getGridY(), loot);
	}
	
	/**
	 * @return the unit to target.
	 */
	public GridUnit getTarget()
	{
		return target;
	}
	
	/**
	 * @param target unit to target.
	 */
	public void setTarget(GridUnit target)
	{
		this.target = target;
	}
}
