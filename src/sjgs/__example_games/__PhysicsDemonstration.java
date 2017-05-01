package sjgs.__example_games;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.Serializable;
import sjgs.base_objects.BaseTile;
import sjgs.base_objects.Bullet;
import sjgs.base_objects.HardObject;
import sjgs.base_objects.PlayerBase;
import sjgs.core.Camera;
import sjgs.core.DeveloperConsole;
import sjgs.core.Engine;
import sjgs.core.Handler;
import sjgs.core.input.Keyboard;
import sjgs.core.input.Mouse;
import sjgs.enums.Facing;
import sjgs.enums.Type;
import sjgs.graphics.Colors;
import sjgs.utils.Utils;
import sjgs.utils.io.SaveFile;
import sjgs.utils.tools.Timer;
//---------------------------------------------------------------------------------------------------//
class __PhysicsDemonstration extends Engine {

	public __PhysicsDemonstration(final int WIDTH, final int HEIGHT, final String title) { super(WIDTH, HEIGHT, title); }

	public static void main(final String[] args) {
		engine = new __PhysicsDemonstration(1280, 720, "Physics Demonstration");
	}

	public static __PhysicsDemonstration engine;
	public static ExamplePlayer player;
	public static Camera camera;
	
	@Override
	protected void init() {
		disableFpsCap();
		setDoubleTickRate();
		camera = new Camera(this);
		new ExampleDevConsole(this);
		new ExampleMouseInput(this);
		generateWorld();
	}

	private void generateWorld() {
		clearHandler();
		final String[] map = {
				"t............................................t",
				"t............................................t",
				"t............................................t",
				"t........................................rtttt",
				"ttttttttttl.............................rttttt",
				"tttttttttttl....p......................rtttttt",
				"ttttttttttttl.........................rttttttt",
				"tttttttttttttttttttttttttttttttttttttttttttttt",
		};

		final int level_width = map[0].length();
		final int TILE_SIZE = 40;

		for(int row = 1; row < map.length+1; row++)
			for(int col = 1; col < level_width+1; col++)
				switch(map[row-1].charAt(col-1)) {
					case 't': new ExampleTile(TILE_SIZE*col, TILE_SIZE*row, TILE_SIZE, TILE_SIZE, 0, 0); break;
					case 'l': new ExampleTile(TILE_SIZE*col, TILE_SIZE*row, TILE_SIZE, TILE_SIZE, 100, 0); break;
					case 'r': new ExampleTile(TILE_SIZE*col, TILE_SIZE*row, TILE_SIZE, TILE_SIZE, 0, 100 ); break;
					case 'p': player = new ExamplePlayer(TILE_SIZE*col, TILE_SIZE*row, TILE_SIZE, TILE_SIZE*1.5f); break;
				}
	}

	@Override
	protected void tick() {
		try {
			if(DeveloperConsole.CONSOLE_OPEN) return;
			ExampleKeyInput.tick(player);
			Handler.tick(camera, getScaleFactor());
			camera.tick(player.getCenter(), getScaleFactor());
		} catch (final NullPointerException npe) { 
			System.out.println("null pointer caught in tick()");
		}
	}
	@Override
	protected void render(Graphics2D g2d) {
		Handler.render(g2d, camera, getScaleFactor());
		g2d = createG2D();
		g2d.setColor(Colors.white);
		final float x = getWidth()*0.025f;
		final float y = getHeight();
		g2d.drawString("Controls: ", x, y*0.85f);
		g2d.drawString("----------------------------", x, y*0.861f);
		g2d.drawString("Reset World = 'R'", x, y*0.875f);
		g2d.drawString("Shoot = 'F' or left click", x, y*0.895f);
		g2d.drawString("Spawn Tiles = 'E'", x, y*0.915f);
		g2d.drawString("Jump = 'Space'", x, y*0.935f);
		g2d.drawString("Zoom = 'Scroll Wheel'", x, y*0.955f);
		g2d.drawString("Console = '~'", x, y*0.9775f);
	}

	private static class ExamplePlayer extends PlayerBase {

		public ExamplePlayer(final float x, final float y, final float WIDTH, final float HEIGHT) { super(x, y, WIDTH, HEIGHT); }

		@Override
		public void init() { }

		@Override
		public void tick() {
			applyPhysics().discard();
			manageJumping();
		}

		@Override
		public void render(final Graphics2D g2d) {
			g2d.setColor(Colors.blue);
			getFullBounds().drawFilled(g2d);
			g2d.setColor(Colors.red);
			getBounds().draw(g2d);
		}

		@Override
		public void destroy() { removeFromHandler(); }
	}

	private static class ExampleTile extends BaseTile {
		public ExampleTile(final int x, final int y, final int width, final int height, final int startingHeight, final int endingHeight) {
			super(x, y, width, height, startingHeight, endingHeight);
		}
		@Override
		public void init() { }
		@Override
		public void tick() { }
		@Override
		public void render(final Graphics2D g2d) {
			g2d.setColor(Colors.green);
			drawTestBoundaries(g2d);
		}
		@Override
		public void destroy() { Handler.stationary_hard_objects.remove(this); }
	}

	private static class ExampleObject extends HardObject {
		private final Timer timer;
		private final Color color;

		public ExampleObject(final float x, final float y, final float width, final float height, final boolean mobile) {
			super(x, y, width, height, mobile);
			color = new Color(Utils.nextInt(256), Utils.nextInt(256), Utils.nextInt(256));
			timer = new Timer(500);
		}

		@Override
		public void init() {
			getBounds().setVelX(Utils.nextFloat() * Utils.nextInt(12) + 1);
			getBounds().setVelY(Utils.nextFloat() * Utils.nextInt(15) + 1);
			if(Utils.COIN_FLIP()) getBounds().invertVelX();
			if(Utils.COIN_FLIP()) getBounds().invertVelY();
		}

		@Override
		public void tick() {
			applyPhysics(Type.PLAYER, Type.HARD_OBJECT, Type.BULLET).discard();
			if(timer.tick()) destroy();
		}

		@Override
		public void render(final Graphics2D g2d) {
			g2d.setColor(color);
			getBounds().draw(g2d);
		}

		@Override
		public void destroy() { removeFromHandler(); }
	}

	private static class ExampleBullet extends Bullet {

		private final Color color;

		public ExampleBullet(final float x, final float y, final float width, final float height, final float velX, final float velY, final int elasticity, final int health_timer, final Color color) {
			super(x, y, width, height, velX, velY, elasticity, health_timer);
			this.color = color;
		}

		@Override
		public void init() { }

		@Override
		public void tick() {
			applyPhysics().discard();
			if(timer.tick()) destroy();
		}

		@Override
		public void render(final Graphics2D g2d) {
			g2d.setColor(color);
			getBounds().draw(g2d);
		}

		@Override
		public void destroy() { removeFromHandler(); }
	}



	private static class ExampleDevConsole extends DeveloperConsole {
		public ExampleDevConsole(final Engine engine) { super(engine); }
		private static ExampleSaveFile saveFile;

		@Override
		protected void commands(final String action, final String item, final String value) {
			switch(action) {
			case "spawn": spawn(); break;
			case "reset": engine.generateWorld(); break;
			case "fps": System.out.println("FPS: " + engine.FPS +", " + "TPS: " + engine.TPS); break;
			case "save": saveFile = new ExampleSaveFile(engine); break;
			case "load": saveFile.load(engine); break;
			}
		}

		private void spawn() {
			final int range = 10;
			for(int i = 0 ; i < 1000; i++) {
				final float velX = Utils.COIN_FLIP() ? -Utils.nextInt(range) : Utils.nextInt(range);
				final float velY = Utils.COIN_FLIP() ? -Utils.nextInt(range) : Utils.nextInt(range);
				final float x = Utils.COIN_FLIP() ? player.getCenter().x - Utils.nextInt(700) : player.getCenter().x + Utils.nextInt(700);
				final float y = player.getCenter().y - Utils.nextInt(500) - 10;

				new ExampleBullet( x, y,  15 + Utils.nextInt(40), 15 + Utils.nextInt(40),
						velX, velY, Utils.nextInt(100), 1000, new Color(Utils.nextInt(256), Utils.nextInt(256), Utils.nextInt(256))
						);
			}
		}
		private static class ExampleSaveFile extends SaveFile implements Serializable {
			public ExampleSaveFile(final Engine engine) { super(engine); }
			@Override
			public void load(final Engine engine) {
				loadEngineAndHandler(engine);
				player = (ExamplePlayer)Handler.players.peek();
			}
		}
	}

	private static class ExampleKeyInput implements Keyboard {
		static final float walkingVelocity = 3.5f;
		static void tick(final PlayerBase player) {
			if(DeveloperConsole.CONSOLE_OPEN) return;
			if(Keyboard.D()) { player.setVelX(walkingVelocity); player.setFacing(Facing.RIGHT); }
			else if(Keyboard.A()) { player.setVelX(-walkingVelocity); player.setFacing(Facing.LEFT); }
			if(Keyboard.SPACE()) if(!player.getJumping()) { player.setJumping(true); player.setVelY(-15f); }
			if(Keyboard.E()) new ExampleObject(player.getCenter().x, player.getCenter().y, 20, 20, true);
			if(Keyboard.R()) engine.generateWorld();
			if(Keyboard.F()) {
				final float velX = player.facingLeft() ? -15 : 15;
				new ExampleBullet(player.getCenter().x, player.getCenter().y, 40, 40, velX, 0, 75, 225, Colors.orange);
			}
			if(Keyboard.Q()) engine.exit();
		}
	}

	private static class ExampleMouseInput extends Mouse {
		public ExampleMouseInput(final Engine engine) { super(engine); }
		@Override
		public void mouseWheelMoved(final MouseWheelEvent e) {
			engine.setScaleFactor(engine.getScaleFactor() + -e.getPreciseWheelRotation() / 5f);
			engine.setScaleFactor(Utils.clamp(engine.getScaleFactor(), 0.1f, 15f));
		}
		@Override
		public void mousePressed(final MouseEvent e) {
			if(e.getButton() == 1) {
				final float velX = player.facingLeft() ? -50 : 50;
				new ExampleBullet(player.getCenter().x, player.getCenter().y, 40, 40, velX, 0, 35, 650, Colors.cyan);
			}
		}
		@Override
		public void mouseMoved(final MouseEvent e) { }
		@Override
		public void mouseClicked(final MouseEvent e) { }
		@Override
		public void mouseReleased(final MouseEvent e) { }
	}

}
