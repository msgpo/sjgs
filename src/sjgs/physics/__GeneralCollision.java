package sjgs.physics;

import static sjgs.utils.Utils.calcMostIntersecting;
import static sjgs.utils.Utils.cinch;
import static sjgs.utils.Utils.restrict;
import sjgs.base_objects.BaseTile;
import sjgs.base_objects.Bullet;
import sjgs.base_objects.GameObject;
import sjgs.base_objects.HardObject;
import sjgs.base_objects.Mob;
import sjgs.base_objects.PlayerBase;
import sjgs.base_objects.SoftObject;
import sjgs.enums.Type;
import sjgs.utils.Utils;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.shapes.Rectangle;
import sjgs.utils.data_structures.vectors.SimplePoint;

class __GeneralCollision {

	private static void calcDefaultHardCollision(final GameObject obj, final GameObject g) {

		if(obj.getType() == Type.BULLET) { __BulletPhysics.calcBulletCollision((Bullet)obj, g); return; }

		final float velX = obj.getVelX();
		final float velY = obj.getVelY();

		switch(Utils.intDirection(g.getCenter(), obj.getCenter())) {
		case 1: obj.setVelY(cinch(velY, 0)); obj.setY(g.getY() - obj.getHeight()); break;
		case 3: obj.setVelX(restrict(velX, 0)); obj.setX(g.getX() + g.getWidth() - 1); break; // NOTE THE - 1 is VERY IMPORTANT!
		case 5: obj.setVelY(restrict(velY, 0)); obj.setY(g.getY() + g.getHeight()); break;
		case 7: obj.setVelX(cinch(velX, 0)); obj.setX(g.getX() - obj.getWidth()); break;

		case 2: { final Rectangle r = calcMostIntersecting(g.getFullBounds(), obj.getBottomBounds(), obj.getLeftBounds());
		if(r == obj.getBottomBounds())    { obj.setVelY(cinch(velY, 0)); obj.setY(g.getY() - obj.getHeight()); }
		else if(r == obj.getLeftBounds()) { obj.setVelX(restrict(velX, 0)); obj.setX(g.getX() + g.getWidth()); }
		break; }
		case 4: { final Rectangle r = calcMostIntersecting(g.getFullBounds(), obj.getTopBounds(), obj.getLeftBounds());
		if(r == obj.getTopBounds())       { obj.setVelY(restrict(velY, 0)); obj.setY(g.getY() + g.getHeight()); }
		else if(r == obj.getLeftBounds()) { obj.setVelX(restrict(velX, 0)); obj.setX(g.getX() + g.getWidth()); }
		break; }
		case 6: { final Rectangle r = calcMostIntersecting(g.getFullBounds(), obj.getTopBounds(), obj.getRightBounds());
		if(r == obj.getTopBounds())        { obj.setVelY(restrict(velY, 0)); obj.setY(g.getY() + g.getHeight()); }
		else if(r == obj.getRightBounds()) { obj.setVelX(cinch(velX, 0)); obj.setX(g.getX() - obj.getWidth()); }
		break; }
		case 8: { final Rectangle r = calcMostIntersecting(g.getFullBounds(), obj.getBottomBounds(), obj.getRightBounds());
		if(r == obj.getBottomBounds())     { obj.setVelY(cinch(velY, 0)); obj.setY(g.getY() - obj.getHeight()); }
		else if(r == obj.getRightBounds()) { obj.setVelX(cinch(velX, 0)); obj.setX(g.getX() - obj.getWidth()); }
		break; }
		}
	}

	//--------------------------------- TILE COLLISION --------------------------------------------------------//
	static void default_hard_collision_against_hard_objects(final GameObject obj, final Stack<HardObject> collided_hard_objects) {
		final Stack<Rectangle> rects = new Stack<>();

		for(final HardObject ho : collided_hard_objects) {
			if(ho instanceof BaseTile) {
				final BaseTile t = (BaseTile)ho;

				if(t.angled()) {
					for(final SimplePoint p : t.getSlopePoints())
						if(obj.getFullBounds().contains(p))
							obj.setY(p.y - obj.getHeight());
				} else rects.add(ho.getFullBounds());
			}
		}

		final Rectangle mostIntersected = calcMostIntersecting(obj.getFullBounds(), rects);
		for(final HardObject ho : collided_hard_objects) if(ho.getFullBounds() == mostIntersected) calcDefaultHardCollision(obj, ho);
	}
	// --------------------------------- MOB COLLISION ------------------------------------------------------- //
	static void default_hard_collision_against_mobs(final GameObject obj, final Stack<Mob> collided_mobs) {
		for(final Mob m : collided_mobs) { if(m.getBounds() == obj.getBounds()) continue;
		calcDefaultHardCollision(obj, m);
		}
	}
	// -------------------------------- SCENERY COLLISION ---------------------------------------------------- //
	static void default_hard_collision_against_soft_objects(final GameObject obj, final Stack<SoftObject> collided_mobs) {
		for(final SoftObject s : collided_mobs) { if(s.getBounds() == obj.getBounds()) continue;
		calcDefaultHardCollision(obj, s);
		}
	}
	// -------------------------------- BULLET COLLISION ----------------------------------------------------- //
	static void default_hard_collision_against_bullets(final GameObject obj, final Stack<Bullet> collided_bullets) {
		for(final Bullet b : collided_bullets) { if(b.getBounds() == obj.getBounds()) continue;
		calcDefaultHardCollision(obj, b);
		}
	}
	// -------------------------------- PLAYER COLLISION ----------------------------------------------------- //
	static void default_hard_collision_against_players(final GameObject obj, final Stack<PlayerBase> collided_players) {
		for(final PlayerBase p : collided_players) { if(p.getBounds() == obj.getBounds()) continue;
		calcDefaultHardCollision(obj, p);
		}
	}
}
