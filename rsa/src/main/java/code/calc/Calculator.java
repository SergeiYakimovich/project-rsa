package code.calc;

import code.element.Order;
import code.service.OrderService;

import java.util.List;
import java.util.Map;

/**
 * calculate() - расчет н/ч для з/н по модели
 * SINGLE - суммируем н/ч
 * SET - берем только н/ч для набора
 */
public class Calculator {
    public enum CheckType {
        ALL,
        SET,
        SINGLE
    }

    public static Double calculate(Model model, Order order, CheckType type) {
        if(type == CheckType.ALL || type == CheckType.SET) {
            for(Map.Entry<List<String>, Double> item : model.getSetDetails().entrySet()) {
                List<String> detailNames = item.getKey();
                Double hours = item.getValue();
                if(OrderService.isOrderContainsAll(order, detailNames)) {
                    return hours;
                }
            }
        }
        Double result = 0.0;
        if(type == CheckType.ALL || type == CheckType.SINGLE) {
            for(Map.Entry<List<String>, Double> item : model.getSingleDetails().entrySet()) {
                List<String> detailNames = item.getKey();
                Double hours = item.getValue();
                if(OrderService.isOrderContainsAll(order, detailNames)) {
                    result += hours;
                }
            }
        }
        return result;
    }

}
