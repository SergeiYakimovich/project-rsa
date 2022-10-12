package code.utils;

import code.model.Detail;
import code.model.Order;
import code.model.Work;

import java.io.File;
import static java.nio.file.Files.readString;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {

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
                details.add(newDetail);
//                System.out.println(newDetail.toString());
            } else {
                Work newWork = Work.makeWork(mas);
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

}
