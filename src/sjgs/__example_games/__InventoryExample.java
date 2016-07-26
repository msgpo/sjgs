package sjgs.__example_games;

import static sjgs.graphics.Colors.blue;
import static sjgs.graphics.Colors.orange;
import static sjgs.graphics.Colors.red;
import static sjgs.graphics.Colors.white;
import static sjgs.utils.Utils.rand;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import sjgs.core.Engine;
import sjgs.core.input.Mouse;
import sjgs.graphics.ui.InventorySystem;
import sjgs.graphics.ui.InventorySystemSlot;

/** Welcome to a demonstration of the @InventorySystem class in Mitch's Game Engine! */

class __InventoryExample extends Engine {

	public __InventoryExample(final int WIDTH, final int HEIGHT, final String title) {
		super(WIDTH, HEIGHT, title);
	}

	// Create our engine!
	private static __InventoryExample engine;
	// Create our inventory!
	private static ExampleInventory inventory;

	// Create the window for our engine, and initialize our engine itself while we're here.
	public static void main(final String[] args) {
		engine = new __InventoryExample(800, 600, "Inventory System Demonstration");
	}

	// Create our mouse input, which will also add itself to our engine that we're passing in.
	@Override
	protected void init() {
		new MouseInput(this);
		// Then we initialize our inventory
		inventory = new ExampleInventory(getWidth() * 0.1f, getHeight() * 0.1f, getWidth() * 0.8f, getHeight() * 0.8f);
	}

	// In our main tick method, we make sure to tick our new inventory
	@Override
	protected void tick() { inventory.tick(); }

	// Like wise in our main render method
	@Override
	protected void render(final Graphics2D g2d) { inventory.render(g2d); }

	// Lets create an interface full of itemId's for our inventory --- Note that '0' is reserved for empty slots
	private static interface items { static final int CIRCLE = 1, SQUARE = 2, TRIANGLE = 3; }

	// INVENTORY SLOT CLASS!
	private static class ExampleInventorySlot extends InventorySystemSlot implements items {
		public ExampleInventorySlot(final float x, final float y, final float w, final float h, final int slotNumber) { super(x, y, w, h, slotNumber); }

		@Override
		public void init() {}
		// Here we set the center of the slot to our mouse if we have clicked on it
		@Override
		public void tick() {
			if(mouseClicked) bounds.setLocation(Mouse.getX() - bounds.getHalfWidth(), Mouse.getY() - bounds.getHalfHeight());
		}

		// Render our slots!
		@Override
		public void render(final Graphics2D g2d) {
			switch(itemId) {
			case EMPTY: break;
			case CIRCLE: g2d.setColor(orange); g2d.fillOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight()); break;
			case SQUARE: g2d.setColor(red); bounds.drawFilled(g2d); break;
			case TRIANGLE: g2d.setColor(blue);  bounds.toTriangle().draw(g2d); break;
			}

			// Here I draw the starting locations of the slots for clarity during the demonstration
			g2d.setColor(Color.white);
			g2d.drawRect((int)getStartingX(), (int)getStartingY(), (int)getWidth(), (int)getHeight());

			// Here is where you would draw quantities, but for this demonstration all quantities are zero
			if(quantity > 1)
				g2d.drawString(""+quantity, getX(), getY() + getHalfHeight());
		}

		// Python methods in the superclass require this "baseReset".
		// More advanced inventory systems may require you to write your own reset methods however.
		@Override
		public void reset() { baseReset(); }
	}

	// INVENTORY CLASS!
	private static class ExampleInventory extends InventorySystem implements items {
		public ExampleInventory(final float x, final float y, final float width, final float height) { super(x, y, width, height); }

		// Here we create all our inventory slots!
		@Override
		public void init() {
			final int w = 50, h = 50;
			for(int i = 1; i < 6; i++) slots.add(new ExampleInventorySlot(engine.getWidth() * 0.3f, engine.getHeight()*0.1f + i*h, w, h, i - 1));
			for(int i = 1; i < 6; i++) slots.add(new ExampleInventorySlot(engine.getWidth() * 0.6f, engine.getHeight()*0.1f + i*h, w, h, i - 1));

			// And fill them with random items
			for(int i = 0; i < 10; i++) addItem(rand.nextInt(4));
		}

		// Tick all our slots
		@Override
		public void tick() { for(final InventorySystemSlot i : slots) i.tick(); }

		// Render all our slots, and draw the main inventory's bounds
		@Override
		public void render(final Graphics2D g2d) {
			g2d.setColor(white);
			getBounds().draw(g2d);

			for(final InventorySystemSlot i : slots) i.render(g2d);
			/* pro tip: running through again here like so will put the held item always above the others */
			for(final InventorySystemSlot i : slots) if(i.mouseClicked) { i.render(g2d); break; }
		}

		@Override
		public void destroy() {}
		// Here you can write whatever logic you want to happen on clicks, however for now we use the base, standard click method.
		@Override
		public void onLeftClick() { baseOnLeftClick(); }
		@Override
		public boolean isStackable(final int itemId) { return false; }
		@Override
		public int getStackableAmount(final int itemId) { return 0; }

		@Override
		public void swapSlots(final InventorySystemSlot i, final InventorySystemSlot e) {
			baseSwapSlots(i, e);
		}


	}

	// Basic mouse input class so we have input
	private static class MouseInput extends Mouse {
		public MouseInput(final Engine engine) { super(engine); }
		@Override
		public void mouseMoved(final MouseEvent e) {
			setLocation(e.getX(), e.getY());
		}
		@Override
		public void mouseClicked(final MouseEvent e) {
			setClickLocation(e.getX(), e.getY());
			if(e.getButton() == 1) inventory.onLeftClick();
		}
		@Override
		public void mouseWheelMoved(final MouseWheelEvent arg0) {}
		@Override
		public void mousePressed(final MouseEvent e) {}
		@Override
		public void mouseReleased(final MouseEvent e) {}
	}

	// And that's it! Very easy, simple implementation of an inventory class :)
}
