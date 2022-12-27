package code.utils;

import code.model.Detail;
import code.model.Order;
import code.model.Work;
import code.repository.DetailRepository;
import code.repository.OrderRepository;
import code.repository.WorkRepository;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readString;

public class OrderUtils {

    public static Order getOrderFromFile(String fileName) throws Exception {
        List<Detail> details = new ArrayList<>();
        List<Work> works = new ArrayList<>();
        Order result = Order.makeOrder(fileName);
        String text;
        try {
            text = readString(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("!!! Ошибка чтения из файла " + fileName);
            return null;
        }
        String[] stroki = text.split("\n");
        for(int i = 1; i < stroki.length - 1; i++) {
            String[] mas = new String[4];
            mas = stroki[i].split(";");
            if(mas[0] != "") {
                Detail newDetail = Detail.makeDetail(mas);
                newDetail.setOrder(result);
                details.add(newDetail);
                //                System.out.println(newDetail.toString());
            } else {
                Work newWork = Work.makeWork(mas);
                newWork.setOrder(result);
                works.add(newWork);
                //                System.out.println(newWork.toString());
            }
        }
        result.setDetails(details);
        result.setWorks(works);
        //        System.out.println(result.toString());
        return result;
    }

    public static List<Order> getOrdersFromDirectory(String dir) throws Exception {
        List<Order> orders = new ArrayList<>();
        File[] files = new File(dir).listFiles();
        for(File file : files) {
            String s = file.toString();
            Order order = getOrderFromFile(file.toString());
            if (order != null && !order.isOrderEmpty()) {
                orders.add(order);
            }
        }
        System.out.println("Считано " + orders.size() + " ордеров из " + files.length);
        return orders;
    }

    public static void SQLtestOrder(ConfigurableApplicationContext context) {
        WorkRepository workRepository = context.getBean(WorkRepository.class);
        DetailRepository detailRepository = context.getBean(DetailRepository.class);
        OrderRepository orderRepository = context.getBean(OrderRepository.class);
        List<Order> orders;
        Order order;
        Detail detail;
        List<Detail> details;
        Work work;
        List<Work> works;

        try {
            orders = OrderUtils.getOrdersFromDirectory("../data/all/150/");
        } catch (Exception e) {
            System.out.println("Ошибка чтения");
            return;
        }

        orderRepository.save(orders.get(0));
        orderRepository.save(orders.get(1));
        orderRepository.save(orders.get(2));

        orders = orderRepository.findAll();
        details = detailRepository.findAllByOrderName("..\\data\\all\\150\\2 (100).csv");
        works = workRepository.findAllByOrderId(2l);
        order = orders.get(0);
        detail = details.get(0);
        work = works.get(0);

        System.out.println(order.toString());
        System.out.println(detail.toString());
        System.out.println(work.toString());
        System.out.println(orders.size());
        System.out.println(details.size());
        System.out.println(works.size());
    }

}
