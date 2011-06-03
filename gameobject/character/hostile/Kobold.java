package gridwhack.gameobject.character.hostile;

import gridwhack.core.ImageLoader;

public class Kobold extends HostileCharacter
{
	public Kobold()
	{
		super();

		setImage(ImageLoader.getInstance().loadImage("kobold.png"));

		setName("Kobold");
		setDamage(30, 60);
		setHealth(60);
		setAttackCooldown(1000);
		setMovementCooldown(1000);
		setViewRange(8);
		setExperience(120);
	}
}