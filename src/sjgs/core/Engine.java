package sjgs.core;

import static sjgs.graphics.Colors.black;
import static sjgs.graphics.Colors.white;
import static sjgs.utils.Utils.error;
import static sjgs.utils.pyutils.PyUtils.java2py;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.python.core.PyObject;
import sjgs.core.input.__Keyboard;
import sjgs.core.jython.Jython;
import sjgs.physics.Physics;
import sjgs.utils.Utils;
import sjgs.utils.multithreading.Runner;
import sjgs.utils.multithreading.ThreadPool;

public abstract class Engine extends Canvas implements Runnable, Serializable {

	private static final String engine_version = "SJGS v0.0.13 - Jan 17, 2017";

	/** @TICK_INTERVAL && @FPS_CAP: default no arg constructor gives 60 tps / fps */
	private double TICK_INTERVAL;
	private double FRAME_RATE, FPS_CAP;
	private double scaleFactor = 1d;
	private boolean running;
	protected int frames, ticks, FPS, TPS;

	// NOTE THE DEFAULTS!
	private boolean multithreadedGameUpdating = false, multithreadedRendering = true, variableSleepRate = false;

	public final ThreadPool pool;
	public final JFrame frame;
	public final JPanel panel;

	public final PyObject self;

	public Engine(final int WIDTH, final int HEIGHT, final String title) {
		error(getVersion() + "\n" + Utils.OS + "\n" + "-----------------------------");

		pool = new ThreadPool();
		frame = new JFrame();
		panel = new JPanel();
		self = java2py(this);

		Utils.print("Objects initialized.");
		
		setFPS_CAP(60.0d);
		TICK_INTERVAL = Utils.second / 60.0d;
		toggleDrawFPS();
		createWindow(WIDTH, HEIGHT, title);
		Utils.print("Game window created.");
		
		new Physics().init();    
		Utils.print("Physics initialized.");
		
		new Handler().init();   
		Utils.print("Handler initialized.");
		
		new Jython().__init__(); 
		Utils.print("Jython scripting initialized.");
		
		init();
		
		pool.runTask(this);
		
		Utils.print("Game engine starting...");

		error("-----------------------------");
	}

	private void createWindow(final int WIDTH, final int HEIGHT, final String title) {
		new Runner(() ->  {
			setFocusTraversalKeysEnabled(false); /* NOTE YOU NEED THIS FOR TAB KEY TO WORK! */
			frame.setTitle(title);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
		}).run();
		frame.add(panel); frame.add(this); frame.pack();
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		new __Keyboard().init(panel);
	}

	//----------------------------------------------------------------//
	protected abstract void init();
	protected abstract void tick();
	protected abstract void render(Graphics2D g2d);
	//----------------------------------------------------------------//

	private boolean drawFPS, rendering;
	private BufferStrategy bs; private Graphics g; private Graphics2D g2d;
	public void engine_render() {
		if(rendering) return;
		begin();
		drawBaseLayer();
		scale();
		// ================================ //
		render(g2d);
		// ================================ //
		renderDevConsole();
		renderFPS();
		end();
	}
	private void end() {
		g2d.dispose(); g.dispose();
		g2d = null; g = null;
		bs.show();
		rendering = false;
	}
	private void renderFPS() { if(drawFPS) drawFPS(); }
	private void renderDevConsole() {
		final Graphics2D consoleRenderer = createG2D();
		DeveloperConsole.render(consoleRenderer, getWidth(), getHeight());
	}
	private void begin() {
		rendering = true;
		if (bs == null) createBufferStrategy(3);
		bs = getBufferStrategy();
		if (bs.getDrawGraphics() != null) g = bs.getDrawGraphics();
		if(g != null) g2d = (Graphics2D) g;
	}
	private void drawBaseLayer() {
		g2d.setColor(black);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}
	private void drawFPS() {
		final Graphics2D fps = createG2D();
		fps.setColor(white);
		fps.drawString("FPS: " + FPS + "  " +  "TPS: " + TPS, 50, 50);
	}

	public final void scale() { g2d.scale(scaleFactor, scaleFactor); }






	/** @method createG2D: Creates a new g2d object, useful for when you need to reset
	 * 					   your current g2d object, but can't destroy it. 			*/
	public final Graphics2D createG2D() {
		if (bs.getDrawGraphics() != null) g = bs.getDrawGraphics();
		return g != null ? (Graphics2D) g : null;
	}

	@Override
	public final void run() {
		long now, tickThen, frameThen, fpsUpdateTimer;
		now = tickThen = frameThen = System.nanoTime();
		fpsUpdateTimer = System.currentTimeMillis();
		final int SECOND = 1000;
		double deltaR, deltaT;
		int sleepTime;
		final Runner ticker = new Runner(() -> { tick(); ticks++; });
		final Runner renderer = new Runner(() -> { engine_render(); frames++; });

		frame.setVisible(true);
		setVisible(true);

		running = true;

		// GAME LOOP
		while (running) {
			// GET TIME
			now = System.nanoTime();

			// TICK
			if(now - tickThen > TICK_INTERVAL) {
				if(multithreadedGameUpdating) pool.runTask( ticker );
				else { tick(); ticks++; }
				tickThen = now;
			}

			// RUN RENDERER IN THREADPOOL
			if(!rendering && now - frameThen > FRAME_RATE) {
				if(multithreadedRendering) pool.runTask(renderer);
				else { engine_render(); frames++; }
				frameThen = now;
			}

			// UPDATE FRAMES PER SECOND
			if (System.currentTimeMillis() - fpsUpdateTimer > SECOND) {
				FPS = frames; TPS = ticks; frames = ticks = 0;
				fpsUpdateTimer += SECOND;
			}


			if(variableSleepRate) {
				// FIND WHICH TIME IS SHORTER:
				// ---- The time until next game update,
				// ---- or the time until next render
				// Take whichever is shorter, and sleep that length
				now = System.nanoTime();
				deltaR = FRAME_RATE - (now - frameThen);
				deltaT = TICK_INTERVAL - (now - tickThen);
				sleepTime = (int) (deltaR < deltaT ? deltaR : deltaT) / 1_000_000 - 1;
				if (sleepTime > 0) try { Thread.sleep(sleepTime); } catch (final InterruptedException e) { e.printStackTrace(); }
			}
		}
	}

	protected int getScreenWidth() { return Utils.SCREEN_WIDTH; }
	protected int getScreenHeight() { return Utils.SCREEN_HEIGHT; }
	protected void clearHandler() { Handler.clearAll(); }
	public void disableFpsCap() { setFPS_CAP(10_000); }
	public double getScaleFactor() { return scaleFactor; }
	public void setScaleFactor(final double scaleFactor) { this.scaleFactor = scaleFactor; }
	public static final String getVersion() { return engine_version; }
	public final int getFPS() { return FPS; }
	public final int getTPS() { return TPS; }
	public final double getTickRate() { return TICK_INTERVAL; }
	public final void setTickRate(final double TICK_INTERVAL) { this.TICK_INTERVAL = TICK_INTERVAL; }
	public final double getFPS_CAP() { return FPS_CAP; }
	public final void setFPS_CAP(final double FPS_CAP) { this.FPS_CAP = FPS_CAP; FRAME_RATE = Utils.second / FPS_CAP; }
	public final void setDoubleTickRate() { TICK_INTERVAL = Utils.second / 120d; new Physics().setDoubleTickRate(); }
	public void exit() { System.gc(); running = false; System.exit(0); }
	public final void toggleDrawFPS() { drawFPS = !drawFPS; }
	public final boolean getDrawFps() { return drawFPS; }
	public final void setDrawFps(final boolean drawFPS) { this.drawFPS = drawFPS; }

	public final void enableVariableSleepRate() { variableSleepRate = true; }
	public final void enableMultithreadedGameUpdating() { multithreadedGameUpdating = true; }
	public final void enableMultithreadedRendering() { multithreadedRendering = true; }

	public final void disableVariableSleepRate() { variableSleepRate = false; }
	public final void disableMultithreadedGameUpdating() { multithreadedGameUpdating = false; }
	public final void disableMultithreadedRendering() { multithreadedRendering = false; }
}
