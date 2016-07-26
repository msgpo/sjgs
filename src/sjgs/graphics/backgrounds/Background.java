package sjgs.graphics.backgrounds;

import static sjgs.utils.Utils.exit;
import static sjgs.utils.Utils.print;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import sjgs.core.Camera;
import sjgs.utils.data_structures.vectors.SimplePoint;

public class Background {

	private final BufferedImage image;
	private final float scrollX, scrollY;
	private final SimplePoint start;
	private final boolean loop;

	// NOTE a background that shouldn't move with that player, that is to be completely static, should have a scrollPercentage of 0
	public Background(final BufferedImage image, final float x, final float y, final float scrollPercentageX, final float scrollPercentageY, final boolean shouldLoop) {
		this.image = image;
		if(scrollPercentageX > 100 || scrollPercentageX < 0 || scrollPercentageY > 100 || scrollPercentageY < 0) { print("Incorrect scroll %"); exit(); }
		scrollX = scrollPercentageX / 100f;
		scrollY = scrollPercentageY / 100f;
		start = new SimplePoint((int)x, (int)y);
		loop = shouldLoop;
	}

	public void render(final Graphics2D g2d, final Camera camera, @SuppressWarnings("unused") final double scaleFactor) {
		final int x = start.x + (int)(camera.getX() * scrollX);
		final int y = start.y + (int)(camera.getY() * scrollY);


		g2d.drawImage(image, x, y, null);


		// NOTE THIS IS CURRENTLY BROKEN:

		//		int screenCovered = x;
		// start with nothing drawn, at the camera X
		// continue to redraw the background until the full screen is covered
		//		do {
		//			// set clip if it would go over
		//			if(screenCovered + image.getWidth() > x + camera.getWidth() / scaleFactor) {
		//				final int lengthNeeded = (int) ((x + camera.getWidth() / scaleFactor) - screenCovered);
		//				final Graphics2D copy = (Graphics2D) g2d.create();
		//				copy.setClip(screenCovered, y, lengthNeeded, image.getHeight());
		//				copy.drawImage(image, screenCovered, y, null);
		//			} else g2d.drawImage(image, screenCovered, y, null);
		//
		//
		//			screenCovered += image.getWidth();
		//		} while(loop && screenCovered < x + camera.getWidth() / scaleFactor);

	}

}
