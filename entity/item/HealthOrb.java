package gridwhack.entity.item;

import gridwhack.entity.unit.Player;
import gridwhack.gui.message.MessageLog;

/**
 * Health orb item class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthOrb extends Item
{
	/**
	 * Creates the orb.
	 */
	public HealthOrb()
	{
		super("Health Orb", "healthorb.png");
	}

	/**
	 * Actions to be taken when looting this item.
	 * @param player the player receiving the loot.
	 */
	public void loot(Player player)
	{
		int healAmount = 30;
		MessageLog.addMessage("[" + getName() + "] heals " + player.getName() + " for " + healAmount + ".");
		player.increaseHealth(healAmount);

		super.loot(player);
	}
}
