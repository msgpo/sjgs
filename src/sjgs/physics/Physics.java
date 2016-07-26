package sjgs.physics;

import sjgs.base_objects.GameObject;
import sjgs.base_objects.HardObject;
import sjgs.enums.TickRate;
import sjgs.enums.Type;
import sjgs.physics.structs.CollisionResponse;
import sjgs.utils.data_structures.Stack;

public final class Physics {

	public void updatePosition(final GameObject obj) { __UpdatePosition.updatePosition(obj); }
	public void applyFriction(final GameObject obj, final float friction_decrement) { __Friction.applyFriction(obj, friction_decrement); }
	public CollisionResponse collision(final GameObject obj, final Type ...args) { return __Collision.collision(obj, args); }
	public void applyGravity(final GameObject obj, final float gravity) { __Gravity.applyGravity(obj, gravity); }
	public void calcGroundedAndOnSlope(final GameObject obj, final Stack<HardObject> collided_hard_objects) { __Calculators.calcGroundedAndOnSlope(obj, collided_hard_objects); }

	public CollisionResponse getCollidedObjects(final GameObject obj) { return __Collision.getCollidedObjects(obj); }

	public static void init(final float user_supplied_default_gravity, final float user_supplied_terminal_velocity, final float user_supplied_default_friction) {
		__Gravity.setDefaultGravity(user_supplied_default_gravity);
		__Gravity.setTerminalVelocity(user_supplied_terminal_velocity);
		__Friction.setDefaultFriction(user_supplied_default_friction);
	}

	public void init() {
		final float default_gravity = 0.75f, default_terminal_velocity = 10f, default_friction = 0.1f;
		__Gravity.setDefaultGravity(default_gravity);
		__Gravity.setTerminalVelocity(default_terminal_velocity);
		__Friction.setDefaultFriction(default_friction);
		TPS = TickRate._60;
	}

	private static TickRate TPS;

	static TickRate getTickRate() { return TPS; }
	public void setDoubleTickRate() { TPS = TickRate._120; }

	public static void setUpHillFrictionMultiplier(final float f) { __Friction.setOnSlopeFrictionMultiplier(f); }
	public static void disableGravity() { __Gravity.disableGravity(); }
	public static void enableGravity()  { __Gravity.enableGravity(); }
	public static void toggleGravity()  { __Gravity.toggleGravity(); }

	public static void disableFriction() { __Friction.disableFriction(); }
	public static void enableFriction()  { __Friction.enableFriction(); }
	public static void toggleFriction()  { __Friction.toggleFriction(); }

	public static float getTerminalVelocity() { return __Gravity.getTerminalVelocity(); }
	public static float getDefaultGravity() { return __Gravity.getDefaultGravity(); }
	public static float getDefaultFriction() { return __Friction.getDefaultFriction(); }

}
