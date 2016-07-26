package sjgs.base_objects;

import static sjgs.utils.pyutils.PyUtils.java2py;
import java.awt.Graphics2D;
import java.io.Serializable;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import sjgs.enums.Facing;
import sjgs.enums.Type;
import sjgs.physics.Physics;
import sjgs.physics.structs.BoundingBox;
import sjgs.physics.structs.CollisionResponse;
import sjgs.utils.data_structures.shapes.Line;
import sjgs.utils.data_structures.shapes.Rectangle;
import sjgs.utils.data_structures.vectors.Point2f;
import sjgs.utils.data_structures.vectors.SimplePoint;
import sjgs.utils.pyutils.PyUtils;

public abstract class GameObject implements Serializable {

	private final BoundingBox bounds;
	private final Physics physics;
	public final PyObject self;

	public GameObject(final float x, final float y, final float w, final float h, final Type type) {
		bounds = new BoundingBox(x, y, w, h, type);
		physics = new Physics();
		init();
		self = java2py(this);
	}

	protected abstract void init();
	public abstract void tick();
	public abstract void render(Graphics2D g2d);
	protected abstract void destroy();
	protected abstract void removeFromHandler();

	protected CollisionResponse applyPhysics() { return applyPhysics(Physics.getDefaultGravity(), Physics.getDefaultFriction(), Type.HARD_OBJECT); }
	protected CollisionResponse applyPhysics(final Type ...args) { return applyPhysics(Physics.getDefaultGravity(), Physics.getDefaultFriction(), args); }
	protected CollisionResponse applyPhysics(final float SPECIFIED_GRAVITY, final float SPECIFIED_FRICTION, final Type ...args) {
		physics.updatePosition(this);
		physics.applyFriction(this, SPECIFIED_FRICTION);
		final CollisionResponse response = physics.collision(this, args);
		physics.applyGravity(this, SPECIFIED_GRAVITY);
		physics.calcGroundedAndOnSlope(this, response.collided_hard_objects);
		return response;
	}

	public boolean falling() { return getFalling(); }
	public boolean getFalling() { return !getOnSlope() && !getGrounded() && getVelY() > 0; }
	public PyObject getSelf() { return self; }
	public boolean movingRight() { return getVelX() > 0; }
	public boolean movingLeft() { return getVelX() < 0; }
	public boolean movingDown() { return getVelY() > 0; }
	public boolean movingUp() { return getVelY() < 0; }
	public Physics getPhysics() { return physics; } // NOTE you should really never need to do this!
	public boolean isMoving() { return getVelX() != 0 || getVelY() != 0; }
	public void updatePosition() { physics.updatePosition(this); }
	public CollisionResponse getCollidedObjects() { return physics.getCollidedObjects(this); }
	public boolean useCustomGravity() { return bounds.useCustomGravity(); }
	public void dontUseCustomGravity() { bounds.dontUseCustomGravity(); }
	public void setCustomGravity(final float gravity) { bounds.setCustomGravity(gravity); }
	public float getCustomGravity() { return getCustomGravity(); }
	public boolean stopped() { return bounds.stopped(); }
	public float getVelX() { return bounds.getVelX(); }
	public float getVelY() { return bounds.getVelY(); }
	public void setVelX(final float velX) { bounds.setVelX(velX); }
	public void setVelY(final float velY) { bounds.setVelY(velY); }
	public void setVelocity(final float velX, final float velY) { bounds.setVelocity(velX, velY); }
	public float getArea() { return bounds.getArea(); }
	public BoundingBox getBounds() { return bounds; }
	public Rectangle getFullBounds() { return bounds.getFullBounds(); }
	public Rectangle getLeftBounds() { return bounds.getLeftBounds(); }
	public Rectangle getRightBounds() { return bounds.getRightBounds(); }
	public Rectangle getTopBounds() { return bounds.getTopBounds(); }
	public Rectangle getBottomBounds() { return bounds.getBottomBounds(); }
	public float getWidth() { return bounds.getFullBounds().getWidth(); }
	public float getHeight() { return bounds.getFullBounds().getHeight(); }
	public float getHalfWidth() { return getWidth() * 0.5f; }
	public float getHalfHeight() { return getHeight() * 0.5f; }
	public void setWidth(final float w) { bounds.setWidth(w); }
	public void setHeight(final float h) { bounds.setHeight(h); }
	public void setSize(final float w, final float h) { bounds.setSize(w, h); }
	public boolean getGrounded() { return bounds.getGrounded(); }
	public boolean getOnSlope() { return bounds.getOnSlope(); }
	public void setGrounded(final boolean b) { bounds.setGrounded(b); }
	public void setOnSlope(final boolean b) { bounds.setOnSlope(b); }
	public void dontApplyGravity() { bounds.setDontApplyGravity(true); }
	public boolean getDontApplyGravity() { return bounds.getDontApplyGravity(); }
	public void setDontApplyGravity(final boolean b) { bounds.setDontApplyGravity(b); }
	public void toggleDontApplyGravity() { bounds.toggleDontApplyGravity(); }
	public void dontApplyFriction() { bounds.setDontApplyFriction(true); }
	public boolean getDontApplyFriction() { return bounds.getDontApplyFriction(); }
	public void setDontApplyFriction(final boolean b) { bounds.setDontApplyFriction(b); }
	public void toggleDontApplyFriction() { bounds.toggleDontApplyFriction(); }
	public Point2f getLocation() { return bounds.getLocation(); }
	public void setLocation(final float x, final float y) { bounds.setLocation(x, y); }
	public void setLocation(final Point2f p) { bounds.setLocation(p.x, p.y); }
	public void setLocation(final SimplePoint p) { bounds.setLocation(p.x, p.y); }
	public void setX(final float x) { bounds.setX(x); }
	public void setY(final float y) { bounds.setY(y); }
	public float getX() { return bounds.getFullBounds().getX(); }
	public float getY() { return bounds.getFullBounds().getY(); }
	public float getCenterX() { return getX() + getHalfWidth(); }
	public float getCenterY() { return getY() + getHalfHeight(); }
	public Point2f getCenter() { return new Point2f(getX() + getHalfWidth(), getY() + getHalfHeight()); }
	public void setCenter(final Point2f p) { setLocation(p.x - getWidth() / 2, p.y - getHeight() / 2); }
	public void setCenter(final float x, final float y) { setLocation(x - getWidth() / 2, y - getHeight() / 2); }
	public Facing getFacing() { return bounds.getFacing(); }
	public boolean facingLeft() { return bounds.facingLeft(); }
	public boolean facingRight() { return bounds.facingRight(); }
	public void setFacing(final Facing facing) { bounds.setFacing(facing); }
	public Type getType() { return bounds.getType(); }
	
	public PyFunction createPyFunction(String funcName) { return PyUtils.createPyFunction(funcName); }

	public Point2f getTopRight() { return getBounds().getTopRight(); }
	public Point2f getTopLeft() { return getBounds().getTopLeft(); }
	public Point2f getBottomRight() { return getBounds().getBottomRight(); }
	public Point2f getBottomLeft() { return getBounds().getBottomLeft(); }
	public Line getTop() { return getBounds().getTop(); }
	public Line getBottom() { return getBounds().getBottom(); }
	public Line getLeft() { return getBounds().getLeft(); }
	public Line getRight() { return getBounds().getRight(); }

}
