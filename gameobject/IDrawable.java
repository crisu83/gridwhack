package gridwhack.gameobject;

import java.awt.*;

/**
 * Drawable interface.
 * All objects that can be drawn should implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public interface IDrawable
{
	/**
	 * Draws the object.
	 * @param g The graphics context.
	 */
	public abstract void draw(Graphics2D g);
}
