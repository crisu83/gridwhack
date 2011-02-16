package gridwhack.gui.message;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.gui.GuiElement;

/**
 * Message box class file.
 * Allows for rendering message streams in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class MessageBox extends GuiElement 
{
	protected Font font;
	protected Color textColor;
	protected int lineCount;
	protected int lineHeight;

	/**
	 * Creates the message box.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the box width.
	 * @param height the box height.
	 * @param font the font.
	 * @param textColor the text bgColor.
	 * @param lineCount the amount of lines to display.
	 */
	public MessageBox(int x, int y, int width, int height, Font font, Color textColor, int lineCount)
	{
		super(x, y, width, height);

		this.font = font;
		this.textColor = textColor;
		this.lineCount = lineCount;
		this.lineHeight = (int) Math.round(font.getSize() * 1.5);
	}

	/**
	 * Renders the message box.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		// set the font and bgColor.
		g.setFont(font);
		g.setColor(textColor);
		
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
	 * Returns the messages in the stream.
	 * @return the messages.
	 */
	public abstract ArrayList<String> getMessages();
}
