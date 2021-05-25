package HW02;

import java.util.Comparator;

public class SortByNumber implements Comparator<Mobile> {
    @Override
    public int compare(Mobile o1, Mobile o2) {
        return o1.getNumber().compareTo(o2.getNumber());
    }
}
