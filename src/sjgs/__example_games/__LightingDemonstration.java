package sjgs.__example_games;

import static sjgs.core.input.Keyboard.A;
import static sjgs.core.input.Keyboard.D;
import static sjgs.core.input.Keyboard.Q;
import static sjgs.core.input.Keyboard.SPACE;
import static sjgs.graphics.Colors.blue;
import static sjgs.graphics.Colors.red;
import java.awt.Color;
import java.awt.Graphics2D;
import sjgs.base_objects.BaseTile;
import sjgs.base_objects.PlayerBase;
import sjgs.core.Camera;
import sjgs.core.Engine;
import sjgs.core.Handler;
import sjgs.core.input.Keyboard;
import sjgs.enums.Facing;
import sjgs.graphics.lighting.Light;
import sjgs.graphics.lighting.LightsRenderer;
import sjgs.graphics.lighting.RadialLight;

/** Weclome to the @Light demonstration from Mitch's Game Engine! */

class __LightingDemonstration extends Engine {

	public __LightingDemonstration(final int WIDTH, final int HEIGHT, final String title) {
		super(WIDTH, HEIGHT, title);
	}

	// as normal we create our engine, camera, and players
	public static __LightingDemonstration engine;
	public static Camera camera;
	public static Player player;

	// create our window, initializing our engine...
	public static void main(final String[] args) {
		engine = new __LightingDemonstration(1280, 720, "Lighting Demonstration");
	}

	// initialize our player and camera, and here we create some lights, (see later)
	@Override
	protected void init() {
		player = new Player(getWidth()/2, getHeight()/2, 32, 64);
		camera = new Camera(this);
		for(int i = 0; i < 100; i++) {
			if(i % 10 == 0) new StaticLight(i*32, 575, 30, 1);
			new Block(i*32, 600, 32, 32);
		}
	}

	// again, as usual we need to tick our camera, keyinput, and handler
	// ... but here we're also going to tick all our lights!
	@Override
	protected void tick() {
		camera.tick(player.getCenter(), getScaleFactor());
		KeyInput.tick(player);
		Handler.tick(camera, getScaleFactor());
		for(final Light i : Handler.lights) i.tick();
	}

	// Render the handler, plus a nice white background. We also need to renderer the light manager
	@Override
	protected void render(final Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		Handler.render(g2d, camera, getScaleFactor());
		LightsRenderer.render(g2d, camera, engine);
	}

	// nice little light class, implementing its own tick method to move themselves
	private static class StaticLight extends RadialLight {

		public StaticLight(final float x, final float y, final float radius, final float intensity) {
			super(x, y, radius, intensity);
		}

		@Override
		public void tick() { setX(getX() + 1); }
	}

	// basic player class
	private static class Player extends PlayerBase {
		// ... but this time with its own light!
		private final RadialLight light;

		public Player(final float x, final float y, final float w, final float h) {
			super(x, y, w, h);
			light = new RadialLight(getCenterX(), getCenterY(), getWidth() * 3, 1);
		}

		@Override
		protected void init() { }

		@Override
		public void tick() {
			applyPhysics().discard();
			// make sure to set your light to the new location each game advancement!
			light.setLocation(getCenterX(), getCenterY());
			manageJumping();
		}

		@Override
		public void render(final Graphics2D g2d) {
			g2d.setColor(blue);
			getFullBounds().drawFilled(g2d);
		}

		@Override
		protected void destroy() {}

	}

	// basic blocks so we don't fall through the floor
	private static class Block extends BaseTile {

		public Block(final int x, final int y, final int w, final int h) {
			super(x, y, w, h, 0, 0);
		}
		@Override
		public void init() {
			dontApplyGravity();
		}
		@Override
		public void tick() { applyPhysics().discard(); }
		@Override
		public void render(final Graphics2D g2d) {
			g2d.setColor(red);
			getBounds().draw(g2d);
		}
		@Override
		public void destroy() {}
	}

	// basic key input class
	private static class KeyInput implements Keyboard {
		final static float walkingVelocity = 3f;
		static void tick(final PlayerBase player) {
			if(D()) { player.setVelX(walkingVelocity); player.setFacing(Facing.RIGHT); }
			else if(A()) { player.setVelX(-walkingVelocity); player.setFacing(Facing.LEFT); }
			if(SPACE()) if(!player.getJumping()) { player.setJumping(true); player.setVelY(-15f); }
			if(Q()) engine.exit();
		}

	}

	// AND THAT IS IT! Lighting can be such a complicated and frustrating subject,
	// However with Mitch's Game Engine I strived to make the process as simple as possible
	// Now, all you do is remember to call your light manager, set the lights to their new positions,
	// and you're done! Have fun!

}
