package code.guide.element;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Work {

    private String name;
    private Double count;

    @Override
    public String toString() {
        return "Work { " + "name=" + name + "   count=" + count + " }\n";
    }

}
