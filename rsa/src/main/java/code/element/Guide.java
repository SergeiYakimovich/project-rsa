package code.element;

import code.utils.MyComparators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
    private String name = "Polo";
    private TreeSet<Nabor> detNabors = new TreeSet<>(MyComparators.naborComparator);

    public void addNabor(Nabor newNabor) {
        detNabors.add(newNabor);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("name=" + name + "\n");
        int i = 1;
        for(Nabor nabor : detNabors) {
            builder.append(i + "\n" + nabor.toString());
            i++;
        }
        return builder.toString();
    }
}
