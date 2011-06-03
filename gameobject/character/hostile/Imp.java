package gridwhack.gameobject.character.hostile;

import gridwhack.core.ImageLoader;

public class Imp extends HostileCharacter
{
	public Imp()
	{
		super();

		setImage(ImageLoader.getInstance().loadImage("Imp1.png"));
		
		setName("Imp");
		setDamage(20, 20);
		setHealth(40);
		setAttackCooldown(1000);
		setMovementCooldown(1000);
		setViewRange(6);
		setExperience(60);
	}
}