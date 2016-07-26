package sjgs.base_objects;

import java.awt.Graphics2D;
import java.io.Serializable;
import sjgs.core.Handler;
import sjgs.enums.Type;
import sjgs.utils.tools.Timer;

public abstract class Bullet extends GameObject implements Serializable {

	public static final float MAX_ELASTICITY = 100, NO_ELASTICITY = 0;

	protected final Timer timer;
	/** @elasticity: A percent value, (e.g. from 0.01 to 1.00), that is a multiplier for the amount
	 * 			     of energy retained after a collision.										 */
	protected float elasticity;

	public Bullet(final float x, final float y, final float w, final float h, final float velX, final float velY, final int elasticity, final int health_timer) {
		super(x, y, w, h, Type.BULLET);
		setVelX(velX);
		setVelY(velY);
		this.elasticity = elasticity / 100f;
		timer = new Timer(health_timer);
		Handler.bullets.add(this);
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
	protected void removeFromHandler() { Handler.bullets.remove(this); }

	public float getElasticity() { return elasticity; }
	public void setElasticity(final float elasticity) { this.elasticity = elasticity; }

}
