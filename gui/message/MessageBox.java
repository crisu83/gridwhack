package gridwhack.gui.message;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.gui.GuiElement;

/**
 * Base message box class.
 * Provides functionality for rendering message streams in the gui.
 */
public abstract class MessageBox extends GuiElement 
{	
	protected int lineCount;
	protected int fontSize;
	protected int lineHeight;
	
	/**
	 * Constructs the message box.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the box width.
	 * @param height the box height.
	 * @param lineCount the amount of lines to display.
	 * @param fontSize the font size.
	 */
	public MessageBox(int x, int y, int width, int height, int lineCount, int fontSize) 
	{
		super(x, y, width, height);
		
		this.lineCount = lineCount;
		this.fontSize = fontSize;
		this.lineHeight = (int)Math.round(fontSize * 1.5);
	}

	/**
	 * Renders the message box.
	 * @param g the 2D graphics object.
	 */
	public void render(Graphics2D g)
	{
		// set the font and color.
		g.setFont(new Font("Arial", Font.PLAIN, fontSize));
		g.setColor(Color.white);
		
		// get the messages from the combat log.
		ArrayList<String> messages = getMessages();
		
		// make sure that we have messages.
		if( !messages.isEmpty() )
		{		
			// render the latest messages.
			for( int i=0, length=messages.size(); i<length && i<lineCount; i++ )
			{
				g.drawString(messages.get(i), this.getX(), this.getY()+(i*lineHeight));
			}
		}
	}
	
	/**
	 * Returns a list of the newest messages.
	 */
	public abstract ArrayList<String> getMessages();
}
