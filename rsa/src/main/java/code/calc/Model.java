package code.calc;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Model {

    private String name;

    Map<List<String>, Double> singleDetails;

    Map<List<String>, Double> setDetails;

    @Override
    public String toString() {
        return "Model { " + "name=" + name + "   listSingle=" + singleDetails.size()
                + "   listSet=" + setDetails.size() + " }\n";
    }

}
