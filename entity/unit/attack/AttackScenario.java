package gridwhack.entity.unit.attack;

import java.util.Random;

import gridwhack.entity.unit.Unit;

public class AttackScenario
{
	protected Unit attacker;
	protected Unit defender;
	protected Random rand;
	
	public AttackScenario(Unit attacker, Unit defender, Random rand)
	{
		this.attacker = attacker;
		this.defender = defender;
		this.rand = rand;
	}
	
	public void attack()
	{
		// TODO: Support other types of attacks than melee.
		new MeleeAttack(this).attack();
	}
}
