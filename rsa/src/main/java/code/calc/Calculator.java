package code.calc;

import code.element.Order;
import code.service.OrderService;
import code.service.WorkService;

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
        Double result = WorkService.countWorksContains(order.getWorks(), List.of("ОКР","ВЫКРАС", "КОЛЕР"));

        if(type == CheckType.ALL || type == CheckType.SET) {
            for(Map.Entry<List<String>, Double> item : model.getSetDetails().entrySet()) {
                List<String> detailNames = item.getKey();
                Double hours = item.getValue();
                if(OrderService.isOrderContainsAll(order, detailNames)) {
//                    && order.getDetails().size() == detailNames.size()
                    return hours + result;
                }
            }
        }

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

    public static void calcOrders(Model model, List<Order> orders, CheckType type) {
        for(Order order : orders) {
            double result = calculate(model, order, type);
            System.out.println(order.getName() + " = " + result);
        }
    }
}
