package gridwhack.map;

import java.awt.Rectangle;

import gridwhack.entity.unit.Unit;
import gridwhack.entity.unit.event.IUnitListener;
import gridwhack.entity.unit.event.UnitEvent;

public class Camera implements IUnitListener
{
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected Unit subject;
	protected Rectangle bounds;
	protected boolean follow;
	
	public Camera(int x, int y, int width, int height, Rectangle bounds, Unit subject, boolean follow)
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
		setX( subject.getX() - (width/2) );
		setY( subject.getY() - (height/2) );
	}
	
	public void setX(double x)
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
	
	public double getX()
	{
		return x;
	}
	
	public void setY(double y)
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
	
	public double getY()
	{
		return y;
	}
	
	public void move(int tx, int ty)
	{
		setX(tx);
		setY(ty);
	}

	@Override
	public void onUnitDeath(UnitEvent e) {}

	@Override
	public void onUnitSpawn(UnitEvent e) {}

	@Override
	public void onUnitHealthGain(UnitEvent e) {}

	@Override
	public void onUnitHealthLoss(UnitEvent e) {}

	@Override
	public void onUnitMove(UnitEvent e) 
	{
		if( follow )
		{
			int subjectX = (int) Math.round(subject.getX());
			int subjectY = (int) Math.round(subject.getY());
			
			//System.out.println("width:"+width+", height:"+height+", subject x:"+subjectX+", subject y:"+subjectY);	
			
			if( subjectX<x )
			{
				move((int) (x-width), (int) y);
				//System.out.println("moving camera: x:"+((int) (x-width))+", y:"+((int) y));
			}
			else if( subjectX>=width )
			{
				move((int) (x+width), (int) y);
				//System.out.println("moving camera: x:"+((int) (x+width))+", y:"+((int) y));
			}
			
			if( subjectY<y )
			{
				move((int) x, (int) (y-height));
				//System.out.println("moving camera: x:"+((int) x)+", y:"+((int) (y-height)));
			}
			else if( subjectY>=height )
			{
				move((int) x, (int) (y+height));
				//System.out.println("moving camera: x:"+((int) x)+", y:"+((int) (y+height)));
			}
		}
	}
}
