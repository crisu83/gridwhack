package gridwhack.gameobject.character.effect;

import gridwhack.gameobject.character.Character;

public abstract class Debuff extends CharacterEffect
{
	public Debuff(String name, CharacterEffect.Type type, int duration, Character subject)
	{
		super(name, type, duration, subject);
	}
}
