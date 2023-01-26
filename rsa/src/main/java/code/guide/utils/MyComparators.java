package code.guide.utils;

import code.guide.element.Nabor;

import java.util.Comparator;
import java.util.stream.Collectors;

public class MyComparators {
    public static Comparator<Nabor> naborComparator = new Comparator<Nabor>() {
        @Override
        public int compare(Nabor o1, Nabor o2) {
            int lengthDifference = o2.getDetNames().size() - o1.getDetNames().size();
            if (lengthDifference != 0) return lengthDifference;
            String s1 = o1.getDetNames().stream().sorted().collect(Collectors.joining());
            String s2 = o2.getDetNames().stream().sorted().collect(Collectors.joining());
            return s1.compareTo(s2);
        }
    };
}
