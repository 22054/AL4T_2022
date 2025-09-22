package be.ecam.basics.exercises;

import java.util.Iterator;
import java.util.List;

public class ListUtils {
    public static List<String> removeShortStrings(List<String> list, int minLen) {
        Iterator<String> i = list.iterator();
        while (i.hasNext()) {
            String s = i.next(); // must be called before you can call i.remove()
            if (s.length() < minLen) {
                i.remove();
            }
        }
        return list;
    }
}
