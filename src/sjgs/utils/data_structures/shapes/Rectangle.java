package sjgs.utils.data_structures.shapes;

import static sjgs.utils.Utils.clamp;
import static sjgs.utils.Utils.max;
import static sjgs.utils.Utils.min;
import static sjgs.utils.Utils.square;
import java.awt.Graphics2D;
import java.awt.Point;
import sjgs.utils.data_structures.vectors.Point2f;
import sjgs.utils.data_structures.vectors.SimplePoint;

public class Rectangle {

	public float width, height;
	public Point2f pos;

	public Rectangle(final float x, final float y, final float width, final float height) {
		pos = new Point2f(x, y); this.width = width; this.height = height;
	}

	public Rectangle(final Point2f pos, final float width, final float height) {
		this.pos = new Point2f(pos.x, pos.y); this.width = width; this.height = height;
	}

	public Rectangle(final Point2f tl, final Point2f tr, final Point2f bl, @SuppressWarnings("unused") final Point2f br) {
		pos = tl;
		width = tr.x - tl.x;
		height = bl.y - tl.y;
	}

	public boolean intersectsWithCircle(final Circle c) {
		return square(c.center.x - clamp(c.center.x, pos.x, pos.x + width)) + square(c.center.y  - clamp(c.center.y, pos.y, pos.y + height)) < square(c.radius);
	}

	public boolean intersects(final Rectangle r) {
		return !(pos.x + width < r.pos.x ||   // if this is LEFT of r
				pos.x > r.pos.x + r.width || // if this is RIGHT of r
				pos.y + height < r.pos.y ||  // if this is BELOW r
				pos.y > r.pos.y + r.height); // if this is ABOVE r
	}

	public boolean intersects(final Line line) {
		return line.intersects(getTop()) || line.intersects(getRight()) || line.intersects(getBottom()) || line.intersects(getLeft());
	}

	public float getIntersectingArea(final Rectangle r) {
		if(!intersects(r)) return 0;

		final float newWidth = min(pos.x + width, r.pos.x + r.width) - max(pos.x, r.pos.x);
		final float newHeight = min(pos.y + height, r.pos.y + r.height) - max(pos.y, r.pos.y);

		return newWidth * newHeight;
	}

	public boolean contains(final Point2f p) {
		if(!(p.x > pos.x && p.x < pos.x + width)) return false; if(!(p.y > pos.y && p.y < pos.y + height)) return false; return true;
	}

	public boolean contains(final SimplePoint p) {
		if(!(p.x > pos.x && p.x < pos.x + width)) return false; if(!(p.y > pos.y && p.y < pos.y + height)) return false; return true;
	}

	public boolean contains(final Point p) {
		if(!(p.x > pos.x && p.x < pos.x + width)) return false; if(!(p.y > pos.y && p.y < pos.y + height)) return false; return true;
	}

	public boolean contains(final float x, final float y) {
		if(!(x > pos.x && x < pos.x + width)) return false; if(!(y > pos.y && y < pos.y + height)) return false; return true;
	}

	public boolean contains(final int x, final int y) {
		if(!(x > pos.x && x < pos.x + width)) return false; if(!(y > pos.y && y < pos.y + height)) return false; return true;
	}

	public boolean isSquare() { return width == height; }

	public void draw(final Graphics2D g2d) { g2d.drawRect((int)pos.x, (int)pos.y, (int)width, (int)height); }
	public void drawFilled(final Graphics2D g2d)  { g2d.fillRect((int)pos.x, (int)pos.y, (int)width, (int)height); }

	public Triangle toTriangle() {
		return new Triangle(new Point2f(getX(), getY() + getHeight()),
				new Point2f(getX() + getHalfWidth(), getY()),
				new Point2f(getX() + getWidth(), getY() + getHeight()));
	}

	@Override
	public Rectangle clone() { return new Rectangle(getX(), getY(), getWidth(), getHeight()); }
	@Override
	public String toString() { return "x: " + getX() + ", y: " + getY() + ", w: " + getWidth() + ", h: " + getHeight(); }

	public void setLocation(final float x, final float y) { pos.setLocation(x, y); }
	public void setSize(final float w, final float h) { width = w; height = h; }
	public float   getWidth()              { return width; }
	public void    setWidth(final float width)   { this.width = width; }
	public float   getHeight()             { return height; }
	public void    setHeight(final float height) { this.height = height; }
	public float   getHalfWidth()          { return width * 0.5f; }
	public float   getHalfHeight()         { return height * 0.5f; }
	public Point2f getPos()                { return pos; }
	public void    setPos(final Point2f pos)     { this.pos = pos; }
	public Point2f getCenter()  { return new Point2f(pos.x + getHalfWidth(), pos.y + getHalfHeight()); }
	public float   getCenterX() { return pos.x + width*0.5f; }
	public float   getCenterY() { return pos.y + height*0.5f; }
	public float   getArea() { return width * height; }
	public float   getX() { return pos.x; }
	public float   getY() { return pos.y; }
	public void    setX(final float x) { pos.x = x; }
	public void    setY(final float y) { pos.y = y; }

	public Point2f getTopLeft() { return pos; }
	public Point2f getTopRight() { return new Point2f(getX() + getWidth(), getY()); }
	public Point2f getBottomRight() {  return new Point2f(getX() + getWidth(), getY() + getHeight()); }
	public Point2f getBottomLeft() { return new Point2f(getX(), getY() + getHeight()); }

	public Line getTop() { return new Line(getTopLeft(), getTopRight()); }
	public Line getBottom() { return new Line(getBottomLeft(), getBottomRight()); }
	public Line getLeft() { return new Line(getBottomLeft(), getTopLeft()); }
	public Line getRight() { return new Line(getTopRight(), getBottomRight()); }

}
