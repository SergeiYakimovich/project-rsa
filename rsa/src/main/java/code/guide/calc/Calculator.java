package code.guide.calc;

import code.guide.element.Guide;
import code.guide.element.Nabor;
import code.guide.element.Order;
import code.guide.service.OrderService;
import code.guide.service.WorkService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static code.guide.utils.MyConsts.COLOR_WORK_NAMES;

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
        for(Map.Entry<String, Map<String, Double>> nabor : guide.getDetSingles().entrySet()) {
            List<String> detailNames = List.of(nabor.getKey());
            Double hours = countHoursForSingles(nabor.getValue().values());
            if(OrderService.isOrderContainsAll(order, detailNames)) {
                result += hours;
            }
        }
        return result;
    }

    public static double countHoursForSingles(Collection<Double> list) {
        return  list.stream()
                .mapToDouble(x -> x)
                .min().getAsDouble();
    }

    public static double countHoursForNabor(Collection<Double> list) {
        return  list.stream()
                .mapToDouble(x -> x)
                .min().getAsDouble();
//                .average().getAsDouble();
    }

}
