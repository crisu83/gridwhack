package gridwhack.entity.character;

import java.awt.*;
import java.util.ArrayList;

import gridwhack.grid.Grid;
import gridwhack.grid.GridLoot;
import gridwhack.grid.GridUnit;
import gridwhack.gui.unit.HealthBar;
import gridwhack.path.Path.Step;

public abstract class NPCCharacter extends Character
{
	protected HealthBar healthBar;
	
	protected Character target;

	/**
	 * Constructs the non-player character.
	 * @param filename the sprite filename.
	 * @param grid the grid the character exists on.
	 */
	public NPCCharacter(String filename, Grid grid)
	{
		super(filename, grid);

		// Create a health bar to represent the character health.
		healthBar = new HealthBar(0, 0, 30, 2, this);
	}
	
	/**
	 * @return the closest hostile character.
	 */
	public GridUnit getClosestVisibleHostileCharacters()
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
				
				// make sure the character is hostile.
				if( isHostile( (Character) target) )
				{
					// check if we have no closest character or if the character is closer
					// than the character currently marked as the closest character.
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
	 * Calculates the distance to a specific character.
	 * @param target the character to get the distance to.
	 * @return the distance in grid cells.
	 */
	public int getDistanceCost(GridUnit target)
	{
		// calculate delta between the character and the target.
		int dgx = this.getGridX() - target.getGridX();
		int dgy = this.getGridY() - target.getGridY();
		
		// use pythagorean theorem to determine the distance. (a^2 + b^2 = c^2)
		// might not be the perfect solution but good enough for now.
		int cost = (int)Math.round( Math.sqrt( (dgx*dgx) + (dgy*dgy) ) );
		
		return cost;
	}
	
	/**
	 * Moves the character across its path.
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

	/**
	 * Returns whether the current path is valid.
	 * @return whether the path is valid.
	 */
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
	 * Actions to be taken when the character moves.
	 */
	public void move()
	{
		// move character if it is allowed to move.
		if( movementAllowed() )
		{
			// check if the character has a path.
			if( path!=null )
			{
				moveAlongPath();
				
				// mark the character to have moved.
				markMoved();
			}
			// character has no path.
			else
			{
				// determine in which direction the character should move
				// by simply randomizing the direction without any further logic.
				int d = rand.nextInt(Directions.values().length);
				Directions direction = Directions.values()[d];
				
				// move in the random direction.
				move(direction);
			}
		}
	}
	
	/**
	 * Update the non-player character.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		// always engage the closest visible hostile character.
		GridUnit target = getClosestVisibleHostileCharacters();
		
		// we found a target.
		if( target!=null )
		{
			setTarget( (Character) target );
			
			// create a path to the target unless the character already has a valid path.
			if( path==null || !isPathValid() )
			{
				path = getPath(target.getGridX(), target.getGridY(), this.getViewRange());
			}
		}
		
		// move the character.
		move();
	}
	
	/**
	 * Marks the character dead.
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
		GridLoot loot = new GridLoot(grid);

		// Create some random loot.
		loot.createRandomItems();

		// Make sure that we have items.
		if( loot.getItemCount()>0 )
		{
			grid.addLoot(getGridX(), getGridY(), loot);
		}
	}
	
	/**
	 * @return the character to target.
	 */
	public GridUnit getTarget()
	{
		return target;
	}
	
	/**
	 * @param target character to target.
	 */
	public void setTarget(Character target)
	{
		this.target = target;
	}

	/**
	 * Renders the character.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		if( !dead )
		{
			super.render(g);

			healthBar.render(g);
		}
	}
}
