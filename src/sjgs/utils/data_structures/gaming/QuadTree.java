package sjgs.utils.data_structures.gaming;

import static sjgs.utils.Utils.COIN_FLIP;
import static sjgs.utils.Utils.rand;
import java.io.Serializable;
import sjgs.base_objects.GameObject;
import sjgs.utils.Utils;
import sjgs.utils.data_structures.Stack;

/** @class: NOTE: This data structure is ONLY to be used for PURELY STATIC objects.
 * 				  This implementation does NOT have the ability to continually update
 * 				  the objects inside it. It loses the reference to said objects.
 * 				  With this in mind, this will work great for tiles and scenery, however
 * 				  this will *NOT* work for things like bullet collision, etc.        */

public final class QuadTree implements Serializable {

	private final Node root;
	private int size = 0;

	public QuadTree(final float minX, final float minY, final float maxX, final float maxY) {
		root = new Node(minX, minY, maxX - minX, maxY - minY, null);
	}

	// ----------------------------- PUBLIC METHODS ------------------------------------------------------------------ //

	public void insert(final GameObject value) {
		float x = value.getX(), y = value.getY();
		while(contains(x, y)) {
			x = COIN_FLIP() ? x + rand.nextInt(Utils._2147483647) * 0.000_000_000_1f : x - rand.nextInt(Utils._2147483647) * 0.000_000_000_1f;
			y = COIN_FLIP() ? y + rand.nextInt(Utils._2147483647) * 0.000_000_000_1f : y - rand.nextInt(Utils._2147483647) * 0.000_000_000_1f;
		}
		if (x < root.getX() || y < root.getY() || x > root.getX() + root.getWidth() || y > root.getY() + root.getHeight()) Utils.print("Out of bounds : (" + x + ", " + y + ")");
		else if (insert(root, new Data(x, y, value))) size++;
	}

	/** @method remove: returns the success rate */
	public boolean remove(final GameObject g) { return remove(g.getX(), g.getY()) != null; }
	public boolean isEmpty() { return root.getNodeType() == NodeType.EMPTY; }
	public int size() { return size; }

	public void clear() {
		root.nw = root.ne = root.sw = root.se = null;
		root.setNodeType(NodeType.EMPTY);
		root.setData(null); size = 0;
	}

	public Stack<GameObject> search(final float xmin, final float ymin, final float xmax, final float ymax) {
		final Stack<GameObject> stack = new Stack<GameObject>();
		try { navigate(root,(node) -> { final Data pt = node.getData();
		if (!(pt.getX() < xmin || pt.getX() > xmax || pt.getY() < ymin || pt.getY() > ymax)) stack.push(node.getData().getGameObject());
		}, xmin, ymin, xmax, ymax); } catch (final Exception e) { }
		return stack;
	}

	public Stack<GameObject> toStack() {
		final Stack<GameObject> stack = new Stack<GameObject>();
		traverse(root, (node) -> stack.push(node.getData().getGameObject()));
		return stack;
	}

	@Override
	public QuadTree clone() {
		final Stack<GameObject> temp = toStack();
		final QuadTree clone = new QuadTree(root.getX(), root.getY(), root.getWidth(), root.getHeight());
		for(final GameObject g : temp) clone.insert(g);
		return clone;
	}

	// -------------------------------- END PUBLIC METHODS ---------------------------------------------------------------- //

	private GameObject remove(final float x, final float y) {
		final Node node = find(root, x, y);
		if (node != null) {
			final GameObject value = node.getData().getGameObject();
			node.setData(null);
			node.setNodeType(NodeType.EMPTY);
			balance(node);
			size--;
			return value;
		} else  return null;
	}

	private static void navigate(final Node node, final Func func, final float xmin, final float ymin, final float xmax, final float ymax) {
		switch (node.getNodeType()) {
		case LEAF: func.call(node); break;
		case POINTER:
			if (intersects(xmin, ymax, xmax, ymin, node.ne)) navigate(node.ne, func, xmin, ymin, xmax, ymax);
			if (intersects(xmin, ymax, xmax, ymin, node.se)) navigate(node.se, func, xmin, ymin, xmax, ymax);
			if (intersects(xmin, ymax, xmax, ymin, node.sw)) navigate(node.sw, func, xmin, ymin, xmax, ymax);
			if (intersects(xmin, ymax, xmax, ymin, node.nw)) navigate(node.nw, func, xmin, ymin, xmax, ymax); break;
		}
	}

	private GameObject get(final float x, final float y) {
		final Node node = find(root, x, y);
		return node != null ? node.getData().getGameObject() : null;
	}
	private boolean contains(final float x, final float y) { return get(x, y) != null; }

	private static boolean intersects(final float left, final float bottom, final float right, final float top, final Node node) {
		return !(node.getX() > right || node.getX() + node.getWidth() < left || node.getY() > bottom || node.getY() + node.getHeight() < top);
	}

	private static void traverse(final Node node, final Func func) {
		switch (node.getNodeType()) {
		case LEAF: func.call(node); break;

		case POINTER: traverse(node.ne, func);
		traverse(node.se, func);
		traverse(node.sw, func);
		traverse(node.nw, func); break;
		}
	}

	private static Node find(final Node node, final float x, final float y) {
		switch (node.getNodeType()) {
		case LEAF: return node.getData().getX() == x && node.getData().getY() == y ? node : null;

		case POINTER: return find(getQuadrantForData(node, x, y), x, y);
		} return null;
	}

	private boolean insert(final Node parent, final Data data) {
		switch (parent.getNodeType()) {
		case EMPTY: setDataForNode(parent, data); return true;
		case LEAF:
			if (parent.getData().getX() == data.getX() && parent.getData().getY() == data.getY()) {
				setDataForNode(parent, data);
				return false;
			} else {
				split(parent);
				return insert(parent, data);
			}
		default: return insert(getQuadrantForData(parent, data.getX(), data.getY()), data);
		}
	}

	private void split(final Node node) {
		final Data oldData = node.getData();
		node.setData(null);
		node.setNodeType(NodeType.POINTER);

		final float x = node.getX();
		final float y = node.getY();
		final float hw = node.getWidth() / 2;
		final float hh = node.getHeight() / 2;

		node.nw = new Node(x, y, hw, hh, node);
		node.ne = new Node(x + hw, y, hw, hh, node);
		node.sw = new Node(x, y + hh, hw, hh, node);
		node.se = new Node(x + hw, y + hh, hw, hh, node);

		insert(node, oldData);
	}

	private static void balance(final Node node) {
		if(node.getNodeType() == NodeType.POINTER) {
			Node firstLeaf = null;

			if (node.nw.getNodeType() != NodeType.EMPTY) firstLeaf = node.nw; // note here it starts @ nw, calc'ing CLOCKWISE

			if (node.ne.getNodeType() != NodeType.EMPTY && firstLeaf != null) firstLeaf = node.ne;
			else if (node.sw.getNodeType() != NodeType.EMPTY && firstLeaf != null) firstLeaf = node.sw;
			else if (node.se.getNodeType() != NodeType.EMPTY && firstLeaf != null) firstLeaf = node.se;
			else if (firstLeaf == null) {
				node.setNodeType(NodeType.EMPTY);
				node.nw = node.ne = node.sw = node.se = null;
			}
			else if (firstLeaf.getNodeType() != NodeType.POINTER) {
				node.setNodeType(NodeType.LEAF);
				node.nw = node.ne = node.sw = node.se = null;
				node.setData(firstLeaf.getData());
			}
		}

		if (node.getParent() != null) balance(node.getParent());
	}

	private static Node getQuadrantForData(final Node parent, final float x, final float y) {
		final float mx = parent.getX() + parent.getWidth() / 2f;
		final float my = parent.getY() + parent.getHeight() / 2f;
		if (x < mx) return y < my ? parent.nw : parent.sw; else return y < my ? parent.ne : parent.se;
	}

	private static void setDataForNode(final Node node, final Data data) {
		if (node.getNodeType() == NodeType.POINTER) Utils.print("Can not set data for node of type POINTER");
		else { node.setNodeType(NodeType.LEAF); node.setData(data); }
	}

	private static interface Func { void call(Node node); }
	private static enum NodeType { EMPTY, LEAF, POINTER }

	private static class Node implements Serializable {

		private final float x, y, w, h;
		private final Node parent;
		private Data data;
		private NodeType nodetype = NodeType.EMPTY;
		private Node nw, ne, sw, se;

		public Node(final float x, final float y, final float w, final float h, final Node parent) {
			this.x = x; this.y = y; this.w = w; this.h = h;
			this.parent = parent;
		}

		private float getX() { return x; }
		private float getY() { return y; }
		private float getWidth() { return w; }
		private float getHeight() { return h; }
		private Node getParent() { return parent; }
		private void setData(final Data data) { this.data = data; }
		private Data getData() { return data; }
		private void setNodeType(final NodeType nodetype) { this.nodetype = nodetype; }
		private NodeType getNodeType() { return nodetype; }
	}

	private static class Data implements Serializable {
		private final float x, y;
		private final GameObject game_object;

		public Data(final float x, final float y, final GameObject game_object) {
			this.x = x; this.y = y; this.game_object = game_object;
		}

		private float getX() { return x; }
		private float getY() { return y; }
		private GameObject getGameObject() { return game_object; }
	}

}
