package gridwhack.gameobject.character.hostile;

import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.character.Character;
import gridwhack.gameobject.character.friendly.FriendlyCharacter;
import gridwhack.gameobject.character.NPCCharacter;
import gridwhack.gameobject.character.player.Player;

/**
 * Hostile character.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class HostileCharacter extends NPCCharacter
{
	public static enum HostileType implements IGameObjectType
	{
		IMP,
		SKELETON,
	}

	// ----------
	// Properties
	// ----------

	private int experience;

	// -------
	// Methods
	// -------

	/**
	 * Creates the character.
	 */
	public HostileCharacter()
	{
		super();
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Returns whether the target character is hostile.
	 * @param target The target character.
	 * @return whether The target is hostile.
	 */
	public boolean isHostile(Character target)
	{
		// Players and friendly units are hostile towards hostile units.
		return target instanceof Player || target instanceof FriendlyCharacter;
	}

	// -------------------
	// Getters and setters
	// -------------------

	public void setExperience(int value)
	{
		this.experience = value;
	}

	public int getExperience()
	{
		return experience;
	}
}