
/**
 * Created by tadhg on 05/12/14.
 */

import java.util.Comparator;

public class CustomComparator implements Comparator<timeTable.taskObj> {
    @Override
    public int compare(timeTable.taskObj o1, timeTable.taskObj o2) {
        return o1.startTime.compareTo(o2.startTime);
    }



}