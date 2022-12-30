package code.guide.element;

import code.guide.utils.MyComparators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.TreeMap;
import java.util.TreeSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
    private String name = "Guide";
//    List<String> mainDetails;
//    List<String> notMainDetails;
    private TreeSet<Nabor> detNaborSets = new TreeSet<>(MyComparators.naborComparator);
    private TreeMap<String, Double> detSingles = new TreeMap<>();

    public void addNaborSets(Nabor newNabor) {
        detNaborSets.add(newNabor);
    }
    public void addSingles(String name, Double count) {
        detSingles.put(name, count);
    }

}
