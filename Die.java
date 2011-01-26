package gridwhack;

import java.util.Random;

public class Die 
{
	private int sides;
	private Random rand;
	
	public Die(int sides, Random rand)
	{
		this.sides = sides;
		this.rand = rand;
	}
	
	public int roll()
	{
		return rand.nextInt(sides);
	}
	
	public int roll(int min)
	{		
		int roll = 0;
		
		while( roll<min )
		{
			roll = roll();
		}
		
		return roll;
	}
}