package code.utils;

import code.check.Checker;
import code.check.Result;
import code.element.Detail;
import code.element.Guide;
import code.element.Order;
import code.parse.csvtype.CsvDetail;
import code.parse.DetailsParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static code.App.DET_FREQUENCY;
import static code.App.DET_IMPORTANCE;
import static code.App.DET_MAIN;
import static code.App.DET_NOT_MAIN;
import static code.App.MODEL_DIR;


/**
 * makeUniqDetails() - посчитает уникальные детали c частотой появления в з/н и запишет в файл
 * analizeDetails() - посчитает важность каждой уникальной детали (средняя ошибка справочника без нее) и запишет в файл
 */
public class DetailUtils {

    public static void analizeDetails(List<Order> orders) throws Exception {
        List<String> notMainDetails;
        List<String> allDetailNames = makeUniqDetails(DET_FREQUENCY, orders);

        Map<String, Double> myMap = new HashMap<>();
        for(String nextDetailName : allDetailNames) {
            notMainDetails = List.of(nextDetailName);
            Guide guide = GuideUtils.makeGuide(orders, new ArrayList<>(), notMainDetails);
            List<Result> list = Checker.checkOrders(guide, orders);
            Double diff = Checker.countAvrDiffInPercent(list) * list.size();
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

        List<CsvDetail> detList = new ArrayList<>();
        for(Map.Entry<String, Double> item : sortedMap.entrySet()) {
            CsvDetail newDetail = new CsvDetail(item.getKey(),
                    String.format("%.2f", item.getValue()).replace(",", "."));
            detList.add(newDetail);
        }
        DetailsParser.writeDetNames(DET_IMPORTANCE, detList);
        System.out.println("\nВажность деталей в файле - " + DET_IMPORTANCE);

        List<CsvDetail> notMainDetList = detList.stream()
                .filter(x -> Double.parseDouble(x.getCount()) < 1.0)
                .toList();
        DetailsParser.writeDetNames(DET_NOT_MAIN, notMainDetList);
        System.out.println("\nНенужные детали в файле - " + DET_NOT_MAIN);

        List<CsvDetail> mainDetList = detList.stream()
                .skip(detList.size() - 10)
                .toList();
        DetailsParser.writeDetNames(DET_MAIN, mainDetList);
        System.out.println("\nОсновные детали в файле - " + DET_MAIN);
    }

    public static List<String> makeUniqDetails(String filename, List<Order> orders) throws Exception {
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

        List<CsvDetail> detList = new ArrayList<>();
        for(Map.Entry<String, Integer> item : det_Uniq.entrySet()) {
            CsvDetail newDetail = new CsvDetail(item.getKey() ,String.valueOf(item.getValue()));
            detList.add(newDetail);
        }
        detList.sort((x1,x2) -> (int) (Double.parseDouble(x2.getCount()) - (Double.parseDouble(x1.getCount()))));
        DetailsParser.writeDetNames(filename, detList);
        System.out.println("\nЧастотность деталей в файле - " + filename);

        return detList.stream().map(x -> x.getName()).collect(Collectors.toList());
    }

}
