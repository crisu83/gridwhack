package gridwhack.gameobject.character.hostile;

import gridwhack.core.ImageLoader;

public class Skeleton extends HostileCharacter
{
	public Skeleton()
	{
		super();

		setImage(ImageLoader.getInstance().loadImage("Skeleton1.png"));
		
		setName("Skeleton");
		setDamage(80, 160);
		setHealth(160);
		setAttackCooldown(3000);
		setMovementCooldown(3000);
		setViewRange(3);
		setExperience(320);
	}
}