package ie.gmit.sw;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadPoolService {
	private ExecutorService executor;
	private MainWorker mainWorker;
	private Runnable worker;

	@SuppressWarnings("rawtypes")
	public ThreadPoolService(Class workerClass, int numOfWorkers)
			throws InstantiationException, IllegalAccessException {
		executor = Executors.newFixedThreadPool(numOfWorkers, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r);
			}
		});
		mainWorker = (MainWorker) workerClass.newInstance();
	}

	public ThreadPoolService(Runnable worker, int numOfWorkers) throws InstantiationException, IllegalAccessException {
		executor = Executors.newFixedThreadPool(numOfWorkers, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r);
			}
		});
		this.worker = worker;
	}

	// Add new instance of worker type Runnable to Thread pool.

	public void addWorker() throws InstantiationException, IllegalAccessException {
		Runnable newWorker = worker.getClass().newInstance();
		executor.execute(newWorker);
	}

	// Add a MainWorker clone to Thread pool

	public void addMainWorker() throws CloneNotSupportedException {
		Runnable newWorker = (Runnable) mainWorker.clone();
		executor.execute(newWorker);
	}

	// shutdown ThreadPool

	public void shutDown() {
		executor.shutdown();
	}

	@Override
	protected void finalize() throws Throwable {
		shutDown();
		super.finalize();
	}
}
