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

@Getter
@Setter
public class PaintGuide {
    private String name = "PaintGuide";

    private TreeSet<PaintNabor> detNaborSets = new TreeSet<>(MyComparators.naborComparator); // наборы з/ч

    public void addNaborSets(PaintNabor newNabor) {
        detNaborSets.add(newNabor);
    }

}
