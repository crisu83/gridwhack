package gridwhack.entity.unit.attack;

import gridwhack.gui.message.CombatLog;

/**
 * Attack class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Attack 
{
	protected BattleScenario scenario;

	/**
	 * Creates the engage.
	 * @param scenario the combat scenario.
	 */
	public Attack(BattleScenario scenario)
	{
		this.scenario = scenario;
	}

	/**
	 * Deals damage to the defender.
	 * @param damage the amount of damage to deal.
	 */
	protected void dealDamage(int damage)
	{
		// Reduce the defender health.
		scenario.defender.reduceHealth(damage);

		// Check if the defender died.
		if( scenario.defender.getDead() )
		{
			CombatLog.addMessage(scenario.defender.getName() + " is killed.");	
			scenario.defender.setKilledBy(scenario.attacker);
		}
	}

	/**
	 * Actions to be taken when attacking.
	 */
	public abstract void attack();
}
