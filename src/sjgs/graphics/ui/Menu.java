package sjgs.graphics.ui;

import java.awt.Graphics2D;
import sjgs.utils.data_structures.Stack;

public abstract class Menu {

	public Stack<MenuButton> buttons;

	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics2D g2d);
	public abstract void destroy();

}
