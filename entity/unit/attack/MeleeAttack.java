package gridwhack.entity.unit.attack;

import gridwhack.Die;
import gridwhack.gui.message.CombatLog;

/**
 * Melee attack class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class MeleeAttack extends Attack
{
	/**
	 * Creates the attack.
	 * @param scenario the combat scenario.
	 */
	public MeleeAttack(CombatScenario scenario)
	{
		super(scenario);	
	}

	/**
	 * Actions to be taken when attacking.
	 */
	public void attack()
	{
		Die d100 = new Die(100, scenario.rand);
		int hitRoll = d100.roll();

		Die damageDie = new Die(scenario.attacker.getMaximumDamage(), scenario.rand);
		int damage = damageDie.roll(scenario.attacker.getMinimumDamage());
		
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
