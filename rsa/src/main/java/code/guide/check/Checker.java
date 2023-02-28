package code.guide.check;

import code.guide.calc.Calculator;
import code.guide.element.Guide;
import code.guide.element.Order;
import code.guide.utils.MyConsts;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.writeString;

/**
 * Класс для проверки результатов расчетов
 */
public class Checker {

    /**
     * сверка с эталоном расчета по справочнику одного з/н
     * @param guide - справочник
     * @param order - з/н
     * @return - объект класса Result с результатом
     */
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

    /**
     * сверка с эталонными значениями расчетов по справочнику для списка з/н
     * @param guide - справочник
     * @param orders - список з/н
     * @return - список объектов класса Result с результатами
     */
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

    /**
     * вывод результата проверки
     * @param list - список объектов класса Result с результатами
     */
    public static void showResults(List<Result> list) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("\nПроверено на " + list.size() + " з/н");
        builder.append("\nСредняя точность в % = " + String.format("%.1f", 100 - countAvrDiffInPercent(list)) + "%");
        builder.append("\nМедианное отклонение в % = "
                + String.format("%.1f", list.get(list.size() / 2).getDiffInPercent()) + "%");
        int countBad = (int) list.stream()
                .filter(x -> x.getDiff() > 0.01)
                .count();
        builder.append("\nТочно посчитано = " + (list.size() - countBad) + " из " + list.size() + " з/н ("
                + String.format("%.1f", (double) (list.size() - countBad) / list.size() * 100) + "%)");
        builder.append("\nНеточно = " + countBad + " из " + list.size() + " з/н ("
                + String.format("%.1f", (double) countBad / list.size() * 100) + "%)");
        builder.append("\nМаксимальное отклонение :");
        int i = 0;
        while (i < list.size() && list.get(i).getDiff() > 0.01) {
            builder.append("\n" + list.get(i).toString());
            i++;
        }
        System.out.println("\nРезультаты проверки в файле - " + MyConsts.GUIDE_CHECK_FILE);
        writeString(Paths.get(MyConsts.GUIDE_CHECK_FILE), builder, StandardCharsets.UTF_8);
    }

    /**
     * расчет н/ч для списка тестовых запросов и вывод результата
     * @param guide - справочник
     * @param orders - список тестовых запросов
     */
    public static void countTestOrders(Guide guide, List<Order> orders) {
        System.out.println("\nРезультаты расчета :");
        for(Order order : orders) {
            double result = Calculator.calculate(guide, order);
            System.out.println(order.getName() + " = " + String.format("%.2f",result) + " н/ч");
        }
    }

    /**
     * расчет средней разницы в %
     * @param list - список результатов
     * @return среднюю разницу в %
     */
    public static Double countAvrDiffInPercent(List<Result> list) {
        Double sum = 0.0;
        for(Result result : list) {
            sum += result.getDiffInPercent();
        }
        return sum / list.size();
    }

    /**
     * расчет средней разницы
     * @param list - список результатов
     * @return среднюю разницу
     */
    private static Double countAvrDiff(List<Result> list) {
        Double sum = 0.0;
        for(Result result : list) {
            sum += result.getDiff();
        }
        return sum / list.size();
    }

    public static Double countDiff(Double x1, Double x2)
    {   Double x = (1 - x1 / x2) * 100;
        return x < 0 ? -x : x;
    }

}
