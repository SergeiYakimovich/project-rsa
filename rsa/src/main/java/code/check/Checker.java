package code.check;

import code.calc.Calculator;
import code.element.Guide;
import code.element.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * checkOneOrder() - проверка одного з/н по справочнику
 * checkOrders() - проверка списка з/н по справочнику
 * showResults() - вывод результата проверки
 * checkTestOrders() - проверка тестовых запросов
 */
public class Checker {

    public static Result checkOneOrder(Guide guide, Order order) {
        Result result = new Result();
        Double hours = Calculator.calculate(guide, order);
        Double expected = order.getWorksCount();
        result.setHours(hours);
        result.setExpected(expected);
        result.setOrderName(order.getName());
        result.setDiff(Math.abs(hours - expected));
        result.setDiffInPercent(countDiff(hours,expected));
        return result;
    }

    public static List<Result> checkOrders(Guide guide, List<Order> orders) {
        List<Result> list = new ArrayList<>();
        for(Order order : orders) {
            Result result = checkOneOrder(guide, order);
            list.add(result);
        }
        return list.stream()
                .sorted((o1, o2) -> o2.getDiffInPercent().compareTo(o1.getDiffInPercent()))
                .collect(Collectors.toList());
    }

    public static void showResults(List<Result> list, int number) {
        System.out.println("\nПроверено на " + list.size() + " з/н");
        System.out.println("Среднее отклонение в % = " + String.format("%.1f", countAvrDiffInPercent(list)) + "%");
        System.out.println("Медианное отклонение в % = "
                + String.format("%.1f", list.get(list.size() / 2).getDiffInPercent()) + "%");
        long countBad = list.stream()
                .filter(x -> x.getDiff() > 0.01)
                .count();
        System.out.println("С ошибками = " + countBad + " из " + list.size() + " з/н");
        System.out.println("Точно посчитано = " + (list.size() - countBad) + " из " + list.size() + " з/н");
        System.out.println("Максимальное отклонение :");
        int n = list.size() > number ? number : list.size();
        for(int i = 0; i < n; i++) {
            System.out.println(list.get(i).toString());
        }
    }

    public static void checkTestOrders(Guide guide, List<Order> orders) {
        System.out.println("\nРезультаты расчета :");
        for(Order order : orders) {
            double result = Calculator.calculate(guide, order);
            System.out.println(order.getName() + " = " + String.format("%.2f",result) + " н/ч");
        }
    }


    public static Double countAvrDiffInPercent(List<Result> list) {
        Double sum = 0.0;
        int count = 0;
        for(Result result : list) {
            sum += result.getDiffInPercent();
            count++;
        }
        return sum / count;
    }

    private static Double countAvrDiff(List<Result> list) {
        Double sum = 0.0;
        int count = 0;
        for(Result result : list) {
            sum += result.getDiff();
            count++;
        }
        return sum / count;
    }
    public static Double countDiff(Double x1, Double x2)
    {   Double x = (1 - x1 / x2) * 100;
        return x < 0 ? -x : x;
    }

}
