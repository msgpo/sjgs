package sjgs.physics;

import static sjgs.utils.Utils.sqrtOf2;
import sjgs.base_objects.GameObject;

class __UpdatePosition {

	private static int step = 1;

	static void updatePosition(final GameObject obj) {

		float tempVelX = obj.getVelX();
		float tempVelY = obj.getVelY();

		// ADJUST FOR SMOOTH, EVEN DIAGONAL MOVEMENT, (fast pythagorous method)
		if(obj.getVelX() == obj.getVelY() && obj.getVelX() != 0 && obj.getVelY() != 0) {
			tempVelX /= sqrtOf2;
			tempVelY /= sqrtOf2;
		}

		switch(Physics.getTickRate()) {
		case _60:  obj.setLocation(obj.getX() + tempVelX, obj.getY() + tempVelY);
		break;
		case _120: obj.setLocation(obj.getX() + tempVelX*0.5f, obj.getY() + tempVelY*0.5f);
		break;
		}

	}
}
