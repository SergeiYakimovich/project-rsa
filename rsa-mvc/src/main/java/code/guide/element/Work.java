package code.guide.element;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * класс для описания работ
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Work {

    private String name; // имя
    private Double count; // н/ч

    @Override
    public String toString() {
        return "Work { " + "name=" + name + "   count=" + count + " }\n";
    }

}
