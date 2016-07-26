package sjgs.graphics.lighting;

import static sjgs.graphics.Colors.black;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import sjgs.core.Camera;
import sjgs.core.Engine;
import sjgs.core.Handler;

public class LightsRenderer {

	public static void render(final Graphics2D g2d, final Camera camera, final Engine engine){
		final BufferedImage lightmap = new BufferedImage((int) (engine.getWidth() * 1.1), (int) (engine.getHeight() * 1.1), BufferedImage.TYPE_INT_ARGB);

		final Graphics2D lightsRenderer = (Graphics2D) lightmap.getGraphics();

		lightsRenderer.setColor(black);
		lightsRenderer.fillRect(0, 0, lightmap.getWidth(), lightmap.getHeight());
		lightsRenderer.setComposite(AlphaComposite.DstOut);

		try{ for(final Light light : Handler.lights) light.update(lightsRenderer, camera, engine); }
		catch (final ConcurrentModificationException cme){ /* happens when adding / removing lights, ignore */ }

		lightsRenderer.dispose();

		g2d.drawImage(lightmap, 0, 0, null);
	}

}
