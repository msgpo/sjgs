package sjgs.utils.data_structures.shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import sjgs.utils.Utils;
import sjgs.utils.data_structures.vectors.Point2f;

public class Circle {

	public Point2f center;
	public float radius;

	public Circle(final Point2f center, final float radius){ this.center = center; this.radius = radius; }

	public Rectangle squareBounds(){ return new Rectangle((int)(center.x - radius), (int)(center.y - radius), (int)radius, (int)radius); }

	public void draw(final Graphics2D g2d){ g2d.drawOval((int)(center.x - radius), (int)(center.y - radius), (int)diameter(), (int)diameter()); }
	public  void drawFilled(final Graphics2D g2d){ g2d.fillOval((int)(center.x - radius), (int)(center.y - radius), (int)diameter(), (int)diameter()); }

	public boolean contains(final Point2f p){ return Utils.square(p.x - center.x) + Utils.square(p.y - center.y) < Utils.square(radius); }
	public boolean contains(final Point p){ return Utils.square(p.x - center.x) + Utils.square(p.y - center.y) < Utils.square(radius); }
	public boolean contains(final int x, final int y){ return Utils.square(x - center.x) + Utils.square(y - center.y) < Utils.square(radius); }
	public boolean contains(final float x, final float y){ return Utils.square(x - center.x) + Utils.square(y - center.y) < Utils.square(radius); }

	public float getWidth(){ return radius*2; }
	public float getHeight(){ return radius*2; }
	public float diameter(){ return radius*2; }

	@Override
	public String toString(){ return "radius: " + Utils.df.format(radius) + ", " + "center: " + center; }

	public boolean intersects(final Circle c) {
		return center.distance(c.center) <= radius + c.radius;
	}

	public boolean intersects(final Point2f center2, final float r2) {
		return center.distance(center2) <= radius + r2;
	}

}
