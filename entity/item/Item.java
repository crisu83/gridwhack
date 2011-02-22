package gridwhack.entity.item;

import gridwhack.entity.CEntity;
import gridwhack.entity.unit.player.Player;
import gridwhack.gui.message.MessageLog;

/**
 * Item class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Item extends CEntity implements Lootable
{
	// Item types.
	public static enum Type {
		HEALTH_ORB,
	}

	protected String name;

	/**
	 * Creates the item.
	 * @param name the name of the item.
	 */
	public Item(String name, String filename)
	{
		super(filename);
		
		this.name = name;
	}

	/**
	 * Initializes the item.
	 */
	public void init() {}

	/**
	 * Sets the name for this item.
	 * @param name the name.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the name of this item.
	 * @return the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Actions to be taken when looting this item.
	 * @param player the player receiving the loot.
	 */
	public void loot(Player player)
	{
		MessageLog.addMessage("You looted [" + getName() + "].");
	}
}
