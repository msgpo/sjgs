package sjgs.base_objects;

import java.awt.Graphics2D;
import java.io.Serializable;
import sjgs.core.Handler;
import sjgs.enums.Type;

public abstract class PlayerBase extends GameObject implements Serializable {

	protected float health;
	protected boolean jumping, attacking, lookingUp, lookingDown;

	public PlayerBase(final float x, final float y, final float w, final float h) {
		super(x, y, w, h, Type.PLAYER);
		Handler.addPlayer(this);
	}

	@Override
	protected abstract void init();
	@Override
	public abstract void tick();
	@Override
	public abstract void render(Graphics2D g2d);
	@Override
	protected abstract void destroy();

	/** @manageJumping: Sets player to not be able to jump if the player is falling while not
	 * 					grounded, or has already jumped. Make sure you set the player's jumping variable to
	 * 					be true when he/she jumps in your keyinput class.                                */
	protected void manageJumping() {
		if(jumping && getBounds().getGrounded() || getBounds().getOnSlope()) jumping = false;
	}

	@Override
	protected void removeFromHandler() { Handler.removePlayer(this); }

	public void setLookingUp(final boolean b) { lookingUp = b; }
	public boolean getLookingUp() { return lookingUp; }
	public void setLookingDown(final boolean b) { lookingDown = b; }
	public boolean getLookingDown() { return lookingDown; }
	public void setJumping(final boolean jumping) { this.jumping = jumping; }
	public boolean getJumping() { return jumping; }
	public void setAttacking(final boolean attacking) { this.attacking = attacking; }
	public boolean getAttacking() { return attacking; }

	public void decrementHealth(final float decrement) { setHealth(getHalfHeight() - decrement); }
	public void incrementHealth(final float increment) { setHealth(getHealth() + increment); }
	public void setHealth(final float health) { this.health = health; }
	public float getHealth() { return health; }

}