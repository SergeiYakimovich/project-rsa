package code.guide.service;

import code.guide.element.Detail;
import code.guide.element.Order;
import code.guide.element.Work;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * класс для работы с з/н
 * makeOrder() - создать пустой з/н (имя и пустые списки з/ч и работ)
 * makeSimpleOrder() - создать з/н из списка имен з/ч
 */
public class OrderService {

    public static List<Order> getOrdersContainsDetail(List<Order> orders, String str) {
        return orders.stream()
                .filter(x -> isOrderContainsDetail(x, str))
                .collect(Collectors.toList());
    }

    /**
     * получить список з/н содержащих все имена деталей
     * @param orders - первоначальный список з/н
     * @param list - список имен з/ч
     * @return - отфильтрованный список з/н
     */
    public static List<Order> getOrdersContainsAll(List<Order> orders, List<String> list) {
        return orders.stream()
                .filter(x -> isOrderContainsAll(x, list))
                .collect(Collectors.toList());
    }

    /**
     * проверка содержит ли з/н имя з/ч
     * @param order - з/н
     * @param str - имя з/ч
     * @return - true, если содержит
     */
    public static boolean isOrderContainsDetail(Order order, String str) {
        if(DetailService.isDetailsContains(order.getDetails(), str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * проверка содержит ли з/н все имена з/ч
     * @param order - з/н
     * @param list - список имен з/ч
     * @return - true, если содержит
     */
    public static boolean isOrderContainsAll(Order order, Collection<String> list) {
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

    /**
     * создать з/н из списка имен з/ч
     * @param elements - список имен з/ч
     * @param fileName - имя файла
     * @return - з/н
     */
    public static Order makeSimpleOrder(List<String> elements, String fileName) {
        Order result = makeOrder(fileName);
        List<Detail> details = new ArrayList<>();
        List<Work> works = new ArrayList<>();
        for (String element : elements) {
            Detail newDetail = new Detail("",element.trim(),0.0, 0.0);
            details.add(newDetail);
        }
        Work newWork = new Work("Работы", 0.0);
        works.add(newWork);
        result.setDetails(details);
        result.setWorks(works);
        return result;
    }

    /**
     * создать пустой з/н
     * @param str - имя файла
     * @return - пустой з/н
     */
    public static Order makeOrder(String str) {
        Order order = new Order();
        order.setDetails(new ArrayList<>());
        order.setWorks(new ArrayList<>());
        order.setCar("Unknown");
        order.setCompany("Unknown");
        String[] mas = str.split(Pattern.quote("\\"));
        String pref = mas.length > 1 ? mas[mas.length - 2] + "\\" : "";
        order.setName(mas[mas.length - 1]
                .replaceAll(".csv","").replaceAll(".xml",""));
        return order;
    }
}
