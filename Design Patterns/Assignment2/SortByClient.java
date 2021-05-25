package HW02;

import java.util.Comparator;

public class SortByClient implements Comparator<Mobile> {
    @Override
    public int compare(Mobile o1, Mobile o2) {
        return o1.getClient().compareTo(o2.getClient());
    }
}
