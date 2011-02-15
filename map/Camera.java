package gridwhack.map;

import java.awt.Rectangle;

import gridwhack.entity.unit.event.IUnitListener;
import gridwhack.entity.unit.event.UnitEvent;
import gridwhack.grid.GridUnit;

public class Camera implements IUnitListener
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected GridUnit subject;
	protected Rectangle bounds;
	protected boolean follow;
	
	public Camera(int x, int y, int width, int height, Rectangle bounds, GridUnit subject, boolean follow)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bounds = bounds;
		this.subject = subject;
		this.follow = follow;
		
		focusOnSubject();
	}
	
	public void focusOnSubject()
	{
		int middleX = (int) Math.round( subject.getX()+(subject.getWidth() / 2) );
		int middleY = (int) Math.round( subject.getY()+(subject.getHeight() / 2) );
		
		int x = middleX - (width / 2);
		int y = middleY - (height / 2);
		
		setX(x);
		setY(y);
	}
	
	public void setX(int x)
	{
		if( x<bounds.x )
		{
			x = bounds.x;
		}
		else if( (x + width)>bounds.width )
		{
			x = bounds.width - width;
		}
				
		this.x = x;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setY(int y)
	{
		if( y<bounds.y )
		{
			y = bounds.y;
		}
		else if( (y + height)>bounds.height )
		{
			y = bounds.height - height;
		}
		
		this.y = y;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void move(int tx, int ty)
	{
		setX(tx);
		setY(ty);
	}

	public void onUnitDeath(UnitEvent e) {}

	public void onUnitSpawn(UnitEvent e) {}

	public void onUnitHealthGain(UnitEvent e) {}

	public void onUnitHealthLoss(UnitEvent e) {}

	public void onUnitMove(UnitEvent e)
	{
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
	}
}
