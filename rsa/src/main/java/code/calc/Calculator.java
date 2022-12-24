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

/**
 * calculate() - расчет н/ч для з/н по справочнику
 */
public class Calculator {

    public static Double calculate(Guide guide, Order order) {
        Double result = WorkService.countWorksContains(order.getWorks(), List.of("ОКР","ВЫКРАС", "КОЛЕР"));
        for(Nabor nabor : guide.getDetNabors()) {
            List<String> detailNames = new ArrayList<>();
            detailNames.addAll(nabor.getDetNames());
            Double hours = nabor.getCount();
            if(OrderService.isOrderContainsAll(order, detailNames)) {
                return hours + result;
            }
        }
        return result;
    }

}
