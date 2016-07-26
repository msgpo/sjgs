package sjgs.physics.structs;

import java.awt.Graphics2D;
import sjgs.enums.Facing;
import sjgs.enums.Type;
import sjgs.utils.Utils;
import sjgs.utils.data_structures.shapes.Line;
import sjgs.utils.data_structures.shapes.Rectangle;
import sjgs.utils.data_structures.vectors.Point2f;
import sjgs.utils.data_structures.vectors.SimplePoint;
import sjgs.utils.data_structures.vectors.Vector2f;

public class BoundingBox {

	/** @type: The kind of object that this bounding box is attached to */
	private final Type type;
	private Facing facing = Facing.NONE; // note they always start out @ none
	private final Rectangle fullBounds, leftBounds, rightBounds, topBounds, bottomBounds;
	private float LONG, SMALL;
	private final Vector2f velocity;
	private float gravity;
	private boolean useCustomGravity;

	// ---------- booleans for the physics interpreter ----------- //
	private boolean dontApplyGravity, grounded, onSlope, dontApplyFriction;
	// ---------------------------------------------------------- //

	public BoundingBox(final float x, final float y, final float WIDTH, final float HEIGHT, final Type type) {
		this.type = type;
		velocity = new Vector2f(0,0);
		getLS(WIDTH, HEIGHT);
		fullBounds = new Rectangle(0, 0, 0, 0);
		leftBounds = new Rectangle(0, 0, 0, 0);
		rightBounds = new Rectangle(0, 0, 0, 0);
		topBounds = new Rectangle(0, 0, 0, 0);
		bottomBounds = new Rectangle(0, 0, 0, 0);
		setSize(WIDTH, HEIGHT);
		setLocation(x, y);
	}
	public BoundingBox(final float x, final float y, final float WIDTH, final float HEIGHT, final float velX, final float velY, final Type type) {
		this.type = type;
		velocity = new Vector2f(velX, velY);
		getLS(WIDTH, HEIGHT);
		fullBounds = new Rectangle(0, 0, 0, 0);
		leftBounds = new Rectangle(0, 0, 0, 0);
		rightBounds = new Rectangle(0, 0, 0, 0);
		topBounds = new Rectangle(0, 0, 0, 0);
		bottomBounds = new Rectangle(0, 0, 0, 0);
		setSize(WIDTH, HEIGHT);
		setLocation(x, y);
	}

	@Override
	public BoundingBox clone() {
		final BoundingBox clone = new BoundingBox(getX(), getY(), getWidth(), getHeight(), getVelX(), getVelY(), type);
		clone.onSlope = onSlope;
		clone.grounded = grounded;
		return clone;
	}

	@SuppressWarnings("unused")
	private void getLS(final float w, final float h) {
		switch(type) {
		default: LONG = 1f/3f; SMALL = 1f/9f;
		}
	}

	public void setLocation(final float x, final float y) {
		fullBounds.setLocation(x, y);
		leftBounds.setLocation(x, y + (getHeight() - leftBounds.height)/2);
		rightBounds.setLocation(x+getWidth() - rightBounds.width, y + (getHeight() - leftBounds.height)/2);
		topBounds.setLocation(x+(getWidth() - topBounds.width)/2, y);
		bottomBounds.setLocation(x+(getWidth() - topBounds.width)/2, y+getHeight()-bottomBounds.height);
	}

	public void setSize(final float w, final float h) {
		fullBounds.setSize(w, h);
		leftBounds.setSize(Utils.restrict(w*SMALL, 1), Utils.restrict(h*LONG, 1));
		rightBounds.setSize(Utils.restrict(w*SMALL, 1), Utils.restrict(h*LONG, 1));
		topBounds.setSize(Utils.restrict(w*LONG, 1), Utils.restrict(h*SMALL, 1));
		bottomBounds.setSize(Utils.restrict(w*LONG, 1), Utils.restrict(h*SMALL, 1));
	}

	public void draw(final Graphics2D g2d) {
		fullBounds.draw(g2d);
		leftBounds.draw(g2d);
		rightBounds.draw(g2d);
		topBounds.draw(g2d);
		bottomBounds.draw(g2d);
	}

	public void drawFilled(final Graphics2D g2d) { g2d.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight()); }


	public void dontUseCustomGravity() { useCustomGravity = false; gravity = 0; }
	public void setCustomGravity(final float gravity) { useCustomGravity = true; this.gravity = gravity; }
	public float getCustomGravity() { return gravity; }
	public boolean useCustomGravity() { return useCustomGravity; }
	public boolean stopped() { return getVelX() == 0 && getVelY() == 0; }
	public void invertVelX() { velocity.invertX(); }
	public void invertVelY() { velocity.invertY(); }
	public float getVelX() { return velocity.getX(); }
	public float getVelY() { return velocity.getY(); }
	public void setVelX(final float velX) { velocity.setX(velX); }
	public void setVelY(final float velY) { velocity.setY(velY); }
	public void setVelocity(final float velX, final float velY) { velocity.setXAndY(velX, velY); }
	public float getArea() { return fullBounds.getArea(); }
	public Point2f getLocation() { return new Point2f(getX(), getY()); }
	public void setCenter(final float x, final float y) { setLocation(x - getHalfWidth(), y - getHalfHeight()); }
	public void setCenter(final Point2f p) { setCenter(p.x, p.y); }
	public void setCenter(final SimplePoint p) { setCenter(p.x, p.y); }
	public float getHalfWidth() { return getWidth() * 0.5f; }
	public float getHalfHeight() { return getHeight() * 0.5f; }
	public boolean getDontApplyGravity() { return dontApplyGravity; }
	public void setDontApplyGravity(final boolean b) { dontApplyGravity = b; }
	public void toggleDontApplyGravity() { dontApplyGravity = !dontApplyGravity; }
	public boolean getDontApplyFriction() { return dontApplyFriction; }
	public void setDontApplyFriction(final boolean b) { dontApplyFriction = b; }
	public void toggleDontApplyFriction() { dontApplyFriction = !dontApplyFriction; }
	public boolean facingLeft()  { return facing == Facing.LEFT; }
	public boolean facingRight() { return facing == Facing.RIGHT; }
	public Facing getFacing() { return facing; }
	public void setFacing(final Facing facing) { this.facing = facing; }
	public Rectangle getFullBounds() { return fullBounds; }
	public Rectangle getLeftBounds() { return leftBounds; }
	public Rectangle getRightBounds() { return rightBounds; }
	public Rectangle getTopBounds() { return topBounds; }
	public Rectangle getBottomBounds() { return bottomBounds; }
	public boolean getGrounded() { return grounded; }
	public void setGrounded(final boolean grounded) { this.grounded = grounded; }
	public boolean getOnSlope() { return onSlope; }
	public void setOnSlope(final boolean onSlope) { this.onSlope = onSlope; }
	public Type getType() { return type; }
	public Point2f getCenter() { return fullBounds.getCenter(); }
	public Point2f getPosition() { return fullBounds.pos; }
	public float getX()  { return fullBounds.pos.x; }
	public float getY()  { return fullBounds.pos.y; }
	public float getWidth()  { return fullBounds.width; }
	public float getHeight() { return fullBounds.height; }
	public void setX(final float x) { setLocation(x, getY()); }
	public void setY(final float y) { setLocation(getX(), y); }
	public void setWidth(final float w)  { setSize(w, getHeight()); }
	public void setHeight(final float h) { setSize(getWidth(), h);  }

	public Point2f getTopRight() { return fullBounds.getTopRight(); }
	public Point2f getTopLeft() { return fullBounds.getTopLeft(); }
	public Point2f getBottomRight() { return fullBounds.getBottomRight(); }
	public Point2f getBottomLeft() { return fullBounds.getBottomLeft(); }
	public Line getTop() { return fullBounds.getTop(); }
	public Line getBottom() { return fullBounds.getBottom(); }
	public Line getLeft() { return fullBounds.getLeft(); }
	public Line getRight() { return fullBounds.getRight(); }

}
