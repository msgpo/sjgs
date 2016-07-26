package sjgs.utils.multithreading;

import java.util.ArrayList;
import sjgs.utils.Utils;

public class ThreadPool extends ThreadGroup {

	private final ArrayList<Runnable> taskQueue;

	public ThreadPool() {
		super("ThreadPool");
		taskQueue = new ArrayList<Runnable>();
		setDaemon(true);
		for (int i = 0; i < Utils.getNumProcessors(); i++) new PooledThread(this).start();
	}

	private synchronized Runnable getTask() {
		while (taskQueue.isEmpty()) try { wait(); } catch (final InterruptedException e) { e.printStackTrace(); }

		return taskQueue.remove(0);
	}

	public synchronized void runTask(final Runnable task) {
		taskQueue.add(task);
		notify();
	}

	private static class PooledThread extends Thread {

		private final ThreadPool pool;

		public PooledThread(final ThreadPool pool) {
			super(pool, "PooledThread");
			this.pool = pool;
		}

		@Override
		public void run() {
			while (!isInterrupted()) {
				final Runnable task = pool.getTask();

				try { task.run(); } catch (final Exception e) { e.printStackTrace(); }
			}
		}
	}

}
