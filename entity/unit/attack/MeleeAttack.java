package gridwhack.entity.unit.attack;

import gridwhack.Die;
import gridwhack.gui.message.CombatLog;

public class MeleeAttack extends Attack
{
	public MeleeAttack(AttackScenario scenario) 
	{
		super(scenario);	
	}

	public void attack()
	{
		Die d100 = new Die(100, scenario.rand);
		
		int hitRoll = d100.roll();
		
		int damage = scenario.attacker.getDamage();
		
		// critical hit.
		if( hitRoll>=75 )
		{
			damage = damage * scenario.attacker.getCritialMultiplier();
			CombatLog.addMessage(scenario.attacker.getName() + " critically hit " + scenario.defender.getName() + " for " + damage + " points of damage.");
			dealDamage(damage);
		}
		// normal hit.
		else if( hitRoll>=25 )
		{
			CombatLog.addMessage(scenario.attacker.getName() + " hit " + scenario.defender.getName() + " for " + damage + " points of damage.");
			dealDamage(damage);
		}
		// miss.
		else
		{
			CombatLog.addMessage(scenario.attacker.getName() + " missed " + scenario.defender.getName() + ".");
			// do nothing.
		}
	}
}
