package code.guide.utils;

import code.guide.check.Checker;
import code.guide.check.Result;
import code.guide.element.Detail;
import code.guide.element.Guide;
import code.guide.element.Order;
import code.guide.parse.csvtype.CsvDetail;
import code.guide.parse.DetailsParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * класс для создания списков деталей
 */
public class DetailUtils {

    /**
     * создаст и запишет в файл список лишних з/ч (из всех и важных з/ч)
     * @param orders - список з/н
     * @throws Exception
     */
    public static void makeNotMainFromMainAndAll(List<Order> orders) throws Exception {
        List<String> allDetailNames = makeUniqDetails(MyConsts.DET_FREQUENCY, orders).stream()
                .sorted()
                .collect(Collectors.toList());
        List<String> mainDetails = DetailsParser.getMainDets();
        List<String> notMainDetails = new ArrayList<>();

        for(String detail : allDetailNames) {
            if(!mainDetails.contains(detail)) {
                notMainDetails.add(detail);
            }
        }

        List<CsvDetail> detList = new ArrayList<>();
        for(String detail : notMainDetails) {
            CsvDetail newDetail = new CsvDetail(detail, "1.0");
            detList.add(newDetail);
        }
        DetailsParser.writeDetNames(MyConsts.DET_NOT_MAIN, detList);
        System.out.println("\n\nНенужные детали в файле - " + MyConsts.DET_NOT_MAIN);

    }

    /**
     * посчитает важность каждой уникальной детали (средняя ошибка справочника без нее) и запишет в файл
     * создаст файлы со списками важных и лишних деталей
     * @param orders - список з/н
     * @throws Exception
     */
    public static void analizeDetails(List<Order> orders) throws Exception {
        List<String> notMainDetails;
        List<String> allDetailNames = makeUniqDetails(MyConsts.DET_FREQUENCY, orders);

        Map<String, Double> myMap = new HashMap<>();
        for(String nextDetailName : allDetailNames) {
            notMainDetails = List.of(nextDetailName);
            Guide guide = GuideUtils.makeGuide(orders, new ArrayList<>(), notMainDetails, "");
            List<Result> list = Checker.checkOrders(guide, orders);
            Double diff = Checker.countAvrDiffInPercent(list);
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
        DetailsParser.writeDetNames(MyConsts.DET_IMPORTANCE, detList);
        System.out.println("\nВажность деталей в файле - " + MyConsts.DET_IMPORTANCE);

        List<CsvDetail> notMainDetList = detList.stream()
                .filter(x -> Double.parseDouble(x.getCount()) < 1.0)
                .toList();
        DetailsParser.writeDetNames(MyConsts.DET_NOT_MAIN, notMainDetList);
        System.out.println("\nНенужные детали в файле - " + MyConsts.DET_NOT_MAIN);

        List<CsvDetail> mainDetList = detList.stream()
                .skip(detList.size() - 10)
                .toList();
        DetailsParser.writeDetNames(MyConsts.DET_MAIN, mainDetList);
        System.out.println("\nОсновные детали в файле - " + MyConsts.DET_MAIN);
    }

    /**
     * посчитает уникальные имена деталей c частотой появления в з/н и запишет в файл
     * @param filename - имя файла для записи
     * @param orders - список з/н
     * @return - список уникальных имен з/ч
     * @throws Exception
     */
    public static List<String> makeUniqDetails(String filename, List<Order> orders) throws Exception {
        Map<String, Integer> det_Uniq = new HashMap<>();
        for(Order order : orders) {
            List<Detail> details = order.getDetails();
            for(Detail detail : details) {
                String name = detail.getMain();
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
