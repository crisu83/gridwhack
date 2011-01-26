package gridwhack;

import java.awt.*;
import javax.swing.ImageIcon;

public class CResourceManager 
{
	/**
	 * Single instance of this class.
	 */
	private static CResourceManager instance = new CResourceManager();
	
	/**
	 * Private constructor.
	 */
	private CResourceManager() {}
	
	/**
	 * @return the single instance of this class.
	 */
	public static CResourceManager get()
	{
		return instance;
	}
	
	/**
	 * Returns the image with the specified filename from the resources folder.
	 * @param filename the name of the image file.
	 * @return the image.
	 */
	public Image getImage(String filename)
	{
		ImageIcon icon = new ImageIcon(this.getClass().getResource("resources\\" + filename));
		return icon.getImage();
	}
}
