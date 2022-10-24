package code.service;

import code.element.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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

    public static Order makeOrder(String str) {
        Order order = new Order();
        order.setDetails(new ArrayList<>());
        order.setWorks(new ArrayList<>());
        order.setCar("VW");
        order.setCompany("Unknown");
        String[] mas = str.split(Pattern.quote("\\"));
        order.setName(mas[mas.length - 1]);
        return order;
    }
}
