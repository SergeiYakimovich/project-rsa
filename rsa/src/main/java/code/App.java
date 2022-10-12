package code;

import code.model.Detail;
import code.model.Order;
import code.model.Work;
import code.check.Checker;
import code.service.OrderService;
import code.utils.Handler;
import code.utils.Parser;
import code.check.Result;

import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        List<Order> orders;
        Order order;
        Detail detail;
        List<Detail> details;
        Work work;
        List<Work> works;
        Result result;

//        order = Parser.getOrderFromFile("../data/2 (110).csv");
//        orders = Parser.getOrdersFromDirectory("../data/");
//
//        List<Result> list = Checker.checkOrders(orders);
//        Checker.showResults(list);


//        int n = 10;
//        System.out.println(n + "   " + Handler.countVariants(n) + "   " + Handler.factorial(n));

//        order = Parser.getOrderFromFile("../data/2 (110).csv");
//        System.out.println(OrderService.isOrderContainsDetail(order, "СТЕКЛО ДВЕРИ"));
//        System.out.println(OrderService.isOrderContainsAll(order, List.of("СТЕКЛО ДВЕРИ", "АБСОРБЕР БАМПЕРА")));
//        System.out.println(OrderService.isOrderContainsDetail(order, "СТЕКЛО ДВРИ"));
//        System.out.println(OrderService.isOrderContainsAll(order, List.of("СТКЛО ДВЕРИ", "АБСОРБЕР БМПЕРА")));

//        orders = Parser.getOrdersFromDirectory("../data/");
//        OrderService.showOrders(OrderService.getOrdersContainsDetail(orders, "СТЕКЛО ДВЕРИ"));
//        OrderService.showOrders(OrderService.getOrdersContainsDetail(orders, "БА"));
//        OrderService.showOrders(OrderService.getOrdersContainsAll(orders, List.of("СТЕКЛО ДВЕРИ", "РУЧКА ДВЕРИ")));
//        OrderService.showOrders(OrderService.getOrdersContainsAll(orders, List.of("ОБЛИЦОВКА БАМПЕРА", "АБСОРБЕР БАМПЕРА")));

    }

}
