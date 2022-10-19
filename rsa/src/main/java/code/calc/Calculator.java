package code.calc;

import code.model.Order;
import code.service.OrderService;

import java.util.List;
import java.util.Map;

public class Calculator {
    public enum CheckType {
        ALL,
        SET,
        SINGLE
    }

    public static Double calculate(Model model, Order order, CheckType type) {
        if(type == CheckType.ALL || type == CheckType.SET) {
            for(Map<List<String>, Double> map : model.getListSet()) {
                Map.Entry<List<String>, Double> entry = map.entrySet().iterator().next();
                List<String> detailNames = entry.getKey();
                Double hours = entry.getValue();
                if(OrderService.isOrderContainsAll(order, detailNames)) {
                    return hours;
                }
            }
        }
        Double result = 0.0;
        if(type == CheckType.ALL || type == CheckType.SINGLE) {
            for(Map<List<String>, Double> map : model.getListSingle()) {
                Map.Entry<List<String>, Double> entry = map.entrySet().iterator().next();
                List<String> detailNames = entry.getKey();
                Double hours = entry.getValue();
                if(OrderService.isOrderContainsAll(order, detailNames)) {
                    result += hours;
                }
            }
        }
        return result;
    }

}
