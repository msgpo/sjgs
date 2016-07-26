package sjgs.utils;

import static sjgs.utils.Utils.dialog;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

class __ImageManipulation {

	static BufferedImage optimizeImage(final BufferedImage image) {
		// obtain the current system graphical settings
		final GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

		/* if image is already compatible and optimized for current system
		 * settings, simply return it  */
		if (image.getColorModel().equals(gfx_config.getColorModel())) return image;

		// image is not optimized, so create a new image that is
		final BufferedImage new_image = gfx_config.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

		// get the graphics context of the new image to draw the old image on
		final Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		// return the new optimized image
		return new_image;
	}

	static final Color voidColor = new Color(255, 0, 255);

	//----------------------------------------------------------------------------------------------------------------------------------------------//
	static BufferedImage reverseImage(final BufferedImage image) { /* (flips image horizontally) */
		final BufferedImage mirrorImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB); /* note, you need ARGB */
		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0, reverseX = image.getWidth() - 1; x < image.getWidth(); x++, reverseX--)
				mirrorImage.setRGB(reverseX, y, image.getRGB(x, y));
		return optimizeImage(mirrorImage);
	}
	static BufferedImage rotateImageRight(final BufferedImage image) {
		final BufferedImage rotatedImage = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
		for(int i=0; i < image.getWidth(); i++ )
			for(int j=0; j < image.getHeight(); j++ )
				rotatedImage.setRGB( image.getHeight()-1-j, i, image.getRGB(i,j));
		return optimizeImage(rotatedImage);
	}
	// I know this is really bad but the above was so much headache i said fuck it, it works.
	static BufferedImage rotateImageLeft(final BufferedImage image) {
		return optimizeImage(rotateImageRight(rotateImageRight(rotateImageRight(image))));
	}
	static BufferedImage invertImage(final BufferedImage image) { /* (flips image horizontally) */
		return optimizeImage(rotateImageRight(rotateImageRight(image)));
	}
	//----------------------------------------------------------------------------------------------------------------------------------------------//
	static BufferedImage imageToBufferedImage(Image image) {
		final BufferedImage buffered = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		buffered.getGraphics().drawImage(image, 0, 0, null); image = null;
		return optimizeImage(buffered);
	}

	static BufferedImage loadImage(final String path) { /* loads as resource to work in jars */
		try { InputStream is = Utils.class.getResourceAsStream(path);
		final BufferedImage image = ImageIO.read(is); is.close(); is = null; return optimizeImage(removeVoidColor(image, voidColor));
		} catch (final Exception e) { e.printStackTrace(); dialog("Utils --- loadImage --- Failed + \n + Contact the developer."); }
		return null;
	}

	static BufferedImage removeVoidColor(final BufferedImage image, final Color voidColor) {
		for(int i = 0; i < image.getWidth(); i++)
			for(int j = 0; j < image.getHeight(); j++){
				final int RGB = image.getRGB(i, j);
				final int red = convertRed(RGB);
				final int green = convertGreen(RGB);
				final int blue = convertBlue(RGB);
				if(red == voidColor.getRed() && green == voidColor.getGreen() && blue == voidColor.getBlue())
					image.setRGB(i, j, voidColor.getRGB()&0x00ffffff);
			}
		return optimizeImage(image);
	}

	static final BufferedImage grabSprite(final BufferedImage image, final int col, final int row, final int width, final int height) {
		return optimizeImage(removeVoidColor(image.getSubimage((col+1) * width - width, (row+1) * height - height, width, height), voidColor));
	}

	/************************************ IMAGE RGB MANIPULATION ********************************************/
	static final int convertRed(final int pixel){ return pixel >> 16 & 0xff; }
	static final int convertGreen(final int pixel){ return pixel >> 8 & 0xff; }
	static final int convertBlue(final int pixel){ return pixel & 0xff; }
	/********************************************************************************************************/
}
