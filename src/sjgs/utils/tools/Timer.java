package sjgs.utils.tools;

public class Timer {

	// NOTE THESE VARIABLES ARE BASED ON HAVING A 60 TPS TICK METHOD!
	public static final int SECOND = 60, MINUTE = SECOND*60;

	private final int duration;
	private int timeRemaining;
	private boolean paused;

	public Timer(final int duration) { this.duration = timeRemaining = duration; }

	public boolean tick() { return paused ? false : --timeRemaining <= 0; }

	public void reset() { timeRemaining = duration; }

	public void pause() { paused = true; }

	public void resume() { paused = false; }

	public void toggle() { paused = !paused; }

	public void destroy() { timeRemaining = 0; paused = false; }

}
