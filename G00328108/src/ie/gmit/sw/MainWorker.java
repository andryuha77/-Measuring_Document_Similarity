package ie.gmit.sw;

public class MainWorker implements Cloneable, Runnable {

	@Override
	public void run() {
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			Util.logMessage("Cloning HeavyWorker caused Error:" + e.getMessage());
		}
		return clone;
	}
}
