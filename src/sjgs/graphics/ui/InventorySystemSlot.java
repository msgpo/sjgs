package sjgs.graphics.ui;

import java.awt.Graphics2D;
import sjgs.utils.data_structures.shapes.Rectangle;
import sjgs.utils.data_structures.vectors.Point2f;

public abstract class InventorySystemSlot {

	public static final int EMPTY = 0;

	public final Point2f startingPoint;
	public final Rectangle bounds;
	public boolean mouseClicked;
	public int itemId, quantity, slotNumber;


	public InventorySystemSlot(final float x, final float y, final float w, final float h, final int slotNumber) {
		bounds = new Rectangle(x, y, w, h);
		this.slotNumber = slotNumber;
		startingPoint = new Point2f(x, y);
		init();
	}

	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics2D g2d);
	public abstract void reset();

	protected void baseReset() {
		itemId = quantity = 0;
		bounds.setX(startingPoint.x);
		bounds.setY(startingPoint.y);
	}

	public void resetPosition() {
		bounds.setX(startingPoint.x);
		bounds.setY(startingPoint.y);
	}

	public void setStartingPoint(final Point2f p) { startingPoint.setLocation(p.getX(), p.getY()); }
	public void setStartingPoint(final float x, final float y) { startingPoint.setLocation(x, y); }
	public float getStartingX() { return startingPoint.getX(); }
	public float getStartingY() { return startingPoint.getY(); }

	public float getX() { return bounds.getX(); }
	public float getY() { return bounds.getY(); }
	public float getWidth() { return bounds.getWidth(); }
	public float getHeight() { return bounds.getHeight(); }
	public float getHalfHeight() { return getHeight() * 0.5f; }
	public float getHalfWidth() { return getWidth() * 0.5f; }

	public boolean isEmpty() { return itemId == 0 && quantity == 0; }

	public void setLocation(final float x, final float y) { bounds.setLocation(x, y); }
}