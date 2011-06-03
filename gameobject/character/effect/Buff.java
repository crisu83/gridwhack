package gridwhack.gameobject.character.effect;

import gridwhack.gameobject.character.Character;

public abstract class Buff extends CharacterEffect
{
	public Buff(String name, CharacterEffect.Type type, int duration, Character subject)
	{
		super(name, type, duration, subject);
	}
}
