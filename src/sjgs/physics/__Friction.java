package sjgs.physics;

import static sjgs.utils.Utils.abs;
import sjgs.base_objects.GameObject;

class __Friction {

	private static boolean apply_friction = true;

	static float onSlopeFrictionMultiplier = 2f;

	private static float DEFAULT_FRICTION;

	static void applyFriction(final GameObject obj) { applyFriction(obj, DEFAULT_FRICTION); }

	public static void applyFriction(final GameObject obj, final float friction_decrement) {
		if(!apply_friction || obj.getDontApplyFriction()) return;

		if(obj.getGrounded()) runFriction(obj, friction_decrement);

		// run it again if going uphill, 3x friction
		if(obj.getOnSlope() && obj.getGrounded()) {
			for(int i = 0 ; i < onSlopeFrictionMultiplier; i++)
				runFriction(obj, friction_decrement);
		}
	}

	private static void runFriction(final GameObject obj, float friction_decrement) {

		switch(Physics.getTickRate()) {
		case _60: break;
		case _120: friction_decrement *= 0.5f; break;
		}

		if(obj.getVelX() != 0 && abs(obj.getVelX()) > friction_decrement)
			obj.setVelX(obj.getVelX() > 0 ? obj.getVelX() - friction_decrement : obj.getVelX() + friction_decrement);
		else obj.setVelX(0);
		if(obj.getVelY() != 0 && abs(obj.getVelY()) > friction_decrement)
			obj.setVelY(obj.getVelY() > 0 ? obj.getVelY() - friction_decrement : obj.getVelY() + friction_decrement);
		else obj.setVelY(0);
	}

	static float getDefaultFriction() { return DEFAULT_FRICTION; }
	static void setDefaultFriction(final float f) { DEFAULT_FRICTION = f; }
	static void setOnSlopeFrictionMultiplier(final float f) { onSlopeFrictionMultiplier = f; }

	static void disableFriction() { apply_friction = false; }
	static void enableFriction() { apply_friction = true; }
	static void toggleFriction() { apply_friction = !apply_friction; }
	static boolean getDontApplyFriction() { return !apply_friction; }
	static void setDontApplyFriction(final boolean b) { apply_friction = !b; }


}
