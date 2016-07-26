package sjgs.utils.data_structures.vectors;

import static sjgs.utils.Utils.atan2;
import static sjgs.utils.Utils.cos;
import static sjgs.utils.Utils.sin;

public class Vector2f {

	/** @class Vector2f is just a vector, essentially its a copy of Point2f.
	 * 	However, I feel the names and variables make this class more explicit,
	 * 	and makes it easier to use than just two Point2f's for pos and vel, for example. */

	private float x, y;

	public Vector2f(final float x, final float y) { this.x = x; this.y = y; }
	public Vector2f(final float theta) { setXYtoTheta(theta); }

	public void add(final Vector2f vect) { x += vect.x; y += vect.y; }
	public void subtract(final Vector2f vect) { x -= vect.x; y -= vect.y; }

	public float getX() { return x; }
	public float getY() { return y; }
	public void  setX(final float x) { this.x = x; }
	public void  setY(final float y) { this.y = y; }
	public void  setXAndY(final float x, final float y) { this.x = x; this.y = y; }

	@Override
	public Vector2f clone() { return new Vector2f(x, y); }
	@Override
	public String toString() { return "v1: " + x + ", v2: " + y; }

	public void invertY() { y = -y; }
	public void invertX() { x = -x; }
	public void invertXandY() { x = -x; y = -y; }

	public float getTheta() { return atan2(y, x); }
	public void setXYtoTheta(final float theta) { x = cos(theta); y = sin(theta); }

}