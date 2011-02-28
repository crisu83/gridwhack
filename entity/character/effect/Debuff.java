package gridwhack.entity.character.effect;

import gridwhack.entity.character.Character;

public abstract class Debuff extends CharacterEffect
{
	public Debuff(String name, CharacterEffect.Type type, int duration, Character subject)
	{
		super(name, type, duration, subject);
	}
}
