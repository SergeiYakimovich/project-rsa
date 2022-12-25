package code.calc;

import code.element.Guide;
import code.element.Nabor;
import code.element.Order;
import code.service.OrderService;
import code.service.WorkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static code.App.COLOR_WORK_NAMES;

/**
 * calculate() - расчет н/ч для з/н по справочнику
 */
public class Calculator {

    public static Double calculate(Guide guide, Order order) {
        Double result = WorkService.countWorksContains(order.getWorks(), COLOR_WORK_NAMES);
        for(Nabor nabor : guide.getDetNaborSets()) {
            List<String> detailNames = new ArrayList<>();
            detailNames.addAll(nabor.getDetNames());
            Double hours = nabor.getCount();
            if(OrderService.isOrderContainsAll(order, detailNames)) {
                return hours + result;
            }
        }
        for(Map.Entry<String, Double> nabor : guide.getDetSingles().entrySet()) {
            List<String> detailNames = List.of(nabor.getKey());
            Double hours = nabor.getValue();
            if(OrderService.isOrderContainsAll(order, detailNames)) {
                result += hours;
            }
        }
        return result;
    }

}
