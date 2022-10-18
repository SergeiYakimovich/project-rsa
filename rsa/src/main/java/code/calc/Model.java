package code.calc;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Model {

    private String name;

    List<Map<List<String>, Double>> listAll;

    List<Map<List<String>, Double>> listOnly;

    @Override
    public String toString() {
        return "Model { " + "name=" + name + "   listAll=" + listAll.size()
                + "   listOnly=" + listOnly.size() + " }\n";
    }

}
