package code.guide.utils;

import code.guide.element.Guide;
import code.guide.element.Nabor;
import code.guide.element.Order;
import code.guide.service.OrderService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static code.guide.utils.MyConsts.GUIDE_FILE;

/**
 * makeGuide() - создание справочника
 */
public class GuideUtils {

    public static Guide makeGuide(List<Order> orders, List<String> mainDetails,
                                  List<String> notMainDetails, String fileName) throws Exception{
        Guide guide = new Guide();
        guide.setName(getNameFromFileName(fileName));
//        guide.setMainDetails(mainDetails);
//        guide.setNotMainDetails(notMainDetails);
        orders.stream()
                .filter(x -> x.getDetails().size() == 1
                        || (x.getDetails().size() == 2 && (x.getDetails().get(1).getMain().contains("МЕЛКИЕ ДЕТАЛИ")))
                )
                .forEach(x -> {
                    Double hours = x.getWorksCount();
                    String name = x.getDetails().get(0).getMain();
                    guide.addSingles(name, Map.of(x.getName(), hours));
                });

        List<List<String>> detailsList = new ArrayList<>();
        List<String> detailsStringList = new ArrayList<>();
        for(Order order : orders) {
            List<String> newDetails = order.getDetails().stream()
                    .map(x -> x.getMain())
                    .filter(x -> isDetailGood(x, notMainDetails))
                    .sorted()
                    .collect(Collectors.toList());
            String newStr = newDetails.stream()
                    .collect(Collectors.joining());
            if(newStr.length() != 0 && !detailsStringList.contains(newStr)) {
                detailsStringList.add(newStr);
                detailsList.add(newDetails);
            }
        }
        detailsList.sort((x1,x2) -> x2.size() - x1.size());
        for(List<String> details : detailsList) {
            Map<String, Double> variants = countVariants(orders, details, notMainDetails);
            if(details.size() == 1) {
                String name = details.get(0);
                guide.addSingles(name, variants);
            } else {
                Nabor newNabor = new Nabor(details, variants);
                guide.addNaborSets(newNabor);
            }
        }
        return guide;
    }

    private static String getNameFromFileName(String fileName) {
        if(fileName.isEmpty()) {
            return "";
        }
        String[] arr = fileName.split("/");
        arr = arr[arr.length - 1].split("[.]");
        return arr[0];
    }

    public static Map<String, Double> countVariants(List<Order> orders, List<String> detailNames, List<String> notMainDetails) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        Map<String, Double> variants = new HashMap<>();
        if(ordersContainsNames.size() == 0) {
            return variants;
        }

        for(Order order : ordersContainsNames) {
            Long detSize = order.getDetails().stream()
                    .map(x -> x.getMain())
                    .filter(x -> isDetailGood(x, notMainDetails))
                    .count();
            if(detSize == detailNames.size()) {
                double newHours = order.getWorksCount();
                variants.put(order.getName(), newHours);
            }
        }
        return variants;
    }

    public static boolean isDetailGood(String name, List<String> details) {
        if(details.size() == 0) {
            return true;
        }
        for(String detName : details) {
            if(name.equals(detName)) {
                return false;
            }
        }
        return true;
    }

}
