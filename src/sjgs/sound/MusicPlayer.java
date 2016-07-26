package sjgs.sound;

import java.io.IOException;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

// NOTE: This class needs the JLayer.jar in your main project in order to function!

public class MusicPlayer extends PlaybackListener {

	private AdvancedPlayer player;
	private SoundThread thread;
	private JavaSoundAudioDevice device;
	private InputStream stream;
	private String filename;
	private final float gain;
	private final boolean loop;

	private boolean paused;

	public MusicPlayer(final String filename, final float gain, final boolean loop) {
		this.filename = filename; this.gain = gain; this.loop = loop;
		createAdvancedPlayer();
	}
	public MusicPlayer(final String filename, final boolean loop) {
		this.filename = filename; gain = 0; this.loop = loop;
		createAdvancedPlayer();
	}

	private void createAdvancedPlayer() {
		try {
			final JavaSoundAudioDevice device = (JavaSoundAudioDevice) FactoryRegistry.systemRegistry().createAudioDevice();
			device.createSource();
			device.setGain(gain);
			stream = SoundPlayer.class.getResourceAsStream(filename);
			player = new AdvancedPlayer(stream, device);
		} catch (final Exception e) { e.printStackTrace(); }
		player.setPlayBackListener(this);
	}

	public void play() {
		thread = new SoundThread(this, loop, filename);
		thread.start();
		paused = false;
	}

	public void pause() {
		thread.stop();
		paused = true;
	}

	public void togglePause() {
		if(paused) play(); else pause();
	}

	public void reset() { createAdvancedPlayer(); }

	// REMEMBER TO CALL THIS FOR RESOURCE LEAKS!!!!
	public void destroy() {
		thread.stop();
		thread = null;
		// Not familiar with JLayer's API, but this seems to throw a null pointer occasionally?
		try { device.flush(); device.close(); } catch (final NullPointerException npe) { }
		try { stream.close(); } catch (final IOException e) { e.printStackTrace(); }
		player.close();
		player = null;
		filename = null;
		device = null;
		stream = null;
	}

	private void runThread() {
		try {  player.play(); // will play from start if not paused before, else it will start @ pause position
		createAdvancedPlayer();
		} catch (final JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public boolean paused() { return paused; }
	public String getFilename() { return filename; }

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	private static class SoundThread extends Thread {

		private final boolean loop;
		private final MusicPlayer player;

		public SoundThread(final MusicPlayer player, final boolean loop, final String filename) {
			this.loop = loop;
			this.player = player;
			setName(filename);
		}

		@Override
		public void run() {
			do {

				player.runThread();

			} while(loop);

			player.destroy();
		}

	}

}