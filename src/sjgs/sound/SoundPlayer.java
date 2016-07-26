package sjgs.sound;

import java.io.InputStream;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;
import sjgs.utils.multithreading.Runner;

// NOTE: This class needs the JLayer.jar in your main project in order to function!

public final class SoundPlayer extends PlaybackListener {

	private static final SoundPlayer PLAYBACK_LISTENER = new SoundPlayer();

	public static void play(final String filename) { play(filename, 0); }

	/** @method play: Plays an mp3 audio file with given resource stream name.
	 * 				  gain can be a value from -80 to 6. No argument assumes no gain */
	public static void play(final String filename, final float gain) {
		new Runner(() -> {
			try {
				JavaSoundAudioDevice device = (JavaSoundAudioDevice) FactoryRegistry.systemRegistry().createAudioDevice();
				device.createSource();
				device.setGain(gain);
				InputStream stream = SoundPlayer.class.getResourceAsStream(filename);
				AdvancedPlayer player = new AdvancedPlayer(stream, device);
				player.setPlayBackListener(PLAYBACK_LISTENER);
				player.play();
				// --------- //
				device.flush();
				device.close();
				player.close();
				stream.close();
				device = null;
				stream = null;
				player = null;
			} catch (final Exception e) { e.printStackTrace(); }
		}, "Sound Player: " + filename).start();
	}

}