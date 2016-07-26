package sjgs.utils.data_structures.shapes;

import java.awt.Graphics2D;
import sjgs.utils.data_structures.vectors.Point2f;

public class Triangle {

	/** points go from bottom left corner @ [0], clockwise */
	public final Point2f[] points;

	public Triangle(final Point2f a, final Point2f b, final Point2f c) {
		points = new Point2f[3];
		points[0] = new Point2f(a.getX(), a.getY());
		points[1] = new Point2f(b.getX(), b.getY());
		points[2] = new Point2f(c.getX(), c.getY());
	}

	public void draw(final Graphics2D g2d) {
		g2d.drawLine((int)points[0].x, (int)points[0].y, (int)points[1].x, (int)points[1].y);
		g2d.drawLine((int)points[1].x, (int)points[1].y, (int)points[2].x, (int)points[2].y);
		g2d.drawLine((int)points[2].x, (int)points[2].y, (int)points[0].x, (int)points[0].y);
	}

}
