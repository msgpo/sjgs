package sjgs.core.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import sjgs.core.Engine;
import sjgs.utils.data_structures.vectors.Point2f;

public abstract class Mouse implements MouseListener, MouseWheelListener, MouseMotionListener{

	protected static final Point2f pos = new Point2f(0, 0);
	protected static final Point2f clickPos = new Point2f(0, 0);
	protected static final Point2f rightClickPos = new Point2f(0,0);

	public Mouse(final Engine engine) {
		engine.addMouseListener(this);
		engine.addMouseWheelListener(this);
		engine.addMouseMotionListener(this);
	}

	@Override
	public abstract void mouseMoved(MouseEvent e);
	@Override
	public abstract void mouseWheelMoved(MouseWheelEvent arg0);
	@Override
	public abstract void mouseClicked(MouseEvent e);
	@Override
	public abstract void mousePressed(MouseEvent e);
	@Override
	public abstract void mouseReleased(MouseEvent e);

	@Override
	public void mouseDragged(final MouseEvent e) { pos.setLocation(e.getX(), e.getY()); }
	@Override
	public void mouseEntered(final MouseEvent e) { pos.setLocation(e.getX(), e.getY()); }
	@Override
	public void mouseExited(final MouseEvent e)  { pos.setLocation(e.getX(), e.getY()); }

	public static float getX() { return pos.x; }
	public static float getY() { return pos.y; }
	public static float getClickX() { return clickPos.x; }
	public static float getClickY() { return clickPos.y; }
	public static float getRightClickX() { return rightClickPos.x; }
	public static float getRightClickY() { return rightClickPos.y; }

	public void setLocation(final int x, final int y) { pos.setLocation(x, y); }
	public void setClickLocation(final int x, final int y) { clickPos.setLocation(x, y); }
	public void setRightClickLocation(final int x, final int y) { rightClickPos.setLocation(x, y); }

	public static Point2f getClickPos() { return clickPos; }
	public static Point2f getRightClickPos() { return rightClickPos; }
	public static Point2f getMousePos() { return pos; }

}
