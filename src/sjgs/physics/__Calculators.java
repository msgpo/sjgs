package sjgs.physics;

import static sjgs.utils.Utils.cinch;
import sjgs.base_objects.BaseTile;
import sjgs.base_objects.GameObject;
import sjgs.base_objects.HardObject;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.vectors.SimplePoint;

class __Calculators {

	static void calcGroundedAndOnSlope(final GameObject obj, final Stack<HardObject> collided_hard_objects) {
		obj.setGrounded(false); obj.setOnSlope(false);

		for (final HardObject ho : collided_hard_objects) {
			if (ho.getFullBounds().intersects(obj.getBottomBounds())) {
				obj.setGrounded(true);
				if (ho instanceof BaseTile) {
					final BaseTile t = (BaseTile) ho;
					if(t.angled()) {
						obj.setOnSlope(true);
						for(final SimplePoint p : t.getSlopePoints())
							if(obj.getFullBounds().contains(p))
								obj.setY(p.y - obj.getHeight());
					}
				}
			}
		}

		if(obj.getGrounded() && !obj.getOnSlope()) obj.setVelY(cinch(obj.getVelY(), 0));
	}


	//	static float calcMomentum(BoundingBox bounds) {
	//		return (bounds.getV + bounds.getVelY()) / sqrtOf2;
	//	}

}
