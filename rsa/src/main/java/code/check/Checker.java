package code.check;

import code.calc.Calculator;
import code.calc.Model;
import code.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Checker {

    public static Result checkOneOrder(Model model, Order order, Calculator.CheckType type) {
        Result result = new Result();
        Double hours = Calculator.calculate(model, order, type);
        Double expected = order.getWorksCount();
        result.setHours(hours);
        result.setExpected(expected);
        result.setOrderName(order.getName());
        result.setDiff(Math.abs(hours - expected));
        result.setDiffInPercent(countDiff(hours,expected));
        return result;
    }

    public static List<Result> checkOrders(Model model, List<Order> orders, Calculator.CheckType type) {
        List<Result> list = new ArrayList<>();
        for(Order order : orders) {
            Result result = checkOneOrder(model, order, type);
            list.add(result);
        }
        return list.stream()
                .sorted((o1, o2) -> o2.getDiffInPercent().compareTo(o1.getDiffInPercent()))
                .collect(Collectors.toList());
    }

    public static void showResults(List<Result> list) {

//        for(int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).toString());
//        }

        System.out.println("Checked on " + list.size() + " orders");
//        System.out.println("Average difference = " + String.format("%.1f", countAvrDiff(list)));
        System.out.println("Average difference in % = " + String.format("%.1f", countAvrDiffInPercent(list)));
        System.out.println("Median difference in % = " + String.format("%.1f", list.get(list.size() / 2).getDiffInPercent()));

        System.out.println("Maximum deviation :");
        int n = list.size() > 5 ? 5 : list.size();
        for(int i = 0; i < n; i++) {
            System.out.println(list.get(i).toString());
        }
    }

    private static Double countAvrDiffInPercent(List<Result> list) {
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
