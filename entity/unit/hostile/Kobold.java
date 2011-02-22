package gridwhack.entity.unit.hostile;

import gridwhack.grid.Grid;

public class Kobold extends HostileUnit 
{
	public Kobold(Grid grid) 
	{
		super("kobold.png", grid);
		
		setName("Kobold");
		setDamage(30, 60);
		setHealth(60);
		setAttackCooldown(1000);
		setMovementCooldown(1000);
		setViewRange(8);
		setExperienceValue(120);

	}
}