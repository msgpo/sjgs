package sjgs.utils.multithreading;

public class Runner extends Thread {

	private final Executable e;

	public Runner(final Executable e) {
		super();
		this.e = e;
	}

	public Runner(final Executable e, final String name) {
		super();
		this.e = e;
		setName(name);
	}

	@Override
	public void run() { try { e.execute(); } catch (final Exception e) { e.printStackTrace(); } }

}
