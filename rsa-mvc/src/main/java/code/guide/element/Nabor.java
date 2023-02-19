package code.guide.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static code.guide.calc.Calculator.countHoursForNabor;

/**
 * класс для описания наборов з/ч
 */
@Getter
@Setter
@NoArgsConstructor
public class Nabor extends SimpleNabor {
    private Double count = 0.0; // количество н/ч для справочника
    private Map<String, Double> allVariants = new HashMap<>(); // варианты для расчета в случае коллизий


    public Nabor(Collection<String> detNames, Map<String, Double> variants) {
        this.detNames = new ArrayList<>();
        this.detNames.addAll(detNames.stream().sorted().collect(Collectors.toList()));
        this.allVariants = variants;
        this.count = countHoursForNabor(variants.values());
    }

    @Override
    public String toString() {
        return "detNames=" + detNames.stream().collect(Collectors.joining(";")) +
                "\ncount=" + String.format("%.2f", count) + "\n";
    }

}
