package code.guide.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * базовый (родительский) класс для описания наборов з/ч
 */
@Getter
@Setter
@NoArgsConstructor
public class SimpleNabor implements Comparable {
    public List<String> detNames = new ArrayList<>(); // список имен з/ч

    public void setDetNames(Collection<String> detNames) {
        this.detNames = new ArrayList<>();
        this.detNames.addAll(detNames.stream().sorted().collect(Collectors.toList()));
    }

    @Override
    public int compareTo(Object o) {
        SimpleNabor o2 = (SimpleNabor) o;
        SimpleNabor o1 = this;
        int lengthDifference = o2.getDetNames().size() - o1.getDetNames().size();
        if (lengthDifference != 0) return lengthDifference;
        String s1 = o1.getDetNames().stream().sorted().collect(Collectors.joining());
        String s2 = o2.getDetNames().stream().sorted().collect(Collectors.joining());
        return s1.compareTo(s2);
    }

    @Override
    public String toString() {
        return "detNames=" + detNames.stream().collect(Collectors.joining(";")) + "\n";
    }
}
