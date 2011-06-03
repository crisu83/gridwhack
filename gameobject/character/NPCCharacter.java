package gridwhack.gameobject.character;

import java.awt.*;
import java.io.Console;
import java.util.ArrayList;

import gridwhack.RandomProvider;
import gridwhack.base.BaseObject;
import gridwhack.gameobject.grid.Grid;
import gridwhack.gameobject.loot.Loot;
import gridwhack.gameobject.unit.Unit;
import gridwhack.gui.character.HealthBar;
import gridwhack.path.Path.Step;
import gridwhack.util.Vector2;

public abstract class NPCCharacter extends Character
{
	// ----------
	// Properties
	// ----------

	protected HealthBar healthBar;
	protected Character target;

	// -------
	// Methods
	// -------
	
	/**
	 * Creates the object.
	 */
	public NPCCharacter()
	{
		super();

		// Create a health bar to represent the character health.
		healthBar = new HealthBar(0, 0, 30, 2, this);
	}
	
	/**
	 * @return the closest hostile character.
	 */
	public Character getClosestVisibleHostileCharacter()
	{		
		Character closest = null;
		int lowestCost = 0;
		
		ArrayList<Unit> units = grid.getVisibleUnits(this);
		
		// loop through all the visible units.
		for (Unit unit : units)
		{
			if( unit!=this )
			{
				Character target = (Character) unit;

				int cost = getDistanceCost(target);
				
				// make sure the character is hostile.
				if (isHostile(target))
				{
					// check if we have no closest character or if the character is closer
					// than the character currently marked as the closest character.
					if (closest == null || cost < lowestCost)
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
	public int getDistanceCost(Unit target)
	{
		Vector2 position = getGridPosition();
		Vector2 targetPosition = target.getGridPosition();

		// use pythagorean theorem to determine the distance. (a^2 + b^2 = c^2)
		// might not be the perfect solution but good enough for now.
		int cost = (int) Math.round( Math.sqrt( position.distance(targetPosition) ) );
		
		return cost;
	}
	
	/**
	 * Moves the character across its path.
	 */
	public void moveAlongPath()
	{
		// Make sure we have a path.
		if (path != null)
		{
			// Make sure there is a next step.
			if (path.hasNextStep())
			{
				Step step = path.getNextStep();
				Vector2 position = getGridPosition();

				// Calculate the deltas.
				int dgx = step.getX() - (int) position.x;
				int dgy = step.getY() - (int) position.y;
				
				grid.moveUnit(dgx, dgy, this);
			}
			// Clear the path once its moved across.
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
		
		Character target = getTarget();
		Vector2 targetPosition = target.getGridPosition();
		
		// compare the coordinates of the last step in the path
		// to the target coordinates.
		// TODO: Improve GridPath to take into account cell size automatically.
		if( (step.getX() * grid.getCellSize() == targetPosition.x)
			&& (step.getY() * grid.getCellSize() == targetPosition.y))
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
		if (movementAllowed())
		{
			// check if the character has a path.
			if (path != null)
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
				int d = RandomProvider.getRand().nextInt(Directions.values().length);
				Directions direction = Directions.values()[d];
				
				// move in the random direction.
				move(direction);
			}
		}
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
		Loot loot = new Loot();

		// Create some random loot.
		loot.createRandomItems();

		// Make sure that we have items.
		if (loot.getItemCount() > 0)
		{
			Vector2 position = getGridPosition();
			loot.setGridPosition(position);
			grid.addLoot(loot);
		}
	}

	/**
	 * Update the object.
	 * @param parent The parent object.
	 */
	public void update(BaseObject parent)
	{
		super.update(parent);

		// always engage the closest visible hostile character.
		Character target = getClosestVisibleHostileCharacter();

		// we found a target.
		if (target != null)
		{
			setTarget(target);

			// spawn a path to the target unless the character already has a valid path.
			if (path == null || !isPathValid())
			{
				path = getPath(target.getGridX(), target.getGridY(), getViewRange());
			}
		}

		// move the character.
		move();
	}

	/**
	 * Draws the object.
	 * @param g The graphics context.
	 */
	@Override
	public void draw(Graphics2D g)
	{
		if (!dead)
		{
			super.draw(g);

			healthBar.draw(g);

			/*
			if (path != null)
			{
				path.draw(g);
			}
			*/
		}
	}

	/**
	 * @return the character to target.
	 */
	public Character getTarget()
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
}
