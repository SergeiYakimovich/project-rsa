package code.utils;

import code.App;
import code.element.Order;
import code.parse.DetailsParser;
import code.parse.OrderParser;
import code.service.OrderService;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.writeString;

public class Handler {

    public static void handle() throws Exception {
        System.out.println();
    }

    public static void makeModelSingle(String dir) throws Exception{
        List<Order> orders;
        String text = "";
        orders = OrderParser.getOrdersFromDirectory(dir);
        orders = orders.stream()
                .sorted((o1, o2) -> o1.getDetails().get(0).getName().compareTo(o2.getDetails().get(0).getName()))
                .collect(Collectors.toList());
        for(Order nextOrder : orders) {
            text += nextOrder.getDetails().get(0).getName()
                    + ";" + nextOrder.getWorksCount()
//                    + "   " + nextOrder.getName()
                    + "\n";
        }
        writeString(Paths.get(App.MODEL_DIR + "out-single.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);
    }

    public static void makeModelSet(String dir, String fileWithDetails) throws Exception{
        List<Order> orders = OrderParser.getOrdersFromDirectory(dir);
        List<List<String>> detailsList = DetailsParser.getDetailsFromFile(App.MODEL_DIR + fileWithDetails);
        String text = "";
        for(List<String> details : detailsList) {
            Double hours = countAverage(orders, details);
            String str = details.stream()
                    .collect(Collectors.joining(";"));
            text += str + ";" + hours + "\n";
        }
        writeString(Paths.get(App.MODEL_DIR + "out-set.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);
    }

    public static Double countAverage(List<Order> orders, List<String> detailNames) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        if(ordersContainsNames.size() == 0) {
            System.out.println("Not found ");
            return 0.0;
        }
        System.out.println("Find in " + ordersContainsNames.size());
        Double worksCount = 0.0;
        for(Order order : ordersContainsNames) {
            worksCount += order.getWorksCount();
        }
        return worksCount / ordersContainsNames.size();
    }

    public static Integer countVariants(Integer n) {
        Integer sum = 0;
        for(int i = 1; i <=n; i++) {
            sum += factorial(n)/factorial(i)/factorial(n - i);
        }
        return sum;
    }

    public static Integer factorial(Integer n) {
        if(n < 2) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

}
