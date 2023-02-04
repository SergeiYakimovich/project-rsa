package code.guide.utils;

import code.guide.element.Order;
import code.guide.parse.OrderParser;
import code.guide.parse.csvtype.CsvOrder;
import code.guide.service.GuideService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.nio.file.Files.writeString;

public class OrderUtils {
    public enum Range {
        RANGE1(0,1),
        RANGE2(1, 2),
        RANGE3(2, 3),
        RANGE4(3, 4),
        RANGE5(4, 5),
        RANGE6(5, 6),
        RANGE7(6, 7),
        RANGE8(7, 8),
        RANGE9(8, 9),
        RANGE10(9, 10),
        RANGE20(10, 20),
        RANGE30(20, 30),
        RANGE40(30, 40),
        RANGE50(40, 50),
        RANGE60(50, 60),
        RANGE70(60, 70),
        RANGE80(70, 80),
        RANGE90(80, 90),
        RANGE1000(90, 1000);

        public double min;
        public double max;

        Range(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return "Диапазон {" +
                    " min=" + min +
                    ", max=" + max +
                    " }\n";
        }

        public static Range getRange(double x) {
            for(Range range : Range.values()) {
                if(x >= range.min && x < range.max) {
                    return range;
                }
            }
            return null;
        }
    }

    public static void rangeOrders() throws Exception {
        Map<Range, Set<String>> rangeMap = new TreeMap<>();
        NameUprNumberUtils.makeMapNameNumber();
        List<Order> orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
        List<String> notMainDetails = Cli.getNotMainDet();
        for(Order order : orders) {
            double hours = order.getWorksCount();
            List<String> detNames = order.getDetails().stream()
                    .map(x -> x.getMain())
                    .filter(x -> GuideUtils.isDetailGood(x, notMainDetails))
                    .collect(Collectors.toList());
            Range range = Range.getRange(hours);
            if(rangeMap.containsKey(range)) {
                rangeMap.get(range).addAll(detNames);
            } else {
                Set<String> newSet = new HashSet<>(detNames);
                rangeMap.put(range, newSet);
            }
        }

        StringBuilder builder = new StringBuilder();
        for(Map.Entry<Range, Set<String>> item : rangeMap.entrySet()) {
            builder.append(item.getKey().toString() + "\n");
            String str = item.getValue().stream()
                    .sorted((x1,x2) -> MyConsts.IS_NAME_MAIN ? x1.compareTo(x2) :
                            NameUprNumberUtils.mapNameNumber.get(x1).compareTo(NameUprNumberUtils.mapNameNumber.get(x2)))
                    .map(x -> GuideService.modifyName(x))
                    .collect(Collectors.joining("\n"));
            builder.append(str + "\n\n");
        }

        writeString(Paths.get(MyConsts.DET_RANGE), builder.toString(), StandardCharsets.UTF_8);
        System.out.println("\nРанжирование заказ-нарядов - " + MyConsts.DET_RANGE);
    }
}
