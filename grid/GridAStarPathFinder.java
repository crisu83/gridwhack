package gridwhack.grid;

import java.util.ArrayList;
import java.util.Collections;

import gridwhack.path.*;

/**
 * Grid A* path finder class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GridAStarPathFinder implements IPathFinder
{
	protected ArrayList<Node> include;
	protected ArrayList<Node> exclude;
	
	protected Node[][] nodes;
	protected IAStarHeuristic heuristic;
	protected Grid grid;
	
	/**
	 * Creates the path finder.
	 * @param heuristic the heuristic to use.
	 * @param grid the grid on which to find the path.
	 */
	public GridAStarPathFinder(IAStarHeuristic heuristic, Grid grid)
	{
		this.heuristic = heuristic;
		this.grid = grid;
		
		include = new ArrayList<Node>();
		exclude = new ArrayList<Node>();
	}
	
	/**
	 * Clears the path finder.
	 */
	public void clear()
	{
		include.clear();
		exclude.clear();
		
		addNodes();
	}
	
	/**
	 * Adds nodes to the path finder.
	 */
	public void addNodes()
	{
		// get the width and height of the grid.
		int width = grid.getGridWidth();
		int height = grid.getGridHeight();
		
		// initialize the nodes as an empty node matrix.
		nodes = new Node[width][height];
		
		// add the necessary nodes.
		for( int x=0; x<width; x++ )
		{
			for( int y=0; y<height; y++ )
			{
				nodes[x][y] = new Node(x, y);
			}
		}
	}
	
	/**
	 * Returns the node for the specified coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @return the node.
	 */
	public Node getNode(int x, int y)
	{
		return nodes[x][y];
	}
	
	/**
	 * Returns the path from the starting coordinates to the target coordinates.
	 * @param sx the starting x-coordinate.
	 * @param sy the starting y-coordinate.
	 * @param tx the target x-coordinate.
	 * @param ty the target y-coordinate.
	 * @param maxPathLength the maximum length allowed for the path.
	 * @param mover the entity for which to get the path.
	 * @return the path.
	 */
	public GridPath getPath(int sx, int sy, int tx, int ty, int maxPathLength, IMover mover)
	{
		// make sure destination is not blocked.
		if( !grid.isBlocked(tx, ty, mover) )
		{
			// clear the path finder before we start building the path.
			clear();
			
			Node start = getNode(sx, sy);
			Node target = getNode(tx, ty);
			int pathLength = 0;
			
			// add the starting node to the include list.
			include.add(start);
			
			// we may loop until we have reach the maximum path length
			// or the include list is empty.
			while( pathLength<maxPathLength && !include.isEmpty() )
			{
				// first node in the open list is the current node.
				Node current = include.get(0);
				
				// we have reached the target.
				if( current==target )
				{
					break;
				}
				
				// we need to remove the current node from the open list
				// before we start processing the neighboring nodes.
				include.remove(current);
				
				// add current node to the exclude list
				// as we do not want to go backwards.
				exclude.add(current);
				
				// loop through the neighboring nodes.
				for( int nx=(current.x-1); nx<(current.x+2); nx++ )
				{
					for( int ny=(current.y-1); ny<(current.y+2); ny++ )
					{
						// make sure we skip the current.
						if( nx!=current.x || ny!=current.y )
						{
							// make sure the node is not blocked.
							if( !grid.isBlocked(nx, ny, mover) )
							{
								// calculate the movement cost for the node.
								float movementCost = current.movementCost + grid.getMovementCost(nx, ny, mover);
								
								Node neighbor = getNode(nx, ny);
								
								// in case the movement cost is lower than the neighboring nodes
								// already calculated cost we need to remove the node from both the
								// include and exclude list in order to re-evaluate it.
								if( movementCost<neighbor.movementCost )
								{
									if( include.contains(neighbor) ) 
									{
										include.remove(neighbor);
									}

									if( exclude.contains(neighbor) )
									{
										exclude.remove(neighbor);
									}
								}
								
								// node is neither in the include or the exclude list
								// meaning that it is most likely the next step.
								if( !include.contains(neighbor) && !exclude.contains(neighbor) ) 
								{
									// set the neighbor movement cost.
									neighbor.movementCost = movementCost;
									
									// get the heuristic cost of the neighbor.
									neighbor.heuristicCost = heuristic.getCost(nx, ny, tx, ty, mover);
									
									// set the current to the parent of the neighbor.
									neighbor.setParent(current);
									
									// set the new path length.
									pathLength = Math.max(pathLength, neighbor.depth);
									
									// add the neighbor to the include list.
									include.add(neighbor);
									
									// sort the include list.
									Collections.sort(include);
								}
							}
						}
					}
				}
			}
			
			// make sure we reached the target node.
			if( target.parent!=null )
			{
				// create a new path.
				GridPath path = new GridPath();
				
				Node step = target;
				
				// loop through the path starting at the end.
				while( step!=start ) 
				{					
					// add the step to the beginning of the path.
					path.prependStep(step.x, step.y);
					
					// set parent as the next step.
					step = step.parent;
				}
				
				// we need to include the start point.
				path.prependStep(sx, sy);
				
				return path;
			}
		}
		
		// no path available.
		return null;
	}

	/**
	 * Private inner class representing a single node in the path finder.
	 */
	private class Node implements Comparable<Object> 
	{
		private int x;
		private int y;
		private float movementCost;
		private float heuristicCost;
		private int depth;
		private Node parent;
		
		/**
		 * Creates the node.
		 * @param x the x-coordinate.
		 * @param y the y-coordinate.
		 */
		public Node(int x, int y) 
		{
			this.x = x;
			this.y = y;
		}
		
		/**
		 * @param parent the parent node.
		 */
		public void setParent(Node parent) 
		{
			this.parent = parent;
			this.depth = parent.depth + 1;
		}

		/**
		 * Compares this node against another node
		 * based on their heuristic costs. 
		 */
		public int compareTo(Object o) 
		{
			Node other = (Node)o;
			
			float cost1 = heuristicCost + movementCost;
			float cost2 = other.heuristicCost + other.movementCost;
			
			if( cost1<cost2 ) 
			{
				return -1;
			} 
			else if( cost1>cost2 ) 
			{
				return 1;
			} 
			else 
			{
				return 0;
			}
		}
	}
}
