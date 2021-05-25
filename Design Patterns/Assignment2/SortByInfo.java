package HW02;

import java.util.Comparator;

public class SortByInfo implements Comparator<Mobile> {
    @Override
    public int compare(Mobile o1, Mobile o2) {
        return o1.getInfo().compareTo(o2.getInfo());
    }
}
