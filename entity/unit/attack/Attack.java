package gridwhack.entity.unit.attack;

import gridwhack.gui.message.CombatLog;

public abstract class Attack 
{
	protected AttackScenario scenario;
	
	public Attack(AttackScenario scenario) 
	{
		this.scenario = scenario;
	}
	
	protected void dealDamage(int damage)
	{
		scenario.defender.reduceHealth(damage);
		
		if( scenario.defender.getDead() )
		{
			CombatLog.addMessage(scenario.defender.getName() + " is killed.");	
			scenario.defender.setKilledBy(scenario.attacker);
		}
	}
	
	public abstract void attack();
}
