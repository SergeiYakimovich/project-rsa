package code.utils;

import code.calc.Model;
import code.model.Detail;
import code.model.Order;
import code.model.Work;
import code.service.DetailService;
import code.service.OrderService;
import code.service.WorkService;

import java.io.File;
import static java.nio.file.Files.readString;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static List<List<String>> getDetailsFromFile(String fileName) throws Exception {
        List<List<String>> detailsList = new ArrayList<>();
        String text;
        try {
            text = readString(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("!!! Ошибка чтения из файла " + fileName);
            return null;
        }
        String[] stroki = text.split("\n");
        for(int i = 0; i < stroki.length; i++) {
            String[] mas = stroki[i].split(";");
            if(mas.length != 0) {
                List<String> details = Arrays.asList(mas);
                detailsList.add(details);
            }
        }
        return detailsList;
    }
    public static Model makeModel(String fileAll, String fileOnly) throws Exception {
        Model model = new Model();
        model.setName(fileAll + "+" + fileOnly);
        model.setListAll(getModel(fileAll));
        model.setListOnly(getModel(fileOnly));
        System.out.println(model.toString());
        return model;
    }

    public static List<Map<List<String>,Double>> getModel(String file) throws Exception {
        List<Map<List<String>,Double>> list = new ArrayList<>();
        String text = "";
        try {
            text = readString(Paths.get(file), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("!!! Ошибка чтения из файла " + file);
            return null;
        }
        String[] stroki = text.split("\n");
        for(int i = 0; i < stroki.length; i++) {
            String[] mas = stroki[i].split(";");
            Map<List<String>, Double> map = new HashMap<>();
            List<String> detailNames = new ArrayList<>();
            if(mas.length > 1) {
                for(int j = 0; j < mas.length - 1; j++) {
                    detailNames.add(mas[j]);
                }
            } else {
                System.out.println("Ошибка данных в файле " + file + "   " + String.join(";", mas));
            }
            Double hours = 0.0;
            try {
                hours = Double.parseDouble(mas[mas.length - 1]);
            } catch (Exception e) {
                System.out.println("Ошибка данных в файле " + file + "   " + String.join(";", mas));
            }
            map.put(detailNames, hours);
            list.add(map);
        }
        return list;
    }

    public static Order getOrderFromFile(String fileName) throws Exception {
        List<Detail> details = new ArrayList<>();
        List<Work> works = new ArrayList<>();
        Order result = OrderService.makeOrder(fileName);
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
                Detail newDetail = DetailService.makeDetail(mas);
                details.add(newDetail);
//                System.out.println(newDetail.toString());
            } else {
                Work newWork = WorkService.makeWork(mas);
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
        System.out.println("load " + orders.size() + " orders from " + files.length);
        return orders;
    }

}
