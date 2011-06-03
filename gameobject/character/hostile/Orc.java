package gridwhack.gameobject.character.hostile;

import gridwhack.core.ImageLoader;

public class Orc extends HostileCharacter
{
	public Orc()
	{
		super();

		setImage(ImageLoader.getInstance().loadImage("orc.png"));

		setName("Orc");
		setDamage(50, 100);
		setHealth(100);
		setAttackCooldown(2000);
		setMovementCooldown(2000);
		setViewRange(5);
		setExperience(200);
	}
}