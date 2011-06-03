package gridwhack.gameobject.item;

import gridwhack.gameobject.character.player.Player;

/**
 * Lootable interface class file.
 * All items that can be looted must implement this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface Lootable
{
	/**
	 * Actions to be taken when looting this item.
	 */
	public void loot(Player player);
}
