package gridwhack.entity.character.attack;

import java.util.Random;

import gridwhack.entity.character.Character;

/**
 * Battle scenario class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class BattleScenario
{
	protected Character attacker;
	protected Character defender;
	protected Random rand;

	/**
	 * Creates the combat scenario.
	 * @param attacker the attacking character.
	 * @param defender the defending character.
	 * @param rand the random context to be used.
	 */
	public BattleScenario(Character attacker, Character defender, Random rand)
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
