package sjgs.graphics.lighting;

import java.awt.Graphics2D;
import sjgs.core.Camera;
import sjgs.core.Engine;
import sjgs.utils.data_structures.vectors.Point2f;

public abstract class Light {

	protected final float intensity;
	protected final Point2f center;

	public Light(final float x, final float y, final float intensity) {
		this.intensity = intensity;
		center = new Point2f(x, y);
	}

	abstract void update(Graphics2D lightsRenderer, Camera camera, Engine engine);

	public void tick() { }
	public void destroy() { }


	public float getX() { return center.x; }
	public float getY() { return center.y; }
	public Point2f getCenter() { return center; }
	public float getIntensity() { return intensity; }

	public void setX(final float x) { center.setX(x); }
	public void setY(final float y) { center.setY(y); }
	public void setLocation(final float x, final float y) { setX(x); setY(y); }
}
