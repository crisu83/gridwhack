package gridwhack.entity.character.attack;

import gridwhack.entity.character.hostile.HostileCharacter;
import gridwhack.entity.character.player.Player;
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
			scenario.defender.setKilledBy(scenario.attacker);

			// TODO: Think of a better way to do this.
			if( scenario.attacker instanceof Player && scenario.defender instanceof HostileCharacter)
			{
				((Player) scenario.attacker).increaseExperience(((HostileCharacter) scenario.defender).getExperienceValue());
			}

			CombatLog.addMessage(scenario.defender.getName() + " is killed.");
		}
	}

	/**
	 * Actions to be taken when attacking.
	 */
	public abstract void attack();
}
