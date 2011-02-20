package gridwhack.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import gridwhack.grid.Grid;
import gridwhack.grid.GridTile;

public class DungeonSection
{
	protected enum SplitType { HORIZONTAL, VERTICAL };
	
	protected static final int MIN_SECTION_SIZE = 5;
	
	protected SplitType splitType;
	protected int splitCount;
	protected int depth;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Random rand;
	protected Grid grid;
	protected Room room;
	
	protected ArrayList<DungeonSection> sections;
	
	/**
	 * Constructs the dungeon section.
	 * @param splitCount the time to split the dungeon.
	 * @param depth the current split depth.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the width of the section in grid cells.
	 * @param height the height of the section in grid cells.
	 * @param rand the random generator to use.
	 * @param grid the grid.
	 * @param splitType how to split the section.
	 */
	DungeonSection(int splitCount, int depth, int x, int y, int width, int height, Random rand, Grid grid, SplitType splitType)
	{
		this.splitCount = splitCount;
		this.depth = depth;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rand = rand;
		this.grid = grid;
		
		sections = new ArrayList<DungeonSection>();
		
		// randomize the split type if this is the first split.
		if( splitType==null )
		{
			// randomize the split type.
			int t = rand.nextInt(SplitType.values().length);
			this.splitType = SplitType.values()[t];
		}
		else
		{
			this.splitType = splitType;
		}
		
		// split the section if necessary.
		if( depth<splitCount )
		{	
			split();
		}
	}
	
	/**
	 * Constructs the dungeon section.
	 * @param width the width of the section in grid cells.
	 * @param height the height of the section in grid cells.
	 * @param rand the random generator to use.
	 * @param grid the grid.
	 */
	DungeonSection(int width, int height, Random rand, Grid grid)
	{
		this(2, 0, 0, 0, width, height, rand, grid, null);
	}
	
	/**
	 * Splits the section into two smaller sections.
	 * Size of those sections are randomized "a bit".
	 */
	protected void split()
	{		
		// calculate the minimum section size.
		int minSectionSize = MIN_SECTION_SIZE * 2;
		
		// make sure the section is large enough to split.
		if( this.width>minSectionSize || this.height>minSectionSize )
		{		
			// set initial values.
			int x = this.x;
			int y = this.y;
			int width = this.width;
			int height = this.height;
					
			// each should only be split once.
			for( int i=0; i<2; i++ )
			{
				if( this.splitType!=null )
				{
					// first section.
					if( i==0 )
					{
						int operand = rand.nextInt(2);
						
						// we need to split the section vertically.
						if( this.splitType==SplitType.VERTICAL )
						{
							int offset = rand.nextInt(width/4);
							width = operand>0 ? (width/2) + offset : (width/2) - offset;
							
							// make sure the section is wide enough.
							if( width<MIN_SECTION_SIZE )
							{
								width = MIN_SECTION_SIZE;
							}
							// make sure the section is not too wide.
							else if( (width + MIN_SECTION_SIZE)>this.width )
							{
								width = this.width - MIN_SECTION_SIZE;
							}
						}
						// we need to split the section horizontally.
						else
						{
							int offset = rand.nextInt(height/4);
							height = operand>0 ? (height/2) + offset : (height/2) - offset;
							
							// make sure the section is high enough.
							if( height<MIN_SECTION_SIZE )
							{
								height = MIN_SECTION_SIZE;
							}
							// make sure the section is not too high.
							else if( (height + MIN_SECTION_SIZE)>this.height )
							{
								height = this.height - MIN_SECTION_SIZE;
							}
						}
					}
					// second section.
					else
					{
						// we need to split the section vertically.
						if( this.splitType==SplitType.VERTICAL )
						{
							x = this.x + width;
							width = this.width - width;
						}
						// we need to split the section horizontally.
						else
						{
							y = this.y + height;
							height = this.height - height;
						}
					}
				
					// change the split type to the opposite.
					SplitType splitType = this.splitType==SplitType.VERTICAL ? SplitType.HORIZONTAL : SplitType.VERTICAL;
					
					// add the new section.
					addSection(new DungeonSection(splitCount, depth+1, x, y, width, height, rand, grid, splitType));
				}
			}
		}
	}
	
	/**
	 * @return the list of sections.
	 */
	protected ArrayList<DungeonSection> getSections()
	{
		return sections;
	}
	
	/**
	 * Adds a sub-section to the section.
	 * @param section the sub-section to add.
	 */
	protected void addSection(DungeonSection section)
	{
		sections.add(section);
	}
	
	/**
	 * Returns the rooms in the top-sub-sections.
	 * @return the rooms.
	 */
	protected ArrayList<Room> getRooms()
	{
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		// make sure there are sub-sections.
		if( !sections.isEmpty() )
		{			
			// loop through the sub-sections.
			for( DungeonSection ds : sections )
			{
				// check if the sub-section has a room.
				if( ds.room!=null )
				{
					rooms.add(ds.room);
				}
				else
				{
					ds.getRooms();
				}
			}
		}
		
		return rooms;
	}
		
	/**
	 * Adds rooms to the top-sections.
	 */
	protected void addRooms()
	{
		// check if this section has sub-sections.
		if( !sections.isEmpty() )
		{
			// loop through the sub-sections
			// and add rooms to the sub-sections.
			for( DungeonSection ds : sections )
			{				
				ds.addRooms();
			}
		}
		// section has no sub-sections.
		else
		{
			// create a room in this section.
			setRoom(new Room(x+1, y+1, width-2, height-2, grid));
		}
	}
	
	/**
	 * Sets the room in this section.
	 * @param room the room.
	 */
	protected void setRoom(Room room)
	{
		this.room = room;
	}
	
	protected void addCorridors()
	{
		if( !sections.isEmpty() )
		{
			for( DungeonSection ds : sections )
			{
				if( ds.sections.isEmpty() && ds.splitType!=null )
				{
					DungeonSection s1 = sections.get(0);
					DungeonSection s2 = sections.get(1);
					
					Room r1 = s1.room;
					Room r2 = s2.room;
					
					int x = 0;
					int y = 0;
					int height = 0;
					int width = 0;
					
					if( ds.splitType==SplitType.VERTICAL )
					{
						x = r1.x + r1.width;
						y = r1.y + (r1.height / 2);
						width = r2.x - x;
						height = 1;
					}
					else
					{
						x = r1.x + (r1.width / 2);
						y = r1.y + r1.height;
						height = r2.y - y;
						width = 1;
					}
					
					System.out.println("x: " + x + ", y: " + y + ", w: " + width + ", h: " + height);
					
					grid.createTileRect(x, y, width, height, GridTile.Type.WALL);
				}
				
				ds.addCorridors();
			}
		}
	}
	
	/*
	protected void addCorridors()
	{
		if( !sections.isEmpty() )
		{
			if( sections.size()==2 )
			{
				DungeonSection s1 = sections.get(0);
				DungeonSection s2 = sections.get(1);
				
				ArrayList<Room> rooms = getNeighboringRooms(s1, s2);
				
				if( !rooms.isEmpty() )
				{
					Room r1 = rooms.get(0);
					Room r2 = rooms.get(1);
					
					int x;
					int y;
					int height;
					int width;
					
					if( splitType==SplitType.VERTICAL )
					{
						x = s1.x + r1.width + 1;
						y = r1.height / 2 + 2;
						width = r2.x - x;
						height = 1;
					}
					else
					{
						x = r1.width / 2 + 2;
						y = s1.y + r1.height + 1;
						width = 1;
						height = r2.y - y;
					}
					
					grid.createTileRect(x, y, width, height, TileFactory.TileType.WALL);
				}
			}
			
			for( DungeonSection ds : sections )
			{
				ds.addCorridors();
			}
		}
	}
	
	protected ArrayList<Room> getNeighboringRooms(DungeonSection s1, DungeonSection s2)
	{		
		ArrayList<DungeonSection> ss1 = s1.getSections();
	
		Room r1 = s1.room;
		
		if( !ss1.isEmpty() )
		{
			for( DungeonSection ds : ss1 )
			{
				Room current = ds.room;
				
				if( current!=null )
				{
					if( current.x>r1.x || current.y>r1.y )
					{
						r1 = current; 
					}
				}
			}
		}
		
		ArrayList<DungeonSection> ss2 = s2.getSections();
		
		Room r2 = s2.room;
		
		int cdx = Math.abs(r2.x - r1.x);
		int cdy = Math.abs(r2.y - r1.y);
		
		if( !ss2.isEmpty() )
		{
			for( DungeonSection ds : ss2 )
			{
				Room current = ds.room;
				
				if( current!=null )
				{
					int dx = Math.abs(current.x - r1.x);
					int dy = Math.abs(current.y - r1.y);
					
					if( dx<cdx || dy<cdy )
					{
						cdx = dx;
						cdy = dy;
						r2 = current;
					}
				}
			}
		}
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		if( r1!=null && r2!=null )
		{
			rooms.add(r1);
			rooms.add(r2);
		}
		
		return rooms;
	}
	*/

	/**
	 * Renders the dungeon section.
	 * Used only for debugging.
	 * @param g the 2D graphics object.
	 * @param colors the colors to use.
	 */
	public void render(Graphics2D g, ArrayList<Color> colors) 
	{	
		g.setColor(colors.get(depth));
		g.drawRect(x*32, y*32, width*32, height*32);
		
		if( !sections.isEmpty() )
		{			
			for( DungeonSection ds : sections )
			{
				ds.render(g, colors);
			}
		}
	}
}
