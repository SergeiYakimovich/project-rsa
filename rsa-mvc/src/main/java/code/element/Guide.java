package code.element;

import code.utils.MyComparators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
    private String name = "Guide";
    List<String> mainDetails;
//    List<String> notMainDetails;
    private TreeSet<Nabor> detNaborSets = new TreeSet<>(MyComparators.naborComparator);
    private TreeMap<String, Double> detSingles = new TreeMap<>();

    public void addNaborSets(Nabor newNabor) {
        detNaborSets.add(newNabor);
    }
    public void addSingles(String name, Double count) {
        detSingles.put(name, count);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Справочник для модели   " + name + "\n");
        builder.append("Наборы з/ч = " + detNaborSets.size() + "\n");
        builder.append("Одиночные з/ч = " + detSingles.size() + "\n");
        return builder.toString();
    }
}
