package sjgs.utils.data_structures.vectors;

public class SimplePoint {

	public int x, y;

	public SimplePoint(final int x, final int y) { this.x = x; this.y = y; }
	public SimplePoint() {}

	public void setLocation(final int x, final int y) { this.x = x; this.y = y; }

	@Override
	public SimplePoint clone() { return new SimplePoint(x, y); }

}
