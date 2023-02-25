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

/**
 * Класс для расчета н/ч для з/н по справочнику
 */
public class Calculator {
    /**
     * расчет н/ч для з/н по справочнику
     * @param guide -справочник
     * @param order - з/н
     * @return - количество н/ч
     */
    public static Double calculate(Guide guide, Order order) {
        Double result = 0.0;
        for(Nabor nabor : guide.getDetNaborSets()) {
            Double hours = nabor.getCount();
            if(OrderService.isOrderContainsAll(order, nabor.getDetNames())) {
                return hours + result;
            }
        }
        for(Map.Entry<String, Map<String, Double>> nabor : guide.getDetSingles().entrySet()) {
            Double hours = countHoursForSingles(nabor.getValue().values());
            if(OrderService.isOrderContainsAll(order, List.of(nabor.getKey()))) {
                result += hours;
            }
        }
        return result;
    }

    /**
     * расчет н/ч в случае коллизий для одиночных з/ч
     * @param list - список вариантов
     * @return - количество н/ч
     */
    public static double countHoursForSingles(Collection<Double> list) {
        if(list.size() > 5) {
            return countMaxFrequency(list);
        } else {
            return countMedian(list);
        }
    }

    /**
     * расчет н/ч в случае коллизий для наборов з/ч
     * @param list - список вариантов
     * @return - количество н/ч
     */
    public static double countHoursForNabor(Collection<Double> list) {
        if(list.size() > 5) {
            return countMaxFrequency(list);
        } else {
            return countMedian(list);
        }
    }

    /**
     * расчет н/ч в случае коллизий для наборов з/ч - медианное значение
     * @param list - список вариантов
     * @return - количество н/ч
     */
    public static double countMedian(Collection<Double> list) {
        if(list.size() == 0) {
            return 0;
        }
        double[] values = list.stream()
                .sorted()
                .mapToDouble(x -> x)
                .toArray();

        if(values.length == 1) return values[0];
        int n = values.length / 2;
        if(values.length % 2 == 0) {
            return (values[n - 1] + values[n]) / 2;
        } else {
            return values[n];
        }
    }

    /**
     * расчет н/ч в случае коллизий для наборов з/ч - минимальное из наиболее часто встречающихся
     * @param list - список вариантов
     * @return - количество н/ч
     */
    public static double countMaxFrequency(Collection<Double> list) {
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
    }

    /**
     * расчет н/ч в случае коллизий для наборов з/ч - минимальное значение
     * @param list - список вариантов
     * @return - количество н/ч
     */
    public static double countMin(Collection<Double> list) {
        return list.stream()
                .mapToDouble(x -> x)
                .min().getAsDouble();
    }

    /**
     * расчет н/ч в случае коллизий для наборов з/ч - среднее значение
     * @param list - список вариантов
     * @return - количество н/ч
     */
    public static double countAvr(Collection<Double> list) {
        return list.stream()
                .mapToDouble(x -> x)
                .average().getAsDouble();
    }

}
