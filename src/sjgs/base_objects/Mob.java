
package sjgs.base_objects;

import java.awt.Graphics2D;
import java.io.Serializable;
import sjgs.core.Handler;
import sjgs.enums.Type;

public abstract class Mob extends GameObject implements Serializable {

	protected float health, damage;
	protected boolean attacking;

	public Mob(final float x, final float y, final float w, final float h) {
		super(x, y, w, h, Type.MOB);
		Handler.mobs.add(this);
	}

	@Override
	protected abstract void init();
	@Override
	public abstract void tick();
	@Override
	public abstract void render(Graphics2D g2d);
	@Override
	protected abstract void destroy();

	protected boolean checkIfAlive() { if(health <= 0) destroy(); return health <= 0; }

	@Override
	protected void removeFromHandler() { Handler.mobs.remove(this); }

	public void setAttacking(final boolean attacking) { this.attacking = attacking; }
	public boolean getAttacking() { return attacking; }

	public void decrementHealth(final float decrement) { setHealth(getHalfHeight() - decrement); }
	public void incrementHealth(final float increment) { setHealth(getHealth() + increment); }
	public void setHealth(final float health) { this.health = health; }
	public float getHealth() { return health; }

	public void setDamage(final float damage) { this.damage = damage; }
	public float getDamage() { return damage; }

}
