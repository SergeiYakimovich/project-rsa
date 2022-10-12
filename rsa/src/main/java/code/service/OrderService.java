package code.service;

import code.model.Detail;
import code.model.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    public static List<Order> getOrdersContainsDetail(List<Order> orders, String str) {
        return orders.stream()
                .filter(x -> isOrderContainsDetail(x, str))
                .collect(Collectors.toList());
    }

    public static List<Order> getOrdersContainsAll(List<Order> orders, List<String> list) {
        return orders.stream()
                .filter(x -> isOrderContainsAll(x, list))
                .collect(Collectors.toList());
    }

    public static boolean isOrderContainsDetail(Order order, String str) {
        if(DetailService.isDetailsContains(order.getDetails(), str)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOrderContainsAll(Order order, List<String> list) {
        if(DetailService.isDetailsContainsAll(order.getDetails(), list)) {
            return true;
        } else {
            return false;
        }
    }

    public static void showOrders(List<Order> orders) {
        for(Order order : orders) {
            System.out.println(order.toString());
        }
    }

}
