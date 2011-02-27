package gridwhack.entity.character.effect;

import gridwhack.entity.character.Character;

public abstract class Buff extends CharacterEffect
{
	public Buff(String name, CharacterEffect.Type type, int duration, Character subject)
	{
		super(name, type, duration, subject);
	}
}
