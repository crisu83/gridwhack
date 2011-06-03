package gridwhack.gameobject.character.effect;

import gridwhack.gameobject.character.Character;

import java.awt.*;

/**
 * Double damage class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class DoubleDamage extends Buff
{
	private int minimum;
	private int maximum;

	/**
	 * Creates the character effect.
	 * @param subject the subject to apply the effect to.
	 */
	public DoubleDamage(Character subject)
	{
		super("Double damage", CharacterEffect.Type.DOUBLE_DAMAGE, 10000, subject);

		minimum = subject.getMinimumDamage();
		maximum = subject.getMaximumDamage();
	}

	/**
	 * Actions to take when the character effect is applied.
	 */
	public void apply()
	{
		getSubject().setDamage(minimum * 2, maximum * 2);
	}

	/**
	 * Actions to take when the character effect ticks.
	 */
	public void tick() {}

	/**
	 * Actions to take when the character effect faces.
	 */
	public void fade()
	{
		getSubject().setDamage(minimum, maximum);
	}

	/**
	 * Renders the object.
	 * @param g The graphics context.
	 */
	public void draw(Graphics2D g) {}
}
