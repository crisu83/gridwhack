package gridwhack.entity.character.hostile;

import gridwhack.entity.character.Character;
import gridwhack.entity.character.FriendlyCharacter;
import gridwhack.entity.character.NPCCharacter;
import gridwhack.entity.character.player.Player;
import gridwhack.grid.Grid;

/**
 * Hostile character class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class HostileCharacter extends NPCCharacter
{
	private int experienceValue;

	/**
	 * Creates the character.
	 * @param filename the image filename.
	 * @param grid the grid the hostile belongs to.
	 */
	public HostileCharacter(String filename, Grid grid)
	{
		super(filename, grid);
	}

	public void setExperienceValue(int value)
	{
		this.experienceValue = value;
	}

	public int getExperienceValue()
	{
		return experienceValue;
	}

	/**
	 * Returns whether the target character is hostile.
	 * @param target the target character.
	 * @return whether the target is hostile.
	 */
	public boolean isHostile(Character target)
	{
		// players and friendly units are hostile towards hostile units.
		return target instanceof Player || target instanceof FriendlyCharacter;
	}
}