package gridwhack.gui.message;

import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;

/**
 * Message box class file.
 * Allows for rendering message streams in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class MessageBox extends GuiElement 
{
	protected int lineCount;

	/**
	 * Creates the message box.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the box width.
	 * @param height the box height.
	 * @param lineCount the amount of lines to display.
	 */
	public MessageBox(int x, int y, int width, int height, int lineCount)
	{
		super(x, y, width, height);

		this.lineCount = lineCount;
	}

	/**
	 * Updates this element.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed) {}

	/**
	 * Draws this object.
	 * @param g The graphics context.
	 */
	public void draw(Graphics2D g)
	{
		// set the font and bgColor.
		g.setFont(getFont());
		g.setColor(getTextColor());
		
		// get the messages from the combat log.
		ArrayList<String> messages = getMessages();
		
		// make sure that we have messages.
		if( !messages.isEmpty() )
		{		
			// render the latest messages.
			for( int i=0, length=messages.size(); i<length && i<lineCount; i++ )
			{
				g.drawString(messages.get(i), getX(),
						getY() + (int) Math.round(getFontSize() * 0.8) + (i*getLineHeight()));
			}
		}
	}
	
	/**
	 * Returns the messages in the stream.
	 * @return the messages.
	 */
	public abstract ArrayList<String> getMessages();
}
