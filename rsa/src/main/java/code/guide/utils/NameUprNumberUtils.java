package code.guide.utils;

import code.guide.element.Detail;
import code.guide.element.Order;
import code.guide.parse.DetailsParser;
import code.guide.parse.csvtype.CsvDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NameUprNumberUtils {
    public static Map<String, String> mapNameNumber = new HashMap<>();

    public static void makeMapNameNumber() throws IOException {
        List<CsvDetail> elements = DetailsParser.readDetNameNumber(MyConsts.DET_MAIN);
        List<CsvDetail> elementE = DetailsParser.readDetNameNumber(MyConsts.DET_MAIN_E);
        for(CsvDetail detail : elementE) {
            detail.name = detail.name + " ЗАМЕНА";
            detail.count = detail.count + " ЗАМЕНА";
        }
        elements.addAll(elementE);
        for(CsvDetail detail : elements) {
            String val;
            String key;
            if(MyConsts.IS_NAME_MAIN) {
                key = detail.getName();
                val = detail.getCount();
            } else {
                key = detail.getCount();
                val = detail.getName();
            }
            mapNameNumber.put(key,val);
        }
    }

    public static Map<String, List<String>> getNameNumber(List<Order> orders, boolean nameFirst) {
        Map<String, List<String>> nameNumber = new HashMap<>();
        for(Order order : orders) {
            for(Detail detail : order.getDetails()) {
                String val;
                String key;
                if(!nameFirst) {
                    val = detail.getName1();
                    key = detail.getNumber1();
                } else {
                    key = detail.getName1();
                    val = detail.getNumber1();
                }

                if(nameNumber.containsKey(key)) {
                    if(!nameNumber.get(key).contains(val)) nameNumber.get(key).add(val);
                } else {
                    List<String> newList = new ArrayList<>();
                    newList.add(val);
                    nameNumber.put(key, newList);
                }
            }
        }
        return nameNumber;
    }

    public static void checkNameNumber(List<Order> orders, boolean nameFirst) {
        Map<String, List<String>> nameNumber = getNameNumber(orders, nameFirst);
        if(!nameFirst) {
            System.out.println("\nНеоднозначные номер-имя :");
        } else {
            System.out.println("\nНеоднозначные имя-номер :");
        }
        for(Map.Entry<String, List<String>> item : nameNumber.entrySet()) {
            if(item.getValue().size() > 1) {
                System.out.println(item.getKey() + "\t" + item.getValue());
            }
        }
    }
}
