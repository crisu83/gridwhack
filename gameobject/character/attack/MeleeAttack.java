package gridwhack.gameobject.character.attack;

import gridwhack.Die;
import gridwhack.gui.message.CombatLog;

/**
 * Melee attack class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class MeleeAttack extends Attack
{
	/**
	 * Creates the attack.
	 * @param scenario the combat scenario.
	 */
	public MeleeAttack(BattleScenario scenario)
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

		Die damageDie = new Die(scenario.attacker.getMaximumDamage() + 1, scenario.rand);
		int damage = damageDie.roll(scenario.attacker.getMinimumDamage());
		
		// critical hit.
		if( hitRoll>=75 )
		{
			damage = damage * scenario.attacker.getCritialMultiplier();
			CombatLog.addMessage( scenario.attacker.getName() + " critically hits " + scenario.defender.getName() + " for " + damage + "!");
			dealDamage(damage);
		}
		// normal hit.
		else if( hitRoll>=25 )
		{
			CombatLog.addMessage(scenario.attacker.getName() + " hits " + scenario.defender.getName() + " for " + damage + ".");
			dealDamage(damage);
		}
		// miss.
		else
		{
			CombatLog.addMessage(scenario.attacker.getName() + " misses " + scenario.defender.getName() + ".");
			// do nothing.
		}
	}
}
