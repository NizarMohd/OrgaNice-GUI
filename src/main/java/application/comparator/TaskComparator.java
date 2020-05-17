package application.comparator;

import javafx.util.Pair;

import java.util.Comparator;

public class TaskComparator implements Comparator<Pair<String, Integer>> {


    @Override
    public int compare(Pair<String, Integer> t1, Pair<String, Integer> t2) {
        Integer val1 = t1.getValue();
        Integer val2 = t2.getValue();

        if(val1 < val2) {
            return -1;
        } else if (val1.equals(val2)) {
            return 0;
        }
        return 1;

    }
}
