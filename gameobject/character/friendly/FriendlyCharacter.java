package gridwhack.gameobject.character.friendly;

import gridwhack.gameobject.character.*;
import gridwhack.gameobject.character.Character;
import gridwhack.gameobject.character.hostile.HostileCharacter;
import gridwhack.gameobject.grid.Grid;

public class FriendlyCharacter extends NPCCharacter
{
	/**
	 * Creates the object.
	 */
	public FriendlyCharacter()
	{
		super();
	}
	
	/**
	 * Returns whether the target character is hostile.
	 * @param target The target character.
	 */
	public boolean isHostile(Character target)
	{
		return target instanceof HostileCharacter;
	}

}
