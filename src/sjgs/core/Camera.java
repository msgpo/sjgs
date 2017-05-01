package sjgs.core;

import static sjgs.utils.Utils.brensenham;
import static sjgs.utils.Utils.cinch;
import static sjgs.utils.Utils.converge;
import static sjgs.utils.Utils.cos;
import static sjgs.utils.Utils.sin;
import sjgs.utils.data_structures.shapes.Rectangle;
import sjgs.utils.data_structures.vectors.Point2f;
import sjgs.utils.data_structures.vectors.SimplePoint;

public class Camera { //TODO: camera acceleartion / delay and shake

	private final Rectangle bounds;

	private SimplePoint[] shakePoints;
	private float shakeSpeed;
	private int shakeIndex;

	private float acceleration, speedX, speedY, maxSpeed;

	private float minX, minY, maxX, maxY;

	public Camera(final Engine engine) {
		bounds = new Rectangle(0, 0, engine.getWidth(), engine.getHeight());
	}

	public void tick(final Point2f target, final double scaleFactor) {
		
		final float w = (float)(getWidth() / scaleFactor);
		final float h = (float)(getHeight() / scaleFactor);
		final float hw = w * 0.5f;
		final float hh = h * 0.5f;

		float proposedX;
		float proposedY;
		final float tX = -target.x + hw;
		final float tY = -target.y + hh;

		// IF WE'RE NOT SHAKING:
		if(shakePoints == null) {

			// IF THERE'S NO ACCELERATION
			if(acceleration <= 0) {
				proposedX = tX;
				proposedY = tY;
				// ELSE IF THERE *IS* ACCELERATION
			} else {
				proposedX = converge(getX(), tX, speedX);
				proposedY = converge(getY(), tY, speedY);

				if(getX() != tX) speedX = cinch(speedX + acceleration, maxSpeed);
				else speedX = 0;

				if(getY() != tY) speedY = cinch(speedY + acceleration, maxSpeed);
				else speedY = 0;
			} // ELSE IF WE *ARE* SHAKING
		} else {
			if(shakeIndex >= shakePoints.length - 1) shakePoints = null;
			proposedX = shakePoints[shakeIndex].x;
			proposedY = shakePoints[shakeIndex].y;
			shakeIndex++;
		}

		// IF THERE ARE BOUNDARIES SET
		if(!(minX == 0 && minY == 0 && maxX == 0 && maxY == 0)) {
			if(-proposedX < minX) setX(minX);
			else if(-proposedX + w > maxX) setX(-(maxX - w));
			else setX(proposedX);

			if(-proposedY < minY) setY(minY);
			else if(-proposedY + h > maxY) setY(-(maxY - h));
			else setY(proposedY);
			// ELSE IF THERE *ISNT* BOUNDARIES SET
		} else {
			setX(proposedX);
			setY(proposedY);
		}
	}

	public void changeSettings(final float screenWidth, final float screenHeight) { bounds.setSize(screenWidth, screenHeight); }

	public float getX() { return bounds.getX(); }
	public float getY() { return bounds.getY(); }
	public float getCenterX() { return bounds.getCenterX(); }
	public float getCenterY() { return bounds.getCenterY(); }
	public float getWidth() { return bounds.getWidth(); }
	public float getHeight() { return bounds.getHeight(); }
	public float getHalfWidth() { return bounds.getHalfWidth(); }
	public float getHalfHeight() { return bounds.getHalfHeight(); }
	public void setX(final float x) { bounds.setX(x); }
	public void setY(final float y) { bounds.setY(y); }
	public Point2f getPos() { return bounds.pos; }
	public Point2f getCenter() { return bounds.getCenter(); }
	public void setScreenWidth(final float screenWidth) { bounds.setWidth(screenWidth); }
	public void setScreenHeight(final float screenHeight) { bounds.setHeight(screenHeight); }

	public void disableAcceleration() { acceleration = maxSpeed = -1; }
	public void setAcceleration(final float acceleration, final float maxSpeed) { this.acceleration = acceleration; this.maxSpeed = maxSpeed; }

	public void setLimitsWithRect(final float x, final float y, final float width, final float height) {
		minX = x;
		minY = y;
		maxX = x + width;
		maxY = y + height;
	}

	public void setLimits(final float minX, final float minY, final float maxX, final float maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	/** @shake: Moves the center of the camera to a point away from the camera center based on
	 * 			the given distance and angle in radians. The transition is governed by the attack
	 * 			and fall durations. */
	public void shake(final float distance, final float theta, final float shakeSpeed) {
		shakePoints = brensenham(getPos(), new Point2f(getX() + distance * cos(theta), getY() + distance * sin(theta)));
		shakeIndex = 0;
		this.shakeSpeed = shakeSpeed;
	}

}
