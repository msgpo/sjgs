package sjgs.physics;

import static sjgs.utils.Utils.cinch;
import sjgs.base_objects.GameObject;

class __Gravity {

	private static boolean GRAVITY_DISABLED;
	private static float DEFAULT_GRAVITY, TERMINAL_VELOCITY;

	static void applyGravity(final GameObject obj, float gravity) {
		if(GRAVITY_DISABLED || obj.getDontApplyGravity()) return;

		if(obj.useCustomGravity()) gravity = obj.getCustomGravity();

		switch(Physics.getTickRate()) {
		case _60: break;
		case _120: gravity *= 0.5f; break;
		}

		obj.setVelY(cinch(obj.getVelY() + gravity, TERMINAL_VELOCITY));
	}

	static float getTerminalVelocity()       { return TERMINAL_VELOCITY; }
	static float getDefaultGravity()         { return DEFAULT_GRAVITY; }
	static void setDefaultGravity(final float g)   { DEFAULT_GRAVITY = g; }
	static void setTerminalVelocity(final float t) { TERMINAL_VELOCITY = t; }

	static void disableGravity() { GRAVITY_DISABLED = true; }
	static void enableGravity()  { GRAVITY_DISABLED = false; }
	static void toggleGravity()  { GRAVITY_DISABLED = !GRAVITY_DISABLED; }

}
