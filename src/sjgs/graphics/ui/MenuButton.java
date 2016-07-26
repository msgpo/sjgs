package sjgs.graphics.ui;

import java.awt.Graphics2D;
import sjgs.core.input.Mouse;
import sjgs.graphics.Animation;
import sjgs.utils.data_structures.shapes.Rectangle;

/** @class: USAGE - Create a Menu class to hold a stack of your menu buttons, along
 * 		    with any other functions you deem to need. Then, in your also created MouseInput class,
 * 			(which should be extending Mouse...), loop through all your menu buttons on each click.
 *
 * 			Render your list of menu buttons in your Engine's render method.  					 */
public abstract class MenuButton {

	private final Rectangle bounds;
	public final Animation animation;

	public MenuButton(final float x, final float y, final float w, final float h, final Animation animation) {
		bounds = new Rectangle(x, y, w, h);
		this.animation = animation;
	}

	public MenuButton(final float x, final float y, final float w, final float h) {
		bounds = new Rectangle(x, y, w, h);
		animation = null;
	}

	public abstract void tick();
	public abstract void render(Graphics2D g2d);
	public abstract void onClick();
	public abstract void onHover();

	public float getX() { return bounds.getX(); }
	public float getY() { return bounds.getY(); }
	public float getWidth() { return bounds.getWidth(); }
	public float getHeight() { return bounds.getHeight(); }
	public Rectangle getBounds() { return bounds; }

	public boolean containsMouse() { return bounds.contains(Mouse.getX(), Mouse.getY()); }
}
