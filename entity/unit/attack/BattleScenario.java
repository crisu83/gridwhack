package gridwhack.entity.unit.attack;

import java.util.Random;

import gridwhack.grid.GridUnit;

/**
 * Battle scenario class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class BattleScenario
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
	public BattleScenario(GridUnit attacker, GridUnit defender, Random rand)
	{
		this.attacker = attacker;
		this.defender = defender;
		this.rand = rand;
	}

	/**
	 * Starts the battle scenario.
	 */
	public void start()
	{
		// TODO: Support other types of attacks than melee.
		new MeleeAttack(this).attack();
	}
}
