package code.guide.element;

import code.guide.utils.MyComparators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * класс для описания справочника
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
    private String name = "Guide";
//    List<String> mainDetails;
//    List<String> notMainDetails;
    private TreeSet<Nabor> detNaborSets = new TreeSet<>(MyComparators.naborComparator); // наборы з/ч
    private TreeMap<String, Map<String, Double>> detSingles = new TreeMap<>(); // единичные з/ч

    /**
     * добавление набора с список
     * @param newNabor - добавляемый набор
     */
    public void addNaborSets(Nabor newNabor) {
        detNaborSets.add(newNabor);
    }

    /**
     * добавление единичной з/ч
     * @param name - имя з/ч
     * @param variants - список вариантов расчета для з/ч
     */
    public void addSingles(String name, Map<String, Double> variants) {
        if(this.detSingles.containsKey(name)) {
            Map<String, Double> allVariants = this.detSingles.get(name);
            allVariants.putAll(variants);
        } else {
            Map<String, Double> allVariants = new HashMap<>();
            allVariants.putAll(variants);
            detSingles.put(name, allVariants);
        }
    }

}
