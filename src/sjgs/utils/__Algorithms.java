package sjgs.utils;

import static sjgs.utils.Utils.abs;
import java.util.ArrayList;
import sjgs.utils.data_structures.vectors.Point2f;
import sjgs.utils.data_structures.vectors.SimplePoint;

class __Algorithms {

	static SimplePoint[] brensenham(final SimplePoint p1, final SimplePoint p2) {
		final ArrayList<SimplePoint> points = new ArrayList<SimplePoint>();

		// BRENSENHAM the line across
		final int dx =  abs(p2.x-p1.x), sx = p1.x<p2.x ? 1 : -1;
		final int dy = -abs(p2.y-p1.y), sy = p1.y<p2.y ? 1 : -1;
		int err = dx+dy, e2; /* error value e_xy */

		while(true){
			points.add(new SimplePoint(p1.x, p1.y));
			if (p1.x==p2.x && p1.y==p2.y) break;
			e2 = 2*err;
			if (e2 >= dy) { err += dy; p1.x += sx; } /* e_xy+e_x > 0 */
			if (e2 <= dx) { err += dx; p1.y += sy; } /* e_xy+e_y < 0 */
		}

		return points.toArray(new SimplePoint[points.size()]);
	}

	static SimplePoint[] brensenham(final Point2f p1, final Point2f p2) {
		final ArrayList<SimplePoint> points = new ArrayList<SimplePoint>();

		// BRENSENHAM the line across
		final int dx =  (int)abs(p2.x-p1.x), sx = p1.x<p2.x ? 1 : -1;
		final int dy = (int)-abs(p2.y-p1.y), sy = p1.y<p2.y ? 1 : -1;
		int err = dx+dy, e2; /* error value e_xy */

		while(true){
			points.add(new SimplePoint((int)p1.x, (int)p1.y));
			if (p1.x==p2.x && p1.y==p2.y) break;
			e2 = 2*err;
			if (e2 >= dy) { err += dy; p1.x += sx; } /* e_xy+e_x > 0 */
			if (e2 <= dx) { err += dx; p1.y += sy; } /* e_xy+e_y < 0 */
		}

		return points.toArray(new SimplePoint[points.size()]);
	}

	static SimplePoint[] brensenham(int x1, int y1, final int x2, final int y2) {
		final ArrayList<SimplePoint> points = new ArrayList<SimplePoint>();

		// BRENSENHAM the line across
		final int dx =  abs(x2-x1), sx = x1<x2 ? 1 : -1;
		final int dy = -abs(y2-y1), sy = y1<y2 ? 1 : -1;
		int err = dx+dy, e2; /* error value e_xy */

		while(true){
			points.add(new SimplePoint(x1, y1));
			if (x1==x2 && y1==y2) break;
			e2 = 2*err;
			if (e2 >= dy) { err += dy; x1 += sx; } /* e_xy+e_x > 0 */
			if (e2 <= dx) { err += dx; y1 += sy; } /* e_xy+e_y < 0 */
		}

		return points.toArray(new SimplePoint[points.size()]);
	}

}
