package gridwhack.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Image loader class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ImageLoader
{
	// ----------
	// Properties
	// ----------

	private static final String IMAGE_DIR = "..\\images\\";

	private static ImageLoader instance = new ImageLoader();

	private GraphicsConfiguration gc;

	// -------
	// Methods
	// -------

	/**
	 * Create the object.
	 * Private to enforce the singleton patter.
	 */
	private ImageLoader()
	{
		// Get the graphics configuration.
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

	}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static ImageLoader getInstance()
	{
		return instance;
	}

	/**
	 * Returns a specific image from the resources.
	 * @param filename The name of the image file.
	 * @return The image.
	 */
	public Image loadImage(String filename)
	{
		try
		{
			// Create a managed image for hardware acceleration.
			BufferedImage image = ImageIO.read(getClass().getResource(IMAGE_DIR + filename));
			int transparency = image.getColorModel().getTransparency();
			BufferedImage copy = gc.createCompatibleImage(image.getWidth(), image.getHeight(), transparency);

			Graphics2D g = copy.createGraphics();

			g.drawImage(image, 0, 0, null);
			g.dispose();
			return copy;
		}
		catch( IOException e )
		{
			System.out.println("Error while loading image: " + filename);
			return null; // image could not be loaded
		}
	}
}
