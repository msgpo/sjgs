package sjgs.graphics;

import static sjgs.utils.Utils.invertImage;
import static sjgs.utils.Utils.reverseImage;
import static sjgs.utils.Utils.rotateImageLeft;
import static sjgs.utils.Utils.rotateImageRight;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import sjgs.physics.structs.BoundingBox;
import sjgs.utils.Utils;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.shapes.Rectangle;
import sjgs.utils.data_structures.vectors.Point2f;
import sjgs.utils.data_structures.vectors.SimplePoint;

public class Animation {

	/** @param speed : the number of ticks needed to advance each frame
	 *  @param timer : the index of the current tick amount
	 *  @param currentFrame : the index of the currently displaying image */

	private int speed;
	public int currentFrame, timer;
	private final BufferedImage[] images;

	public Animation(final int speed, final BufferedImage... args) {
		this.speed = speed; images = new BufferedImage[args.length];
		for (int i = 0; i < args.length; i++)  images[i] = args[i];
	}

	public Animation(final int speed, final Stack<BufferedImage> args) {
		this.speed = speed; images = new BufferedImage[args.size()];
		for (int i = 0; i < args.size(); i++)  images[i] = args.get(i);
	}

	public Animation(final int speed, final Image... args) {
		this.speed = speed; images = new BufferedImage[args.length];
		for (int i = 0; i < args.length; i++)  images[i] = Utils.imageToBufferedImage(args[i]);
	}

	public void runAnimation() { if (++timer > speed) nextFrame(); }

	public void playAnimationOnce() {
		if (currentFrame != images.length - 1 && ++timer > speed) nextFrame();
	}

	public void nextFrame() {
		if (++currentFrame >= images.length) restartAnimation();
		else timer = 0;
	}

	public void restartAnimation() { currentFrame = timer = 0; }

	public void drawAnimation(final Graphics2D g2d, final Point2f p){ g2d.drawImage(images[currentFrame], (int) p.x, (int) p.y, null); }
	public void drawAnimation(final Graphics2D g2d, final SimplePoint p){ g2d.drawImage(images[currentFrame], p.x, p.y, null); }
	public void drawAnimation(final Graphics2D g2d, final Point p){ g2d.drawImage(images[currentFrame], p.x, p.y, null); }
	public void drawAnimation(final Graphics2D g2d, final double x, final double y) { g2d.drawImage(images[currentFrame], (int) x, (int) y, null); }
	public void drawAnimation(final Graphics2D g2d, final float x, final float y) { g2d.drawImage(images[currentFrame], (int) x, (int) y, null); }
	public void drawAnimation(final Graphics2D g2d, final int x, final int y) { g2d.drawImage(images[currentFrame], x, y, null); }
	public void drawAnimation(final Graphics2D g2d, final Rectangle r) { g2d.drawImage(images[currentFrame], (int)r.getPos().x, (int)r.getPos().y, null); }
	public void drawAnimation(final Graphics2D g2d, final BoundingBox b) { g2d.drawImage(images[currentFrame], (int)b.getX(), (int)b.getY(), null); }

	public BufferedImage getLastFrame() { return images[images.length - 1]; }

	public int size() { return images.length; }

	public BufferedImage[] getImages() { return images; }

	public void setSpeed(final int speed) { this.speed = speed; }

	@Override
	public Animation clone() { return new Animation(speed, images); }

	public Animation getReverse() {
		final BufferedImage[] sprites = new BufferedImage[images.length];
		for(int i = 0; i < images.length; i++) sprites[i] = reverseImage(images[i]);
		return new Animation(speed, sprites);
	}

	public Animation getInverted() {
		final BufferedImage[] sprites = new BufferedImage[images.length];
		for(int i = 0; i < images.length; i++) sprites[i] = invertImage(images[i]);
		return new Animation(speed, sprites);
	}

	public Animation getRotatedRight() {
		final BufferedImage[] sprites = new BufferedImage[images.length];
		for(int i = 0; i < images.length; i++) sprites[i] = rotateImageRight(images[i]);
		return new Animation(speed, sprites);
	}

	public Animation getRotatedLeft() {
		final BufferedImage[] sprites = new BufferedImage[images.length];
		for(int i = 0; i < images.length; i++) sprites[i] = rotateImageLeft(images[i]);
		return new Animation(speed, sprites);
	}

}
