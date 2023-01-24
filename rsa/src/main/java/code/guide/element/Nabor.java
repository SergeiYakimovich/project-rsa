package code.guide.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static code.guide.calc.Calculator.countHoursForNabor;

@Getter
@Setter
@NoArgsConstructor
public class Nabor implements Comparable{
    private TreeSet<String> detNames = new TreeSet<>();
    private Double count = 0.0;
    private Map<String, Double> allVariants = new HashMap<>();


    public Nabor(Collection<String> detNames, Map<String, Double> variants) {
        this.detNames = new TreeSet<>();
        this.detNames.addAll(detNames);
        this.allVariants = variants;
        this.count = countHoursForNabor(variants.values());
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

}
