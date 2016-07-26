package sjgs.physics;

import static sjgs.utils.Utils.calcMostIntersecting;
import sjgs.base_objects.Bullet;
import sjgs.base_objects.GameObject;
import sjgs.utils.Utils;
import sjgs.utils.data_structures.shapes.Rectangle;

class __BulletPhysics {

	static void calcBulletCollision(final Bullet obj, final GameObject g) {

		final float velX = obj.getVelX();
		final float velY = obj.getVelY();
		final float ep = obj.getElasticity();

		switch(Utils.intDirection(g.getCenter(), obj.getCenter())) {
		case 1: obj.setVelY(-velY * ep); obj.setY(g.getY() - obj.getHeight()); break;
		case 3: obj.setVelX(-velX * ep); obj.setX(g.getX() + g.getWidth() - 1); break; // NOTE THE - 1 is VERY IMPORTANT!
		case 5: obj.setVelY(-velY * ep); obj.setY(g.getY() + g.getHeight()); break;
		case 7: obj.setVelX(-velX * ep); obj.setX(g.getX() - obj.getWidth()); break;

		case 2: { final Rectangle r = calcMostIntersecting(g.getFullBounds(), obj.getBottomBounds(), obj.getLeftBounds());
		if(r == obj.getBottomBounds())    { obj.setVelY(-velY * ep); obj.setY(g.getY() - obj.getHeight()); }
		else if(r == obj.getLeftBounds()) { obj.setVelX(-velX * ep); obj.setX(g.getX() + g.getWidth()); }
		break; }
		case 4: { final Rectangle r = calcMostIntersecting(g.getFullBounds(), obj.getTopBounds(), obj.getLeftBounds());
		if(r == obj.getTopBounds())       { obj.setVelY(-velY * ep); obj.setY(g.getY() + g.getHeight()); }
		else if(r == obj.getLeftBounds()) { obj.setVelX(-velX * ep); obj.setX(g.getX() + g.getWidth()); }
		break; }
		case 6: { final Rectangle r = calcMostIntersecting(g.getFullBounds(), obj.getTopBounds(), obj.getRightBounds());
		if(r == obj.getTopBounds())        { obj.setVelY(-velY * ep); obj.setY(g.getY() + g.getHeight()); }
		else if(r == obj.getRightBounds()) { obj.setVelX(-velX * ep); obj.setX(g.getX() - obj.getWidth()); }
		break; }
		case 8: { final Rectangle r = calcMostIntersecting(g.getFullBounds(), obj.getBottomBounds(), obj.getRightBounds());
		if(r == obj.getBottomBounds())     { obj.setVelY(-velY * ep); obj.setY(g.getY() - obj.getHeight()); }
		else if(r == obj.getRightBounds()) { obj.setVelX(-velX * ep); obj.setX(g.getX() - obj.getWidth()); }
		break; }
		}
	}

}
