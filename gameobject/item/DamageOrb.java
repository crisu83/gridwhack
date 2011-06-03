package gridwhack.gameobject.item;

import gridwhack.core.ImageLoader;
import gridwhack.exception.InvalidObjectException;
import gridwhack.gameobject.character.effect.CharacterEffect;
import gridwhack.gameobject.character.effect.CharacterEffectFactory;
import gridwhack.gameobject.character.player.Player;
import gridwhack.gui.message.MessageLog;

/**
 * Health orb item class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class DamageOrb extends Item
{
	/**
	 * Creates the orb.
	 */
	public DamageOrb()
	{
		super("Damage Orb");

		// TODO: Change image for this item.
		setImage(ImageLoader.getInstance().loadImage("healthorb.png"));

	}

	/**
	 * Actions to be taken when looting this item.
	 * @param player the player receiving the loot.
	 */
	public void loot(Player player)
	{
		CharacterEffect effect = null;

		try
		{
			effect = CharacterEffectFactory.factory(CharacterEffect.Type.DOUBLE_DAMAGE, player);
		}
		catch( InvalidObjectException e )
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}

		MessageLog.addMessage("[" + getName() + "] gives " + player.getName() + " double damage for " + effect.getDuration() + " seconds.");

		super.loot(player);
	}
}
