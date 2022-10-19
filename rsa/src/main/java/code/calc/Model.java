package code.calc;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Model {

    private String name;

    List<Map<List<String>, Double>> listSingle;

    List<Map<List<String>, Double>> listSet;

    @Override
    public String toString() {
        return "Model { " + "name=" + name + "   listSingle=" + listSingle.size()
                + "   listSet=" + listSet.size() + " }\n";
    }

}
