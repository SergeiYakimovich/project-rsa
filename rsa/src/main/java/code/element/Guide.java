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
    private String name = "Polo";
    List<String> mainDetails;
    List<String> notMainDetails;
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
        builder.append("Спавочник для модели   " + name + "\n");
        builder.append("\nНаборы з/ч :\n");
        int i = 1;
        for(Nabor nabor : detNaborSets) {
            builder.append("\n№" + i + "\n" + nabor.show(mainDetails));
            i++;
        }
        builder.append("\nОдиночные з/ч :\n");
        i = 1;
        for(Map.Entry<String, Double> nabor : getDetSingles().entrySet()) {
            builder.append("\n№" + i + "\nДеталь = " + nabor.getKey() + "\nН/ч = "
                    + String.format("%.2f", nabor.getValue()) + "\n");
            i++;
        }
        return builder.toString();
    }
}
