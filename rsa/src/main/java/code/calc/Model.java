package code.calc;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Модель для расчета н/ч
 * singleDetails - з/ч с кол-вом н/ч
 * setDetails - наборы з/ч с кол-вом н/ч
 */

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
