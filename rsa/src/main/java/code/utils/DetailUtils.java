package code.utils;

import code.calc.Calculator;
import code.calc.Model;
import code.check.Checker;
import code.check.Result;
import code.element.Detail;
import code.element.Order;
import code.element.Work;
import code.parse.CsvOrder;
import code.parse.DetailsParser;
import code.parse.ModelParser;
import code.parse.OrderParser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static code.App.FILE_SINGLE;
import static code.App.FILE_UNIQ_SET;
import static code.App.MODEL_DIR;
import static code.App.ORDERS_ALL_DIR;
import static code.App.ORDERS_MANY_DIR;
import static java.nio.file.Files.writeString;

/**
 * countUniqDetails() - посчитает уникальные детели и частоту появления в з/н
 *
 * makeSetsOfDetails() - создаст наборы деталей
 *
 * checkDetailsLists() - сверяет список единичных деталей из модели Single
 *                       со списком всех деталей
 *                       выводит в файл список деталей, которых нет в модели Single
 */
public class DetailUtils {

    public static void makeSetsOfDetails(Map<String, Integer> det_Uniq, int number) throws Exception {
        List<String> details = det_Uniq.keySet().stream().limit(number).toList();
        List<List<String>> setsOfDetails = new ArrayList<>();

        setsOfDetails.add(details);
        for(int i = 0; i < number; i++) {
            List<String> newSet = new ArrayList<>(details);
            newSet.remove(i);
            setsOfDetails.add(newSet);
        }
        for(int i = 0; i < number - 1; i++) {
            for(int j =  i + 1; j < number; j++) {
                List<String> newSet = new ArrayList<>(details);
                String el1 = newSet.get(i);
                String el2 = newSet.get(j);
                newSet.removeAll(List.of(el1,el2));
                setsOfDetails.add(newSet);
            }
        }

        String text = "";
        for(List<String> item : setsOfDetails) {
            text += item.stream().collect(Collectors.joining(";"));
            text += "\n";
        }
        writeString(Paths.get(MODEL_DIR + "det-sets.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);

    }

    public static List<String> getNotMainDetails() throws Exception {
        List<String> notMainDetails;
        Order order = OrderParser.getOrderFromCsvFile(MODEL_DIR + "not_main_det.csv", new CsvOrder());
        notMainDetails = order.getDetails().stream()
                .map(x -> x.getName())
                .collect(Collectors.toList());
        return notMainDetails;
    }
    public static void makeNotMainDetails() throws Exception {
        List<Order> orders;
        Order order;
        Model model;
        List<String> notMainDetails = new ArrayList<>();

        orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
//        DetailUtils.countUniqDetails(orders);
        order = OrderParser.getOrderFromCsvFile(MODEL_DIR + "details-all.csv", new CsvOrder());
        List<String> allDetailNames = order.getDetails().stream()
                .map(x -> x.getName())
                .collect(Collectors.toList());

        Map<String, Double> myMap = new HashMap<>();
        for(String nextDetailName : allDetailNames) {
            notMainDetails = List.of(nextDetailName);
            ModelUtils.makeModelUniqSets(ORDERS_MANY_DIR, notMainDetails);
            model = ModelParser.makeModel(MODEL_DIR + FILE_SINGLE, MODEL_DIR + FILE_UNIQ_SET);
            List<Result> list = Checker.checkOrders(model, orders, Calculator.CheckType.ALL);
            Double diff = Checker.countAvrDiffInPercent(list) * 1000;
            myMap.put(nextDetailName, diff);
        }

        Map<String, Double> sortedMap = myMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        String text = "номер;имя;к-во;0\n";
        for(Map.Entry<String, Double> item : sortedMap.entrySet()) {
            text += "1;" + item.getKey() + ";" + item.getValue().intValue() + ";0\n";
        }
//        String.format("%.2f", item.getValue())
        writeString(Paths.get(MODEL_DIR +"not_main_det_all.csv"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);

    }

    public static Map<String, Integer> countUniqDetails(List<Order> orders) throws Exception {
        Map<String, Integer> det_Uniq = new HashMap<>();

        for(Order order : orders) {
            List<Detail> details = order.getDetails();
            for(Detail detail : details) {
                String name = detail.getName();
                if(det_Uniq.containsKey(name)) {
                    det_Uniq.put(name, det_Uniq.get(name) + 1);
                } else {
                    det_Uniq.put(name, 1);
                }
            }
        }

        Map<String, Integer> sortedMap = new TreeMap<String, Integer>(
//                Comparator.comparing(det_Uniq::get,Comparator.reverseOrder())
                );
        sortedMap.putAll(det_Uniq);

        String text = "номер;имя;к-во;0\n";
        for(Map.Entry<String, Integer> item : sortedMap.entrySet()) {
            text += "1;" + item.getKey() + ";" + item.getValue() + ";0\n";
        }
        writeString(Paths.get(MODEL_DIR + "details-all.csv"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);

        return sortedMap;
    }

    public static void checkDetailsLists() throws Exception {
        List<List<String>> details_all = DetailsParser.getDetailsFromFile(MODEL_DIR + "details_all.txt");
        List<List<String>> details_80 = DetailsParser.getDetailsFromFile(MODEL_DIR + FILE_SINGLE);
        List<String> det_all = new ArrayList<>();
        for(List<String> item : details_all) {
            det_all.add(item.get(2));
        }
        List<String> det_80 = new ArrayList<>();
        for(List<String> item : details_80) {
            det_80.add(item.get(0));
        }
        det_all.removeAll(det_80);
        det_all=det_all.stream().sorted().collect(Collectors.toList());
        String text = "";
        for(String str : det_all) {
            text += str + "\n";
        }
        writeString(Paths.get(MODEL_DIR + "det-new.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);
    }

}
