package gridwhack.entity.item;

import gridwhack.entity.unit.Player;
import gridwhack.grid.Grid;

public class HealthOrb extends Item
{
	public HealthOrb() 
	{
		super();
	}
	
	public void pickedUp(Player player)
	{
		player.increaseHealth(10);
	}
}
