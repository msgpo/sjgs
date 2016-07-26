package sjgs.graphics.backgrounds;

import java.awt.Graphics2D;
import sjgs.core.Camera;

public class ParallaxBackground {

	private final Background[] backgrounds;

	// sort these later so no need to worry about order
	public ParallaxBackground(final Background ...args) { backgrounds = args; }

	public void render(final Graphics2D g2d, final Camera camera, final double scaleFactor) {
		for(final Background b : backgrounds) b.render(g2d, camera, scaleFactor);
	}

}
