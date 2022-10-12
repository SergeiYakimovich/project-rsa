package code.utils;

import code.model.Order;

import java.util.List;

public class Handler {

    public static void handle() throws Exception {

//        Order order = rsa.Parser.getOrderFromFile("src/main/resources/2 (110).csv");
//        System.out.println(order.toString());
//        System.out.println(order.getWorksCount());

        List<Order> orders = Parser.getOrdersFromDirectory("src/main/resources/");
        for(Order order : orders) {
            System.out.println(order.toString());
        }
        System.out.println(orders.size());
    }

    public static Double countAverage(List<Order> orders) {
        Double detailsSum = 0.0;
        Double worksCount = 0.0;
        for(Order order : orders) {
            detailsSum += order.getDetailsSum();
            worksCount += order.getWorksCount();
        }
        return detailsSum / worksCount;
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
