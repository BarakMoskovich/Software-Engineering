package HW02;

public class QueueGenerator {
	private static QueueGenerator _instance = null;
	private int queue;

	public QueueGenerator() {
		this.queue = 0;
	}

	private int getNumber() {
		return queue;
	}

	private int getNext() {
		return ++queue;
	}

	public String getNextEqueue() { return "A" + getNext(); }

	public static QueueGenerator getInstance() {
		if (_instance == null)
			_instance = new QueueGenerator();
		return _instance;
	}
	@Override
	public String toString() {
		return "Equeue number : A" + getNumber();
	}

}
