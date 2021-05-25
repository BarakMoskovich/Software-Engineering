package HW02;

import java.util.Comparator;

public class SortQueueNumber implements Comparator<Mobile> {
    @Override
    public int compare(Mobile o1, Mobile o2) {
        return o1.getQueueNumber().compareTo(o2.getQueueNumber());
    }
}
