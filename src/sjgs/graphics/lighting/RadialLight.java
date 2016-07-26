package sjgs.graphics.lighting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import sjgs.core.Camera;
import sjgs.core.Engine;
import sjgs.core.Handler;

public class RadialLight extends Light {

	private final BufferedImage lightImage;
	protected final float radius;

	public RadialLight(final float x, final float y, final float radius, final float intensity) {
		super(x, y, intensity);
		this.radius = radius;

		/******** DRAW THE LIGHT IMAGE **********************************************************/
		final Color[] color = { new Color(0, 0, 0, intensity), new Color(0, 0, 0, 0) };
		lightImage = new BufferedImage((int)(radius*2), (int)(radius*2), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = (Graphics2D) lightImage.getGraphics();
		final float[] distance = { 0f, 1f };
		g2d.setPaint(new RadialGradientPaint(new Point((int)radius, (int)radius), radius, distance, color));
		g2d.fillOval(0, 0, (int)(radius*2), (int)(radius*2));
		g2d.dispose();
		/***************************************************************************************/

		Handler.lights.add(this);
	}

	@Override
	public void update(final Graphics2D lightsRenderer, final Camera camera, final Engine engine){
		lightsRenderer.drawImage(lightImage.getScaledInstance((int)(lightImage.getWidth() * engine.getScaleFactor()),
				(int)(lightImage.getHeight() * engine.getScaleFactor()), Image.SCALE_FAST),
				(int)((center.x + camera.getX()) * engine.getScaleFactor()) - (int)(radius * engine.getScaleFactor()),
				(int)((center.y + camera.getY()) * engine.getScaleFactor()) - (int)(radius * engine.getScaleFactor()), null);
	}

	public float getRadius() { return radius; }
}
