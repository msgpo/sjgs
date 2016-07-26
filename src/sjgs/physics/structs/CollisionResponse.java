
package sjgs.physics.structs;

import sjgs.base_objects.BaseTile;
import sjgs.base_objects.Bullet;
import sjgs.base_objects.GameObject;
import sjgs.base_objects.HardObject;
import sjgs.base_objects.Mob;
import sjgs.base_objects.PlayerBase;
import sjgs.base_objects.SoftObject;
import sjgs.core.Handler;
import sjgs.enums.Type;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.shapes.Rectangle;

public final class CollisionResponse {

	public Stack<HardObject> collided_hard_objects;
	public Stack<SoftObject> collided_soft_objects;
	public Stack<Mob> collided_mobs;
	public Stack<Bullet> collided_bullets;
	public Stack<PlayerBase> collided_players;

	public CollisionResponse(final GameObject obj, Stack<GameObject> hard_objects, Stack<GameObject> mobs, Stack<GameObject> bullets, Stack<GameObject> soft_objects) {

		try {
			// CREATE STACKS
			collided_hard_objects = new Stack<HardObject>();
			collided_mobs = new Stack<Mob>();
			collided_bullets = new Stack<Bullet>();
			collided_soft_objects = new Stack<SoftObject>();
			collided_players = new Stack<PlayerBase>();
			Stack<HardObject> tempHardObjects = new Stack<HardObject>();
			Stack<Mob> tempMobs = new Stack<Mob>();
			Stack<Bullet> tempBullets = new Stack<Bullet>();
			Stack<SoftObject> tempSoftObjects = new Stack<SoftObject>();
			Stack<PlayerBase> tempPlayers = new Stack<PlayerBase>();

			// CONVERT GENERICS TO USEABLE OBJECTS
			for(final GameObject g : hard_objects) tempHardObjects.push((HardObject)g);
			for(final GameObject g : mobs) tempMobs.push((Mob)g);
			for(final GameObject g : bullets) tempBullets.push((Bullet)g);
			for(final GameObject g : soft_objects) tempSoftObjects.push((SoftObject)g);
			for(final GameObject g : Handler.players) tempPlayers.push((PlayerBase)g);

			// REFINE SEARCHED STACKS FOR COLLISION
			final Rectangle r = obj.getFullBounds();

			// NOTE THE != BOUNDS CHECKS IF THE TWO ARE NOT THE SAME ENTITY
			for(final HardObject i : tempHardObjects)     if(i.getBounds() != obj.getBounds() && r.intersects(i.getFullBounds()))  collided_hard_objects.push(i);
			for(final Mob i : tempMobs)             if(i.getBounds() != obj.getBounds() && r.intersects(i.getFullBounds()))  collided_mobs.push(i);
			for(final Bullet i : tempBullets)       if(i.getBounds() != obj.getBounds() && r.intersects(i.getFullBounds()))  collided_bullets.push(i);
			for(final SoftObject i : tempSoftObjects)   if(i.getBounds() != obj.getBounds() && r.intersects(i.getFullBounds()))  collided_soft_objects.push(i);
			for(final PlayerBase i : tempPlayers)   if(i.getBounds() != obj.getBounds() && r.intersects(i.getFullBounds()))  collided_players.push(i);

			// NULL JUNK FOR MEMORY
			hard_objects.clear(); mobs.clear(); bullets.clear(); soft_objects.clear();
			hard_objects = mobs = bullets = soft_objects = null;
			tempHardObjects.clear(); tempMobs.clear(); tempBullets.clear(); tempSoftObjects.clear(); tempPlayers.clear();
			tempHardObjects = null; tempMobs = null; tempBullets = null; tempSoftObjects = null; tempPlayers = null;
		} catch (final Exception e) {
			/* nulls / class casts / concurrent mods...
			 * all kinds of shenanigans happen when saving / loading.
			 * Just catch them.									*/
			e.printStackTrace();
		}

	}

	/** @method isEmpty: return true if all the stacks pertaining to the args are null or empty.
	 * 					 If no args were passed, assume all stacks are meant to be checked */
	public boolean isEmpty(final Type ...args) {
		final boolean hards = collided_hard_objects == null || collided_hard_objects.isEmpty();
		final boolean softs = collided_soft_objects == null || collided_soft_objects.isEmpty();
		final boolean mobs = collided_mobs == null || collided_mobs.isEmpty();
		final boolean bullets = collided_bullets == null || collided_bullets.isEmpty();
		final boolean players = collided_players == null || collided_players.isEmpty();

		if(args.length == 0) return hards == softs == mobs == mobs == bullets == players;

		final Stack<Boolean> bools = new Stack<Boolean>();

		for(final Type t : args) {
			if(t == Type.HARD_OBJECT) bools.push(hards);
			else if(t == Type.SOFT_OBJECT) bools.push(softs);
			else if(t == Type.MOB) 		   bools.push(mobs);
			else if(t == Type.BULLET)	   bools.push(bullets);
			else if(t == Type.PLAYER) 	   bools.push(players);
		}

		for(final Boolean b : bools) if(b != true) return false;

		return true;
	}

	public void discard() {
		collided_hard_objects.clear();
		collided_mobs.clear();
		collided_bullets.clear();
		collided_soft_objects.clear();
		collided_players.clear();

		collided_hard_objects = null;
		collided_mobs = null;
		collided_bullets = null;
		collided_soft_objects = null;
		collided_players = null;
	}

	public Stack<BaseTile> getTiles() {
		final Stack<BaseTile> tiles = new Stack<BaseTile>();
		for(final HardObject o : collided_hard_objects) if(o instanceof BaseTile) tiles.push((BaseTile)o);
		return tiles;
	}

}
