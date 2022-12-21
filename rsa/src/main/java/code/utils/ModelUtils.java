package code.utils;

import code.element.Order;
import code.parse.DetailsParser;
import code.parse.OrderParser;
import code.service.OrderService;
import code.service.WorkService;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static code.App.MODEL_DIR;
import static java.nio.file.Files.writeString;

/**
 * makeModelSingle() - создаем файл для модели Single :
 *                     считываем список единичных (с одной деталью) з/н из директории
 *                     и выводим в файл "деталь;н/ч"
 *
 * makeModelSet() - создаем файл для модели Set :
 *                  считываем список з/н из директории
 *                  считываем список наборов з/ч из файла
 *                  расчитываем среднее кол-во н/ч в списке з/н для каждого набора деталей
 *                  и выводим в файл "набор деталей;н/ч"
 *
 * countAverage() - расчитывает среднее кол-во н/ч в списке з/н для набора деталей
 */
public class ModelUtils {

    public static void makeModelUniqSets(String dir, List<String> notMainDetails) throws Exception{
        List<Order> orders = OrderParser.getOrdersFromDirectory(dir);
        List<List<String>> detailsList = new ArrayList<>();
        List<String> detailsStringList = new ArrayList<>();

        for(Order order : orders) {
            List<String> newDetails = order.getDetails().stream()
                    .map(x -> x.getName())
//                    .filter(x -> isDetailMain(x))
                    .filter(x -> isDetailNotMain(x, notMainDetails))
                    .sorted()
                    .collect(Collectors.toList());
            String newStr = newDetails.stream()
                    .collect(Collectors.joining());
            if(newStr.length() != 0 && !detailsStringList.contains(newStr)) {
                detailsStringList.add(newStr);
                detailsList.add(newDetails);
            }
        }

        detailsList.sort((x1,x2) -> x2.size() - x1.size());
        String text = "";
        for(List<String> details : detailsList) {
            Double hours = countMin(orders, details);
            String str = details.stream()
                    .collect(Collectors.joining(";"));
            text += str + ";" + hours + "\n";
        }
        writeString(Paths.get(MODEL_DIR + "uniq-sets.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);
    }

    public static boolean isDetailMain(String name) {
        List<String> mainDetails = List.of("БАМП","ДВЕР","ПОДКР","СТЕКЛО","РУЧК","ФОНАР","БАГАЖ","РАМА",
                "БАК", "ДИСК","ЛОНЖЕР","ПАНЕЛ");

        for(String main : mainDetails) {
            if(name.contains(main)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isDetailNotMain(String name, List<String> notMainDetails) {
        for(String main : notMainDetails) {
            if(name.contains(main)) {
                return false;
            }
        }
        return true;
    }


    public static Double countUniqAverage(List<Order> orders, List<String> detailNames) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        int listSize = detailNames.size();
        ordersContainsNames = ordersContainsNames.stream()
                .filter(x -> x.getDetails().size() == listSize)
                .collect(Collectors.toList());
        if(ordersContainsNames.size() == 0) {
            System.out.println("Not found ");
            return 0.0;
        }
//        System.out.println("Find in " + ordersContainsNames.size());
        if(ordersContainsNames.size() == 1) {
            return ordersContainsNames.get(0).getWorksCount();
        }
        Double worksCount = 0.0;
//        System.out.print(ordersContainsNames.size() + " совпадения   ");
        for(Order order : ordersContainsNames) {
            worksCount += order.getWorksCount();
//            System.out.print(order.getName() + "   ");
        }
//        System.out.println();
        return worksCount / ordersContainsNames.size();
    }

    public static Double countUniqMin(List<Order> orders, List<String> detailNames) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        int listSize = detailNames.size();
        ordersContainsNames = ordersContainsNames.stream()
                .filter(x -> x.getDetails().size() == listSize)
                .collect(Collectors.toList());
        if(ordersContainsNames.size() == 0) {
            System.out.println("Not found ");
            return 0.0;
        }
//        System.out.println("Find in " + ordersContainsNames.size());
        if(ordersContainsNames.size() == 1) {
            return ordersContainsNames.get(0).getWorksCount();
        }
        Double worksCount = Double.MAX_VALUE;
//        System.out.print(ordersContainsNames.size() + " совпадения   ");
        for(Order order : ordersContainsNames) {
            worksCount = Double.min(worksCount, order.getWorksCount());
//            System.out.print(order.getName() + "   ");
        }
//        System.out.println();
        return worksCount;
    }

    public static Double countAverage(List<Order> orders, List<String> detailNames) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        if(ordersContainsNames.size() == 0) {
            System.out.println("Not found ");
            return 0.0;
        }
//        System.out.println("Find in " + ordersContainsNames.size());
        Double worksCount = 0.0;
        for(Order order : ordersContainsNames) {
            worksCount += order.getWorksCount();
        }
        return worksCount / ordersContainsNames.size();
    }

    public static Double countMin(List<Order> orders, List<String> detailNames) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        if(ordersContainsNames.size() == 0) {
            System.out.println("Not found ");
            return 0.0;
        }
//        System.out.println("Find in " + ordersContainsNames.size());
        Double worksCount = Double.MAX_VALUE;
        for(Order order : ordersContainsNames) {
            worksCount = Double.min(worksCount, order.getWorksCount());
        }
        return worksCount;

    }

    public static void makeModelSingle(String dir) throws Exception{
        List<Order> orders = OrderParser.getOrdersFromDirectory(dir);
        String text = "";
        orders = orders.stream()
                .sorted((o1, o2) -> o1.getDetails().get(0).getName().compareTo(o2.getDetails().get(0).getName()))
                .collect(Collectors.toList());
        for(Order nextOrder : orders) {
            text += nextOrder.getDetails().get(0).getName()
                    + ";" + nextOrder.getWorksCount()
//                    + "   " + nextOrder.getName()
                    + "\n";
        }
        writeString(Paths.get(MODEL_DIR + "out-single.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);
    }

    public static void makeModelSet(String dir, String fileWithDetails) throws Exception{
        List<Order> orders = OrderParser.getOrdersFromDirectory(dir);
        List<List<String>> detailsList = DetailsParser.getDetailsFromFile(MODEL_DIR + fileWithDetails);
        String text = "";
        for(List<String> details : detailsList) {
            Double hours = countAverage(orders, details);
            String str = details.stream()
                    .collect(Collectors.joining(";"));
            text += str + ";" + hours + "\n";
        }
        writeString(Paths.get(MODEL_DIR + "out-set.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);
    }

    public static void makeModelAllSets(String dir) throws Exception{
        List<Order> orders = OrderParser.getOrdersFromDirectory(dir);
        orders.sort((x1,x2) -> x2.getDetails().size() - x1.getDetails().size());

        String text = "";
        for(Order order : orders) {
            Double hours = order.getWorksCount();
            String str = order.getDetails().stream()
                    .map(x -> x.getName())
                    .sorted()
                    .collect(Collectors.joining(";"));
            text += str + ";" + hours + ";" + order.getName() + "\n";
        }
        writeString(Paths.get(MODEL_DIR + "all-sets.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);
    }

}
