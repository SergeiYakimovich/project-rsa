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

import static code.App.MODEL_DIR;


/**
 * makeUniqDetails() - посчитает уникальные детали c частотой появления в з/н и запишет в файл
 * makeNotMainDetails() - посчитает важность каждой уникальной детали (справочник без нее) и запишет в файл
 */
public class DetailUtils {

    public static void makeNotMainDetails(String filename, List<Order> orders) throws Exception {
        List<String> notMainDetails;

        List<String> allDetailNames = DetailsParser.readDetNames(MODEL_DIR + "details-all.csv");

        Map<String, Double> myMap = new HashMap<>();
        for(String nextDetailName : allDetailNames) {
            notMainDetails = List.of(nextDetailName);
            Guide guide = GuideUtils.makeGuide(orders, new ArrayList<>(), notMainDetails);
            List<Result> list = Checker.checkOrders(guide, orders);
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

        List<CsvDetail> detList = new ArrayList<>();
        for(Map.Entry<String, Double> item : sortedMap.entrySet()) {
            CsvDetail newDetail = new CsvDetail(item.getKey() , String.format("%.2f", item.getValue()));
            detList.add(newDetail);
        }
        DetailsParser.writeDetNames(filename, detList);

    }

    public static void makeUniqDetails(String filename, List<Order> orders) throws Exception {
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
    }

}
