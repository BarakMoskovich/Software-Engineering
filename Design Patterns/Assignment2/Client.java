package HW02;

public class Client implements Comparable<Client> {
	private String id;
	private String firstName;
	private String lastName;

	public Client(String id, String firstName, String lastName) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public String toString() {
		return String.format("%-13s %-13s %-15s", getId(), getFirstName(), getLastName());
	}

	@Override
	public int compareTo(Client o) {
		return getFullName().compareTo(o.getFullName());
	}
}
