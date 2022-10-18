package code.calc;

import code.model.Order;
import code.service.OrderService;

import java.util.List;
import java.util.Map;

public class Calculator {

    public static Double calculate(Model model, Order order) {
//        for(Map<List<String>, Double> map : model.getListOnly()) {
//            Map.Entry<List<String>, Double> entry = map.entrySet().iterator().next();
//            List<String> detailNames = entry.getKey();
//            Double hours = entry.getValue();
//            if(OrderService.isOrderContainsAll(order, detailNames)) {
//                return hours;
//            }
//        }

        Double result = 0.0;
        for(Map<List<String>, Double> map : model.getListAll()) {
            Map.Entry<List<String>, Double> entry = map.entrySet().iterator().next();
            List<String> detailNames = entry.getKey();
            Double hours = entry.getValue();
            if(OrderService.isOrderContainsAll(order, detailNames)) {
                result += hours;
            }
        }
        return result;

    }

}
