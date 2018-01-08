package ie.gmit.sw;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogService {

	private static String logFile;
	private static ArrayBlockingQueue<String> servLog;
	private static LogService instance;
	private static boolean stop = false;

	private LogService(ArrayBlockingQueue<String> servLog, String logFile) {
		LogService.logFile = logFile;
		LogService.servLog = servLog;
		Thread dLogger = new Thread(new Runnable() {
			public void run() {
				try {
					logger();
				} catch (InterruptedException e) {
					System.out.println("[ERROR] Logging service error: " + e.getMessage());
				}
			}
		});
		dLogger.start();
	}

	// Singleton Initialization method.

	public static synchronized LogService init(ArrayBlockingQueue<String> servLog, String logFile) {
		if (instance == null) {
			instance = new LogService(servLog, logFile);
		}
		return instance;
	}

	private static void logger() throws InterruptedException {
		// Creates Logger for log file management
		Logger logger = Logger.getLogger("ServerLog");
		logger.setUseParentHandlers(true);
		FileHandler fh;
		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler(logFile, true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			String log = new StringBuilder().append("Logging Service Started in: ").append(logFile)
					.append(System.getProperty("line.separator")).append("____________________________________________")
					.toString();
			// Allows to orderly finish thread
			do {
				logger.info(log);
				log = servLog.take();
			} while (!stop);
			log = "Logging Service Stopped.";
			logger.warning(log);
		} catch (SecurityException e) {
			System.out.println("Logging service exception: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Please set the corrent path in web.xml\n " + e.getMessage());
		}
	}

	// Terminate the logging Thread.

	public static void shutdown() {
		stop = true;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		shutdown();
	}
}
