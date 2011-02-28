package gridwhack.entity.character;

import gridwhack.entity.character.hostile.HostileCharacter;
import gridwhack.grid.Grid;

public class FriendlyCharacter extends NPCCharacter
{
	/**
	 * Creates the character.
	 * @param filename the image filename.
	 * @param grid the grid the hostile belongs to.
	 */
	public FriendlyCharacter(String filename, Grid grid)
	{
		super(filename, grid);
	}
	
	/**
	 * Returns whether the target character is hostile.
	 * @param target the target character.
	 */
	public boolean isHostile(Character target)
	{
		return target instanceof HostileCharacter;
	}

}
