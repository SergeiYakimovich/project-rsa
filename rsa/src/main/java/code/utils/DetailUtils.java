package code.utils;

import code.element.Detail;
import code.element.Order;
import code.parse.DetailsParser;
import code.parse.OrderParser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static code.App.FILE_SINGLE;
import static code.App.MODEL_DIR;
import static code.App.ORDERS_DIR;
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


    public static Map<String, Integer> countUniqDetails() throws Exception {
        List<Order> orders = OrderParser.getOrdersFromDirectory(ORDERS_DIR);
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
                Comparator.comparing(det_Uniq::get,Comparator.reverseOrder()));
        sortedMap.putAll(det_Uniq);

        String text = "";
        for(Map.Entry<String, Integer> item : sortedMap.entrySet()) {
            text += item.getKey() + ";" + item.getValue() + "\n";
        }
        writeString(Paths.get(MODEL_DIR + "det-uniq.txt"), text.substring(0, text.length() - 1), StandardCharsets.UTF_8);

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
