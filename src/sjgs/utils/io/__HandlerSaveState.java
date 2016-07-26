
package sjgs.utils.io;

import java.io.Serializable;
import sjgs.base_objects.GameObject;
import sjgs.core.Handler;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.gaming.QuadTree;

/** @HandlerSaveState: This is just for ease of use. Isn't entirely necessary.
 * 					   Just zips up all the @Handler data to be passed around.
 * 					   Easy to use! Just two no arg calls, the @constructor and
 * 					   then @method	loadIntoHandler()                         */

class __HandlerSaveState implements Serializable {

	private final QuadTree stationary_hard_objects, stationary_soft_objects;
	private final Stack<GameObject> players, mobs, bullets, mobile_hard_objects, mobile_soft_objects;
	private final int TREE_OFFSET, TREE_BOUNDS;

	public __HandlerSaveState() {
		stationary_hard_objects = Handler.stationary_hard_objects.clone();
		stationary_soft_objects = Handler.stationary_soft_objects.clone();
		players = Handler.players.clone();
		mobs = Handler.mobs.clone();
		bullets = Handler.bullets.clone();
		mobile_hard_objects = Handler.mobile_hard_objects.clone();
		mobile_soft_objects = Handler.mobile_soft_objects.clone();
		TREE_OFFSET = Handler.getTreeOffset();
		TREE_BOUNDS = Handler.getTreeBounds();

	}

	protected boolean loadIntoHandler() {
		try { Handler.clearAll();

		Handler.stationary_hard_objects = stationary_hard_objects;
		Handler.stationary_soft_objects = stationary_soft_objects;
		Handler.players = players;
		Handler.mobs = mobs;
		Handler.bullets = bullets;
		Handler.mobile_hard_objects = mobile_hard_objects;
		Handler.mobile_soft_objects = mobile_soft_objects;
		Handler.setBounds(TREE_BOUNDS);
		Handler.setTreeOffset(TREE_OFFSET);

		return true;

		} catch (final Exception e) { e.printStackTrace(); return false; }
	}
}