package gridwhack.entity.character.hostile;

import gridwhack.grid.Grid;

public class Skeleton extends HostileCharacter
{
	public Skeleton(Grid grid) 
	{
		super("skeleton.png", grid);
		
		setName("Skeleton");
		setDamage(80, 160);
		setHealth(160);
		setAttackCooldown(3000);
		setMovementCooldown(3000);
		setViewRange(3);
		setExperienceValue(320);
	}
}