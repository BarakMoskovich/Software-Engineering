package HW02;

public class Mobile implements Comparable<Mobile> {
	private String number;
	private Client client;
	private String info;
	private String queueNumber;

	public Mobile(String number, Client client, String info, String queueNumber) {
		setNumber(number);
		setClient(client);
		setInfo(info);
		setQueueNumber(queueNumber);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getQueueNumber() {
		return queueNumber;
	}

	public void setQueueNumber(String queueNumber) {
		this.queueNumber = queueNumber;
	}

	@Override
	public String toString() {
		return String.format("%-16s\t%-16s\t%-16s", client.getFullName(), number, queueNumber);
	}

	@Override
	public int compareTo(Mobile o) {
		return getQueueNumber().compareTo(o.getQueueNumber());
	}
}
