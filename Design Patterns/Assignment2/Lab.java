package HW02;

import java.util.*;

public class Lab implements LabFunc, Comparator<Mobile> {
	private final ArrayList<Mobile> mobilesList;
	private final HashMap<Mobile, String> statusMap;
	private Comparator<Mobile> comparator;
	private QueueGenerator queueGenerator;
	private int size;

	public Lab() {
		mobilesList = new ArrayList<>();
		statusMap = new HashMap<>();
		setComparator(new SortQueueNumber()); // Default
		queueGenerator = QueueGenerator.getInstance();
	}

	public String getNextEQueue() {
		return queueGenerator.getNextEqueue();
	}

	public String getEQueue() {
		return queueGenerator.toString();
	}

	@Override
	public void addMobile(Mobile m) {
		// Appends Mobile
		mobilesList.add(m);
		size++;
	}

	@Override
	public boolean setStatus(String mobileNumber, String status) {
		// put Mobile and status to statusMap
		return statusMap.put(findMobileByNumber(mobileNumber), status) != null;
//		return statusMap.put(findMobileByNumber(mobileNumber), status) != null ? true : false;
	}

	@Override
	public String getStatus(String mobileNumber) {
		// Get Mobile status by mobile number
		for (Mobile mobile : statusMap.keySet())
			if (mobile.getNumber().contains(mobileNumber))
				return statusMap.get(mobile);
		return null;
	}

	public boolean isExist(String mobileNumber) {
		// Check if Mobile exist in statusMap
		for (Mobile mobile : statusMap.keySet())
			if (mobile.getNumber().contains(mobileNumber))
				return true;
		return false;
	}

	@Override
	public Mobile getMobile(int index) {
		// Returns the Mobile at the specified position in MobileList
		return (index < size) ? mobilesList.get(index) : null;
	}

	@Override
	public boolean removeMobile(Mobile m) {
		// Removes the Mobile from MobileList,if it is present
		// Search by Mobile
		if (mobilesList.contains(m)) {
			statusMap.remove(m);
			mobilesList.remove(m);
			size--;
			return true;
		}
		return false;
	}

	@Override
	public boolean removeMobile(String mobileNumber) {
		// Removes the Mobile from MobileList,if it is present
		// Search by Mobile Number
		for (Mobile mobile : statusMap.keySet()) {
			if (mobile.getNumber().contains(mobileNumber)) {
				statusMap.remove(mobile);
				mobilesList.remove(mobile);
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public Mobile removeMobile(int index) {
		// Removes the Mobile from MobileList,if it is present
		// Search by index
		if (index > size)
			return null;

		Mobile tmp;
		if (mobilesList.get(index) != null) {
			tmp = mobilesList.get(index);
			statusMap.remove(tmp);
			size--;
			return mobilesList.remove(index);
		}
		return null;
	}

	@Override
	public int size() {
		// Returns the number of elements in MobileList
		return size;
	}

	@Override
	public Comparator<Mobile> getComparator() {
		// Returns the comparator of MobileList.
		return comparator;
	}

	@Override
	public void setComparator(Comparator<Mobile> c) {
		// Set default comparator in MobileList for default sorting
		comparator = c;
	}

	@Override
	public void sort() {
		// Sorts MobileList according to the order induced by Comparator
		Collections.sort(mobilesList, comparator);
	}

	@Override
	public Mobile findMobileByNumber(String mobileNumber) {
		// Find and return Mobile in mobileList by mobile number
		Iterator<Mobile> iter = iterator();
		Mobile tmp;

		while (iter.hasNext()) {
			tmp = iter.next();
			if (tmp.getNumber().equals(mobileNumber))
				return tmp;
		}
		return null;
	}

	private void printSortedByStatus(String status) {
		// Print Mobile in status
		// Sorted by mobile number
		setComparator(new SortByNumber());
		Set<Mobile> mobileSet = new TreeSet<>(comparator);
		Iterator<Mobile> iter = iterator();
		Mobile tmp;

		while (iter.hasNext()) {
			tmp = iter.next();
			if (getStatus(tmp.getNumber()).equals(status))
				mobileSet.add(tmp);
		}

		for (Mobile mobile: mobileSet) {
			System.out.println(mobile);
		}
	}

	@Override
	public void printRepaired() {
		// Print all mobiles repaired
		// Sorted by mobile number
		printSortedByStatus("Repaired");
	}

	@Override
	public void printInRepair() {
		// Print all mobiles in repair
		// Sorted by mobile number
		printSortedByStatus("In Repair");
	}

	@Override
	public void printAllMobiles() {
		// Print all mobiles
		// Sorted by queue number
		System.out.printf("%-30s %-30s %-30s \n", "Client Full Name", "No.", "Status");

		TreeMap<Mobile, String> sorted = new TreeMap<>();
		sorted.putAll(statusMap);

		for (Map.Entry<Mobile, String> entry : sorted.entrySet()) {
			System.out.printf("%-30s %-30s %-30s \n", entry.getKey().getClient().getFullName(),
					entry.getKey().getNumber(),
					entry.getValue());
		}
	}

	public void printClientsSet() {
		// Printed by Client full name
		// Sorted by Client
		Set<Client> clientSet = new TreeSet<>();
		Iterator<Mobile> iter = iterator();

		while (iter.hasNext()) {
			clientSet.add(iter.next().getClient());
		}

		for (Client client : clientSet)
			System.out.println(client.toString());
	}

	@Override
	public int compare(Mobile o1, Mobile o2) {
		return o1.compareTo(o2);
	}

	@Override
	public Iterator<Mobile> iterator() {
		return new ConcreteIterator();
	}

	private class ConcreteIterator implements Iterator<Mobile> {
		private int cur = 0;
		private int last = -1;

		@Override
		public boolean hasNext() {
			return cur < size;
		}

		@Override
		public Mobile next() {
			if(!hasNext())
				throw new NoSuchElementException();
			Mobile tmp = mobilesList.get(cur);
			last = cur;
			cur++;
			return tmp;
		}

		@Override
		public void remove() {
			if (last == -1)
				throw new IllegalStateException();

			if (last != 0) {
				cur = last;
				last = -1;
			}
			size--;

			statusMap.remove(mobilesList.get(last));
			mobilesList.remove(last);
		}
	}
}
