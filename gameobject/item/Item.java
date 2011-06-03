package gridwhack.gameobject.item;

import gridwhack.gameobject.GameObject;
import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.character.player.Player;
import gridwhack.gameobject.sprite.Sprite;
import gridwhack.gui.message.MessageLog;

/**
 * Item class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Item extends Sprite implements Lootable
{
	public static enum ItemType implements IGameObjectType
	{
		DAMAGE_ORB,
		HEALTH_ORB,
	}

	// ----------
	// Properties
	// ----------

	protected String name;

	// -------
	// Methods
	// -------
	
	/**
	 * Creates the item.
	 * @param name the name of the item.
	 */
	public Item(String name)
	{
		super();
		
		this.name = name;
	}

	/**
	 * Actions to be taken when looting this item.
	 * @param player the player receiving the loot.
	 */
	public void loot(Player player)
	{
		MessageLog.addMessage("You looted [" + getName() + "].");
	}

	// -------------------
	// Getters and Setters
	// -------------------

	/**
	 * Returns the name of this item.
	 * @return the name.
	 */
	public String getName()
	{
		return name;
	}
}
