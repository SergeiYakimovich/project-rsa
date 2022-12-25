package code.element;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Nabor implements Comparable{
    private TreeSet<String> detNames = new TreeSet<>();
    private Double count = 0.0;

    public Nabor(Collection<String> detNames, Double count) {
        this.detNames = new TreeSet<>();
        this.detNames.addAll(detNames);
        this.count = count;
    }

    public void setDetNames(Collection<String> detNames) {
        this.detNames = new TreeSet<>();
        this.detNames.addAll(detNames);
    }

    @Override
    public int compareTo(Object o) {
        Nabor o2 = (Nabor) o;
        Nabor o1 = this;
        int lengthDifference = o2.getDetNames().size() - o1.getDetNames().size();
        if (lengthDifference != 0) return lengthDifference;
        String s1 = o1.getDetNames().stream().collect(Collectors.joining());
        String s2 = o2.getDetNames().stream().collect(Collectors.joining());
        return s2.compareTo(s1);
    }

    @Override
    public String toString() {
        return "detNames=" + detNames.stream().collect(Collectors.joining(";")) +
                "\ncount=" + String.format("%.2f", count) + "\n";
    }

    public String show(List<String> mainDetails) {
        String main = detNames.stream()
                .filter(x -> mainDetails.contains(x))
                .collect(Collectors.joining(";"));
        String simple = detNames.stream()
                .filter(x -> !mainDetails.contains(x))
                .collect(Collectors.joining(";"));

        return "Основные детали = " + main + "\nПрочие детали = " + simple +
                "\nН/ч для набора = " + String.format("%.2f", count) + "\n";
    }
}
