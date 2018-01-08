package ie.gmit.sw;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Util {
	private static ArrayBlockingQueue<Job> inQueue;
	private static ConcurrentHashMap<Integer, StoreResults> outQueue;
	private static ThreadPoolService workersPool;
	private static volatile int jobNumber = 0;
	private static volatile int workerNumber = 0;
	private static ArrayBlockingQueue<String> servLog;

	private Util() {
	}

	public static synchronized Boolean init() {
		// initialization of workers thread pool. The size determined in web.xml.
		try {
			inQueue = new ArrayBlockingQueue<>(Config.numOfWorkers);
			outQueue = new ConcurrentHashMap<>();
			workersPool = new ThreadPoolService(Worker.class, Config.numOfWorkers);
			if (Util.isLoggingOn()) {
				servLog = new ArrayBlockingQueue<>(Config.numOfWorkers);
				LogService.init(Util.getServLog(), Config.logFile);
			}
			Util.logMessage(String.format("Thread Pool initialized", Config.numOfWorkers));
		} catch (Exception e) {
			Util.logMessage("ThreadPool failed to initialize: " + e.getMessage());
			// In case Logging service failed to start.
			System.out.println("ThreadPool failed to initialize: " + e.getMessage());
			return false;
		}
		return true;
	}

	public static ArrayBlockingQueue<Job> getInQueue() {
		return inQueue;
	}

	public static ConcurrentHashMap<Integer, StoreResults> getOutQueue() {
		return outQueue;
	}

	public static synchronized int getJobNumber() {
		jobNumber++;
		return jobNumber;
	}

	public static synchronized int getWorkerNumber() {
		workerNumber++;
		return workerNumber;
	}

	// Add a new Worker to Thread pool. If more than predefine workers are submitted

	public static boolean processJob(Job job) {
		try {
			workersPool.addMainWorker();
			inQueue.put(job);
		} catch (CloneNotSupportedException | InterruptedException e) {
			Util.logMessage("ERROR adding new Worker to ThreadPool: " + e.getMessage());
			return false;
		}
		return true;
	}

	public static ArrayBlockingQueue<String> getServLog() {
		return servLog;
	}

	public static int getHashFunctionsSize() {
		return Config.hashFunctionsSize;
	}

	public static int getShingleSize() {
		return Config.shingleSize;
	}

	public static DocumentInterface getDb() {
		return Config.db;
	}

	public static boolean isLoggingOn() {
		return Config.loggingOn;
	}

	public static int getRefreshRate() {
		return Config.refreshRate;
	}

	// Logging service entry point
	public static void logMessage(String message) {
		if (Util.isLoggingOn())
			servLog.offer(message);
	}

	public static void shutdown() {
		workersPool.shutDown();
		Util.logMessage("ThreadPool Shutdown.");
		Util.logMessage(String.format("Total jobs processed: %d", jobNumber));
		Util.logMessage(String.format("Total workers spawned: %d", workerNumber));
		if (Util.isLoggingOn())
			LogService.shutdown();
	}

	@Override
	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}

	public static class Config {
		private static int numOfWorkers;
		private static int hashFunctionsSize;
		private static int shingleSize;
		private static DocumentInterface db;
		private static boolean loggingOn = false;
		private static int refreshRate;
		private static String logFile;

		public static void setNumOfWorkers(int numOfWorkers) {
			Config.numOfWorkers = numOfWorkers;
		}

		public static void setHashFunctions(int hashFunctions) {
			Config.hashFunctionsSize = hashFunctions;
		}

		public static void setShingleSize(int shingleSize) {
			Config.shingleSize = shingleSize;
		}

		public static void setDb(String filename, String password) {
			Config.db = new Db4oController(filename, password);
		}

		public static void setLoggingOn(boolean loggingOn) {
			Config.loggingOn = loggingOn;
		}

		public static void setRefreshRate(int refreshRate) {
			Config.refreshRate = refreshRate;
		}

		public static void setLogFile(String logFile) {
			Config.logFile = logFile;
		}

	}

}
