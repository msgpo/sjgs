package sjgs.graphics.ui;

import static sjgs.core.jython.Jython.pi;
import static sjgs.utils.pyutils.PyUtils.One;
import static sjgs.utils.pyutils.PyUtils.True;
import static sjgs.utils.pyutils.PyUtils.int2py;
import static sjgs.utils.pyutils.PyUtils.java2py;
import java.awt.Graphics2D;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.shapes.Rectangle;

public abstract class InventorySystem {

	protected final Rectangle INVENTORY_BOUNDS;
	protected Stack<InventorySystemSlot> slots;
	protected final PyObject self;

	public InventorySystem(final float x, final float y, final float width, final float height) {
		INVENTORY_BOUNDS = new Rectangle(x, y, width, height);
		slots = new Stack<InventorySystemSlot>();
		self = java2py(this);
		init();
	}

	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics2D g2d);
	public abstract void destroy();
	public abstract void onLeftClick(); // NOTE: you must put 'baseOnLeftClick()' in this method!
	public abstract void swapSlots(final InventorySystemSlot i, final InventorySystemSlot e);
	public abstract boolean isStackable(int itemId);
	public abstract int getStackableAmount(int itemId);

	public void addItem(final int itemId, final int quantity) { addItem.__call__(int2py(itemId), int2py(quantity), self); }
	public void addItem(final int itemId) { addItem.__call__(int2py(itemId), One, self); }
	public void removeItemWithSlotNumber(final int itemId, final int quantity, final int slotNumber) {
		removeItemWithSlotNumber.__call__(int2py(itemId), int2py(quantity), int2py(slotNumber), self);
	}
	public void removeItem(final int itemId, final int quantity) { removeItem.__call__(int2py(itemId), int2py(quantity), self); }
	public void removeItem(final int itemId) {  removeItem.__call__(int2py(itemId), One, self); }

	public void resetPositions() { for(final InventorySystemSlot i : slots) if(!i.mouseClicked) i.resetPosition(); }


	// swaps the values from this slot to the other slot and vice versa
	public void baseSwapSlots(final InventorySystemSlot i, final InventorySystemSlot e) {
		final int     tempId         = i.itemId;
		final int     tempQ          = i.quantity;
		i.itemId    				 = e.itemId;
		i.quantity   				 = e.quantity;
		e.itemId     				 = tempId;
		e.quantity   				 = tempQ;
		e.mouseClicked = false;
		if(i.isEmpty()) i.mouseClicked = false;
		resetPositions();
	}

	// NOTE YOU MUST USE THIS!
	public void baseOnLeftClick() { baseOnLeftClick.__call__(self); }

	public void clearInventory() { for (final InventorySystemSlot i : slots) i.reset(); }
	public void resetMouseClicked() { for (final InventorySystemSlot i : slots) i.mouseClicked = false; }

	public boolean isFull(final int itemId, final int quantity) { return isFull.__call__(int2py(itemId), int2py(quantity), self) == True; }
	public boolean contains(final int itemId, final int quantity) { return contains.__call__(int2py(itemId), int2py(quantity), self) == True; }

	public Stack<InventorySystemSlot> getSlots() { return slots; }
	public float getWidth() { return INVENTORY_BOUNDS.getWidth(); }
	public float getHeight() { return INVENTORY_BOUNDS.getHeight(); }
	public float getX() { return INVENTORY_BOUNDS.getX(); }
	public float getY() { return INVENTORY_BOUNDS.getY(); }
	public Rectangle getBounds() { return INVENTORY_BOUNDS; }

	//=======================================================================================================//
	private static PyFunction isFull, contains, baseOnLeftClick, addItem, removeItem, removeItemWithSlotNumber;
	public static void __engine_init_pyfuncs() {
		isFull = pi.get("__engine_inventory_is_full", PyFunction.class);
		contains =  pi.get("__engine_inventory_contains", PyFunction.class);
		baseOnLeftClick = pi.get("__engine_onLeftClick", PyFunction.class);
		addItem = pi.get("__engine_add_item", PyFunction.class);
		removeItem = pi.get("__engine_remove_item", PyFunction.class);
		removeItemWithSlotNumber = pi.get("__engine_remove_item_with_slot_number", PyFunction.class);
	}
}
