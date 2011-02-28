package gridwhack.entity.character.effect;

import gridwhack.entity.character.Character;

public class DoubleDamage extends Buff
{
	private int minimum;
	private int maximum;

	public DoubleDamage(Character subject)
	{
		super("Double damage", CharacterEffect.Type.DOUBLE_DAMAGE, 10, subject);

		minimum = subject.getMinimumDamage();
		maximum = subject.getMaximumDamage();
	}

	public void affect()
	{
		getSubject().setDamage(minimum * 2, maximum * 2);
	}

	public void fade()
	{
		getSubject().setDamage(minimum, maximum);
	}
}
