package sjgs.core;

import java.awt.Graphics2D;
import sjgs.base_objects.GameObject;
import sjgs.base_objects.PlayerBase;
import sjgs.graphics.lighting.Light;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.gaming.QuadTree;

public final class Handler {

	public static QuadTree stationary_hard_objects, stationary_soft_objects;
	public static Stack<GameObject> players, mobs, bullets, mobile_hard_objects, mobile_soft_objects;
	public static Stack<Light> lights;
	private static int TREE_OFFSET, TREE_BOUNDS;

	void init() {
		TREE_OFFSET = 32;
		TREE_BOUNDS = 5000;
		stationary_hard_objects = new QuadTree(-TREE_BOUNDS, -TREE_BOUNDS, TREE_BOUNDS, TREE_BOUNDS);
		stationary_soft_objects = new QuadTree(-TREE_BOUNDS, -TREE_BOUNDS, TREE_BOUNDS, TREE_BOUNDS);

		mobile_hard_objects = new Stack<GameObject>();
		mobile_soft_objects = new Stack<GameObject>();
		mobs = new Stack<GameObject>();
		bullets = new Stack<GameObject>();
		players = new Stack<GameObject>();

		lights = new Stack<Light>();
	}

	/** @param scaleFactor: should be one if not using any scaling */
	public static void tick(final Camera camera, final double scaleFactor) {
		final float minX = -camera.getX() - TREE_OFFSET;
		final float minY = -camera.getY() - TREE_OFFSET;
		final float maxX = (float)(-camera.getX() + camera.getWidth()  / scaleFactor + TREE_OFFSET);
		final float maxY = (float)(-camera.getY() + camera.getHeight() / scaleFactor + TREE_OFFSET);

		try { for(final GameObject g : stationary_hard_objects.search(minX, minY, maxX, maxY)) g.tick(); } catch (final Exception e) { e.printStackTrace(); }
		try { for(final GameObject g : stationary_soft_objects.search(minX, minY, maxX, maxY)) g.tick(); } catch (final Exception e) { e.printStackTrace(); }
		try { for(final GameObject g : mobile_hard_objects) g.tick(); } catch (final Exception e) { e.printStackTrace(); }
		try { for(final GameObject g : mobile_soft_objects) g.tick(); } catch (final Exception e) { e.printStackTrace(); }
		try { for(final GameObject g : bullets) 			  g.tick(); } catch (final Exception e) { e.printStackTrace(); }
		try { for(final GameObject g : mobs) 				  g.tick(); } catch (final Exception e) { e.printStackTrace(); }

		try { for(final GameObject player : players) try { player.tick(); } catch(final Exception e) { e.printStackTrace(); } } catch (final Exception e) { e.printStackTrace(); }
	}

	public static void render(final Graphics2D g2d, final Camera camera, final double scaleFactor) {
		g2d.translate(camera.getX(), camera.getY());

		final float minX = -camera.getX() - TREE_OFFSET;
		final float minY = -camera.getY() - TREE_OFFSET;
		final float maxX = (float)(-camera.getX() + camera.getWidth()  / scaleFactor + TREE_OFFSET);
		final float maxY = (float)(-camera.getY() + camera.getHeight() / scaleFactor + TREE_OFFSET);

		try { for(final GameObject g : stationary_hard_objects.search(minX, minY, maxX, maxY)) try { g.render(g2d); } catch (final Exception e) { e.printStackTrace(); } } catch (final Exception e) { e.printStackTrace(); }
		try { for(final GameObject g : stationary_soft_objects.search(minX, minY, maxX, maxY)) try { g.render(g2d); } catch (final Exception e) { e.printStackTrace(); } } catch (final Exception e) { e.printStackTrace(); }
		for(final GameObject g : mobile_hard_objects) try { g.render(g2d); } catch (final Exception e) { e.printStackTrace(); }
		for(final GameObject g : mobile_soft_objects) try { g.render(g2d); } catch (final Exception e) { e.printStackTrace(); }
		for(final GameObject g : mobs) try { g.render(g2d); } catch (final Exception e) { e.printStackTrace(); }
		for(final GameObject g : bullets) try { g.render(g2d); } catch (final Exception e) { e.printStackTrace(); }

		try { for(final GameObject player : players) try { player.render(g2d); } catch (final Exception e) { e.printStackTrace(); } } catch (final Exception e) { e.printStackTrace(); }
		g2d.translate(-camera.getX(), -camera.getY());
		//		g2d.scale(-scaleFactor, -scaleFactor);
	}

	public static void setBounds(final int _TREE_BOUNDS) {
		TREE_BOUNDS = _TREE_BOUNDS;
		final Stack<GameObject> tempHards = stationary_hard_objects.toStack();
		final Stack<GameObject> tempSofts = stationary_soft_objects.toStack();
		stationary_hard_objects.clear(); stationary_soft_objects.clear();
		stationary_hard_objects = new QuadTree(-TREE_BOUNDS, -TREE_BOUNDS, TREE_BOUNDS, TREE_BOUNDS);
		stationary_soft_objects = new QuadTree(-TREE_BOUNDS, -TREE_BOUNDS, TREE_BOUNDS, TREE_BOUNDS);
		for(final GameObject h : tempHards) stationary_hard_objects.insert(h);
		for(final GameObject s : tempSofts) stationary_soft_objects.insert(s);
	}

	public static void setTreeOffset(final int _TREE_OFFSET) { TREE_OFFSET = _TREE_OFFSET; }
	public static int getTreeOffset() { return TREE_OFFSET; }
	public static int getTreeBounds() { return TREE_BOUNDS; }
	public static void addPlayer(final PlayerBase PLAYER) { players.push(PLAYER); }
	public static void removePlayer(final PlayerBase PLAYER) { players.remove(PLAYER); }
	public static void clearPlayers() 	  { if(players != null) players.clear(); }
	public static void clearBullets() 	  { if(bullets != null) bullets.clear(); }
	public static void clearMobs() 		  { if(mobs != null) mobs.clear(); }
	public static void clearSoftObjects() { if(stationary_soft_objects != null) stationary_soft_objects.clear(); if(mobile_soft_objects != null) mobile_soft_objects.clear(); }
	public static void clearHardObjects() { if(stationary_hard_objects != null) stationary_hard_objects.clear(); if(mobile_hard_objects != null) mobile_hard_objects.clear(); }
	public static void clearAll() {
		clearPlayers();
		clearBullets();
		clearMobs();
		clearSoftObjects();
		clearHardObjects();
	}

}