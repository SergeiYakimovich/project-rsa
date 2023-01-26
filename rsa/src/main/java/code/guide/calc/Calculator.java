package code.guide.calc;

import code.guide.element.Guide;
import code.guide.element.Nabor;
import code.guide.element.Order;
import code.guide.service.OrderService;
import code.guide.service.WorkService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .map(x -> String.valueOf(x))
                .collect(Collectors.groupingBy(x -> x, LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .sorted((x1,x2) -> {
                    long n = x2.getValue() - x1.getValue();
                    if(n != 0) return (int) n;
                    Double d1 = Double.parseDouble(x1.getKey());
                    Double d2 = Double.parseDouble(x2.getKey());
                    return d1.compareTo(d2);
                })
                .findFirst()
                .map(x -> Double.parseDouble(x.getKey()))
                .get();

//                .mapToDouble(x -> x)
//                .min().getAsDouble();
    }

    public static double countHoursForNabor(Collection<Double> list) {
        return  list.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.groupingBy(x -> x, LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .sorted((x1,x2) -> {
                    long n = x2.getValue() - x1.getValue();
                    if(n != 0) return (int) n;
                    Double d1 = Double.parseDouble(x1.getKey());
                    Double d2 = Double.parseDouble(x2.getKey());
                    return d1.compareTo(d2);
                        })
                .findFirst()
                .map(x -> Double.parseDouble(x.getKey()))
                .get();

//                .mapToDouble(x -> x)
//                .min().getAsDouble();
//                .average().getAsDouble();
    }

}
