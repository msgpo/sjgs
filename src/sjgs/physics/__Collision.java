package sjgs.physics;

import sjgs.base_objects.GameObject;
import sjgs.core.Handler;
import sjgs.enums.Type;
import sjgs.physics.structs.CollisionResponse;
import sjgs.utils.Utils;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.shapes.Rectangle;

class __Collision {

	/** @class Collision:
	 *
	 *  GOALS:
	 *   1:  Find all objects that are near the given bounds by searching through the
	 *       Handler's quad trees.
	 *   2:  Find all objects that actually intersect with said bounds, after narrowing down the search list, (1).
	 *   3:  Provide default collision methods for use with this collision response, that can be used by the
	 *   	 user if he or she chooses. If not, the user can implement his or her own collision methods, using
	 *       the collection of objects that has been found to be collided.
	 */


	/** @param bounds: the bounding box being passed in from the object
	 * 	@param args:   The different kinds of objects that hard collision SHOULD be
	 * 				   ran against.                                              */
	public static CollisionResponse collision(final GameObject obj, final Type ...args) {
		final CollisionResponse all_collided_objects = getCollidedObjects(obj);

		final Stack<Type> types = new Stack<Type>();
		for(final Type i : args) types.push(i);

		if(types.size() > 0) {
			if(types.contains(Type.HARD_OBJECT)) __GeneralCollision.default_hard_collision_against_hard_objects(obj, all_collided_objects.collided_hard_objects);
			if(types.contains(Type.MOB)) __GeneralCollision.default_hard_collision_against_mobs(obj, all_collided_objects.collided_mobs);
			if(types.contains(Type.SOFT_OBJECT)) __GeneralCollision.default_hard_collision_against_soft_objects(obj, all_collided_objects.collided_soft_objects);
			if(types.contains(Type.BULLET)) __GeneralCollision.default_hard_collision_against_bullets(obj, all_collided_objects.collided_bullets);
			if(types.contains(Type.PLAYER)) __GeneralCollision.default_hard_collision_against_players(obj, all_collided_objects.collided_players);
		} else __GeneralCollision.default_hard_collision_against_hard_objects(obj, all_collided_objects.collided_hard_objects);

		return all_collided_objects;
	}


	/** @getCollidedObjects: searches through the quad trees to find all objects around the bound's area.
	 * 						 Then creates a collision response containing stacks of all objects that do
	 * 						 indeed intersect with the bounding box.                                   */
	static CollisionResponse getCollidedObjects(final GameObject obj) {
		/** @param OFFSET_MULTIPLIER:  small mult here helps the quad tree search, fixes rare bugs */
		final float OFFSET_MULTIPLIER = 1.25f;
		/** @param OFFSET: Restricting the tree offset here as to prevent very small objects from not getting their collided objects */
		final float OFFSET = Utils.restrict(Utils.biggest(obj.getWidth(), obj.getHeight()) * OFFSET_MULTIPLIER, 5f);
		final float x = obj.getCenterX();
		final float y = obj.getCenterY();

		final Stack<GameObject> hard_objects = new Stack<>();
		final Stack<GameObject> soft_objects = new Stack<>();
		final Stack<GameObject> bullets = new Stack<>();
		final Stack<GameObject> mobs = new Stack<>();

		final Rectangle r = obj.getFullBounds();

		for(final GameObject g : Handler.mobile_hard_objects) if(r.intersects(g.getFullBounds())) hard_objects.push(g);
		hard_objects.combine(Handler.stationary_hard_objects.search(x + -OFFSET, y + -OFFSET, x + OFFSET, y + OFFSET));

		for(final GameObject g : Handler.mobile_soft_objects) if(r.intersects(g.getFullBounds())) soft_objects.push(g);
		soft_objects.combine(Handler.stationary_soft_objects.search(x + -OFFSET, y + -OFFSET, x + OFFSET, y + OFFSET));

		for(final GameObject g : Handler.bullets) if(r.intersects(g.getFullBounds())) bullets.push(g);
		for(final GameObject g : Handler.mobs) if(r.intersects(g.getFullBounds())) mobs.push(g);

		return new CollisionResponse(obj, hard_objects, mobs, bullets, soft_objects);
	}

}
