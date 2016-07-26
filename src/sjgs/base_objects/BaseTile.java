package sjgs.base_objects;

import static sjgs.utils.Utils.brensenham;
import static sjgs.utils.Utils.exit;
import static sjgs.utils.Utils.isPercent;
import static sjgs.utils.Utils.print;
import java.awt.Graphics2D;
import java.io.Serializable;
import sjgs.utils.data_structures.vectors.SimplePoint;

public abstract class BaseTile extends HardObject implements Serializable {

	private final SimplePoint[] slopePoints;
	/** @param sh: The starting point at the left of the tile */
	/** @param eh: The ending point at the right of the tile */
	/** Both are measured from the floor upwards */
	private final int sh, eh;

	public BaseTile(final int x, final int y, final int w, final int h, final float startingHeightPercentage, final float endingHeightPercentage) {
		super(x, y, w, h, false);
		if(!isPercent(startingHeightPercentage / 100f) || !isPercent(endingHeightPercentage / 100f)) { print("Error: Tile angle height % out of range"); exit(); }
		sh = (int)(getHeight() * (startingHeightPercentage / 100f)); eh = (int)(getHeight() * (endingHeightPercentage / 100f));

		if(!(sh == 0 && eh == 0)) {
			final int shX = (int)getX();
			final int shY = (int)(getY() + getHeight() - sh);
			final int ehX = (int)(getX() + w);
			final int ehY = (int)(getY() + getHeight() - eh);

			slopePoints = brensenham(shX, shY, ehX, ehY);

		} else slopePoints = null;


	}

	@Override
	public abstract void init();
	@Override
	public abstract void tick();
	@Override
	public abstract void render(Graphics2D g2d);
	@Override
	public abstract void destroy();

	public boolean angled() { return slopePoints != null; }

	public SimplePoint[] getSlopePoints() { return slopePoints; }

	protected void drawTestBoundaries(final Graphics2D g2d) {
		if(!angled()) getBounds().draw(g2d);
		else {
			// BOTTOM LINE
			g2d.drawLine((int)getX(), (int)(getY()+getHeight()), (int)(getX()+getWidth()), (int)getY()+(int)getHeight());

			// BOTTOM TO SH LINE
			g2d.drawLine((int)getX(), (int)(getY()+getHeight()), (int)getX(), (int)(getY()+getHeight()) - sh);

			// SH TO EH LINE
			g2d.drawLine((int)getX(), (int)(getY()+getHeight() - sh), (int)(getX() + getWidth()), (int)(getY()+getHeight()) - eh);

			// BOTTOM RIGHT TO EH LINE
			g2d.drawLine((int)(getX() + getWidth()), (int)(getY()+getHeight()), (int)(getX() + getWidth()), (int)(getY()+getHeight()) - eh);
		}
	}

}
