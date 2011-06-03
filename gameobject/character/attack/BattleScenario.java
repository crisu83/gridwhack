package gridwhack.gameobject.character.attack;

import java.util.Random;

import gridwhack.RandomProvider;
import gridwhack.gameobject.character.Character;

/**
 * Battle scenario class.
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
	 */
	public BattleScenario(Character attacker, Character defender)
	{
		this.attacker = attacker;
		this.defender = defender;

		rand = RandomProvider.getRand();
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
