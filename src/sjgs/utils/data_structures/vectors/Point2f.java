package sjgs.utils.data_structures.vectors;

import static sjgs.utils.Utils.atan2;
import java.awt.Graphics2D;
import java.awt.Point;
import sjgs.utils.Utils;

public class Point2f {

	public float x, y;

	public Point2f(final float x, final float y) { this.x = x; this.y = y; }
	public Point2f(final double x, final double y) { this.x = (float)x; this.y = (float)y;}
	public Point2f(final int x, final int y) { this.x = x; this.y = y;}
	public Point2f(final long x, final long y) { this.x = x; this.y = y;}
	public Point2f() { x = 0f; y = 0f; }
	public Point2f(final Point2f p) { x = p.x; y = p.y; }
	public Point2f(final Point p) { x = p.x; y = p.y; }
	public Point2f(final SimplePoint p) { x = p.x; y = p.y; }

	public void setLocation(final float x, final float y){ this.x = x; this.y = y; }

	public void rotate(final double theta) {
		final float temp = Utils.rotateX(x, y, theta);
		y = Utils.rotateY(x, y, theta);
		x = temp;
	}

	public float distance(final Point2f p){ return Utils.sqrt(Utils.square(y - p.y) + Utils.square(x - p.x)); }
	public float distance(final Point p){ return Utils.sqrt(Utils.square(y - p.y) + Utils.square(x - p.x)); }
	public float distance(final SimplePoint p){ return Utils.sqrt(Utils.square(y - p.y) + Utils.square(x - p.x)); }
	public int distance(final int x, final int y){ return (int)Utils.sqrt(Utils.square(this.y - y) + Utils.square(this.x - x)); }
	public float distance(final float x, final float y){ return Utils.sqrt(Utils.square(this.y - y) + Utils.square(this.x - x)); }
	public double distance(final double x, final double y){ return Utils.sqrt(Utils.square(this.y - y) + Utils.square(this.x - x)); }
	public long distance(final long x, final long y){ return (long)Utils.sqrt(Utils.square(this.y - y) + Utils.square(this.x - x)); }

	// NOTE: returns the angle in radians!
	public final float direction(final Point2f p){ return atan2(p.y - y, p.x - x); }

	public Point toPoint(){ return new Point((int)x, (int)y); }
	public SimplePoint toSimplePoint() { return new SimplePoint((int)x, (int)y); }

	public void draw(final Graphics2D g2d){ g2d.drawString(".", x - 2, y); } // need the -2, not sure why but this is correct!
	@Override
	public String toString(){ return "(" + Utils.df.format(x) + ", " + Utils.df.format(y) + ")"; }

	@Override
	public Point2f clone() { return new Point2f(this); }

	public float getX() { return x; }
	public float getY() { return y; }
	public void setX(final float x) { this.x = x; }
	public void setY(final float y) { this.y = y; }


}
