
package sjgs.base_objects;

import java.awt.Graphics2D;
import java.io.Serializable;
import sjgs.core.Handler;
import sjgs.enums.Type;

public abstract class SoftObject extends GameObject implements Serializable  {

	public final boolean mobile;

	public SoftObject(final float x, final float y, final float w, final float h, final boolean mobile) {
		super(x, y, w, h, Type.SOFT_OBJECT);
		this.mobile = mobile;
		if(mobile) Handler.mobile_soft_objects.add(this);
		else Handler.stationary_soft_objects.insert(this);
	}

	@Override
	protected abstract void init();
	@Override
	public abstract void tick();
	@Override
	public abstract void render(Graphics2D g2d);
	@Override
	protected abstract void destroy();

	@Override
	protected void removeFromHandler() {
		if(mobile) Handler.mobile_soft_objects.remove(this);
		else Handler.stationary_soft_objects.remove(this);
	}

}
