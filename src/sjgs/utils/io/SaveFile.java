package sjgs.utils.io;

import java.io.Serializable;
import sjgs.core.Engine;

public abstract class SaveFile extends __HandlerSaveState implements Serializable {

	// ----------- engine variables -------------- //
	private final double engineTickInterval, engineFpsCap;
	private final boolean engineDrawFPS;
	// ------------------------------------------- //

	public SaveFile(final Engine engine) {
		super();
		engineTickInterval = engine.getTickRate();
		engineFpsCap = engine.getFPS_CAP();
		engineDrawFPS = engine.getDrawFps();
	}

	public abstract void load(Engine engine);

	private void loadIntoEngine(final Engine engine) {
		engine.setDrawFps(engineDrawFPS);
		engine.setTickRate(engineTickInterval);
		engine.setFPS_CAP(engineFpsCap);
	}

	// returns success status
	protected boolean loadEngineAndHandler(final Engine engine) {
		try {	loadIntoEngine(engine);
		loadIntoHandler();
		return true;
		} catch (final Exception e) { e.printStackTrace(); return false; }
	}

}