package gridwhack.entity.unit.attack;

import java.util.Random;

import gridwhack.grid.GridUnit;

/**
 * Attack scenario class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CombatScenario
{
	protected GridUnit attacker;
	protected GridUnit defender;
	protected Random rand;

	/**
	 * Creates the combat scenario.
	 * @param attacker the attacking unit.
	 * @param defender the defending unit.
	 * @param rand the random context to be used.
	 */
	public CombatScenario(GridUnit attacker, GridUnit defender, Random rand)
	{
		this.attacker = attacker;
		this.defender = defender;
		this.rand = rand;
	}

	/**
	 * Actions to be taken when engaging.
	 */
	public void engage()
	{
		// TODO: Support other types of attacks than melee.
		new MeleeAttack(this).attack();
	}
}
