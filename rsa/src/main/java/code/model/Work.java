package code.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Work {

    private String name;
    private Double count;

    @Override
    public String toString() {
        return "Work { " + "name=" + name + "   count=" + count + " }\n";
    }

}
