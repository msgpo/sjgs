package sjgs.utils.data_structures.shapes;

import static sjgs.utils.Utils.max;
import static sjgs.utils.Utils.min;
import java.awt.Graphics2D;
import sjgs.utils.data_structures.vectors.Point2f;

public class Line {

	public final Point2f p1, p2;
	public final float angle;

	public Line(final Point2f p1, final Point2f p2) {
		this.p1 = p1; this.p2 = p2;
		angle = p1.direction(p2);
	}

	public Line(final float x1, final float y1, final float x2, final float y2) {
		p1 = new Point2f(x1, y1); p2 = new Point2f(x2, y2);
		angle = p1.direction(p2);
	}

	public void draw(final Graphics2D g2d) {
		g2d.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
	}

	public boolean intersects(final Line l) { return doIntersect(p1, p2, l.p1, l.p2); }


	// ----------------- INTERSECTION HELPERS ------------------------------------------------------------------------------------ //
	private boolean onSegment(final Point2f p, final Point2f q, final Point2f r) {
		return q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) && q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y);
	}

	private int orientation(final Point2f p, final Point2f q, final Point2f r) {
		final float val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
		return val == 0 ? 0 : val > 0? 1: 2;  // colinear
	}

	private boolean doIntersect(final Point2f p1, final Point2f q1, final Point2f p2, final Point2f q2)
	{
		// Find the four orientations needed for general and
		// special cases
		final int o1 = orientation(p1, q1, p2);
		final int o2 = orientation(p1, q1, q2);
		final int o3 = orientation(p2, q2, p1);
		final int o4 = orientation(p2, q2, q1);

		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		return o1 != o2 && o3 != o4 ||
				o1 == 0 && onSegment(p1, p2, q1) ||
				// p1, q1 and p2 are colinear and q2 lies on segment p1q1
				o2 == 0 && onSegment(p1, q2, q1) ||
				// p2, q2 and p1 are colinear and p1 lies on segment p2q2
				o3 == 0 && onSegment(p2, p1, q2) ||
				// p2, q2 and q1 are colinear and q1 lies on segment p2q2
				o4 == 0 && onSegment(p2, q1, q2);
	}
	// ---------------- END INTERSECTION HELPERS ------------------------------------------------------------------------------------ //


}
