package gridwhack.map;

import java.awt.Rectangle;

import gridwhack.CEvent;
import gridwhack.IEventListener;
import gridwhack.entity.CEntity;
import gridwhack.entity.unit.Player;

public class Camera implements IEventListener
{
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected CEntity focus;
	protected Rectangle bounds;
	
	public Camera(int width, int height, Rectangle bounds, CEntity focus)
	{
		this.width = width;
		this.height = height;
		this.bounds = bounds;
		this.focus = focus;
		
		focus();
	}
	
	public void focus()
	{
		setX(focus.getX() - (width/2));
		setY(focus.getY() - (height/2));
	}
	
	public double getX()
	{
		return x;
	}
	
	public void setX(double x)
	{
		// make sure we do not cross the bounds.
		if( x<bounds.x )
		{
			x = bounds.x;
		}
		else if( (x + (width))>bounds.width )
		{
			x = bounds.width - width;
		}
				
		this.x = x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setY(double y)
	{
		// make sure we do not cross the bounds.
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

	/*
	public void update(long timePassed)
	{
		int dx = 0;
		int dy = 0;
		int fx0 = (int)Math.round(focus.getX())-(width/2);
		int fy0 = (int)Math.round(focus.getY())-(height/2);
		
		if( x<fx0 )
		{
			dx = 32;
		}
		else if( x>fx0 )
		{
			dx = -32;
		}
		else if( y<fy0 )
		{
			dy = 32;
		}
		else if( y>fy0 )
		{
			dy = -32;
		}
		
		System.out.println("dx:" + dx + ", dy:" + dy);

		if( dx>0 || dy>0 )
		{
			x += dx * timePassed / 50;
			y += dy * timePassed / 50;
			
			setX(x);
			setY(y);
		}
	}
	*/
	
	public void handleEvent(CEvent e) 
	{
		String type = e.getType();
		Object source = e.getSource();
		
		if( source instanceof Player )
		{
			Player player = (Player)source;
			
			if( type=="move" )
			{
				setX(player.getX()-(width/2));
				setY(player.getY()-(height/2));
			}
		}
	}
}
