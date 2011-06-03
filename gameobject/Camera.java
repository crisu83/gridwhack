package gridwhack.gameobject;

import java.awt.Rectangle;

import gridwhack.gameobject.character.event.CharacterEvent;
import gridwhack.gameobject.character.event.ICharacterDeathListener;
import gridwhack.gameobject.character.event.ICharacterMoveListener;
import gridwhack.gameobject.character.event.ICharacterSpawnListener;
import gridwhack.gameobject.unit.Unit;
import gridwhack.util.Vector2;

public class Camera extends GameObject
		implements ICharacterDeathListener, ICharacterMoveListener, ICharacterSpawnListener
{
	protected int width;
	protected int height;
	protected Unit subject;
	protected Rectangle bounds;
	protected boolean follow;
	
	public Camera(int x, int y, int width, int height, Rectangle bounds, Unit subject, boolean follow)
	{
		this.width = width;
		this.height = height;
		this.bounds = bounds;
		this.subject = subject;
		this.follow = follow;

		setPosition(new Vector2(x, y));
		focusOnSubject();
	}
	
	public void focusOnSubject()
	{
		Vector2 position = subject.getPosition();
		Vector2 center = new Vector2(subject.getWidth() / 2, subject.getHeight() / 2);
		position.add(center);

		Vector2 newPosition = new Vector2(width / 2, height / 2);
		newPosition.substract(center);

		setPosition(newPosition);
	}

	public void setPosition(Vector2 position)
	{
		if (position.x < bounds.x)
		{
			position.x = bounds.x;
		}
		else if ((position.x + width) > bounds.width)
		{
			position.x = bounds.width - width;
		}

		if (position.y < bounds.y)
		{
			position.y = bounds.y;
		}
		else if ((position.y + height) > bounds.height)
		{
			position.y = bounds.height - height;
		}
	}
	
	public void move(Vector2 position)
	{
		setPosition(position);
	}

	public void onCharacterDeath(CharacterEvent e) {}

	public void onCharacterSpawn(CharacterEvent e) {}

	public void onCharacterMove(CharacterEvent e)
	{
		/*
		if( follow )
		{
			//focusOnSubject();
			int subjectX = (int) Math.round(subject.getX());
			int subjectY = (int) Math.round(subject.getY());
			
			//System.out.println("width:"+width+", height:"+height+", subject x:"+subjectX+", subject y:"+subjectY);	
			
			if( subjectX<x )
			{
				move(x - width, y);
				//System.out.println("moving camera: x:"+((int) (x-width))+", y:"+((int) y));
			}
			else if( (x + subjectX)>=width )
			{
				move(x + width, y);
				//System.out.println("moving camera: x:"+((int) (x+width))+", y:"+((int) y));
			}
			
			if( subjectY<y )
			{
				move(x, y - height);
				//System.out.println("moving camera: x:"+((int) x)+", y:"+((int) (y-height)));
			}
			else if( (y + subjectY)>=height )
			{
				move(x, y + height);
				//System.out.println("moving camera: x:"+((int) x)+", y:"+((int) (y+height)));
			}
		}
		*/
	}
}
