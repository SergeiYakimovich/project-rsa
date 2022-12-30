package code.guide.utils;

import code.guide.element.Guide;
import code.guide.element.Nabor;
import code.guide.element.Order;
import code.guide.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static code.guide.utils.MyConsts.GUIDE_FILE;

/**
 * makeGuide() - создание справочника
 */
public class GuideUtils {

    public static Guide makeGuide(List<Order> orders, List<String> mainDetails, List<String> notMainDetails) throws Exception{
        Guide guide = new Guide();
        guide.setName(getNameFromFileName(GUIDE_FILE));
//        guide.setMainDetails(mainDetails);
//        guide.setNotMainDetails(notMainDetails);
        orders.stream()
                .filter(x -> x.getDetails().size() == 1
                        || (x.getDetails().size() == 2 && (x.getDetails().get(1).getName().contains("МЕЛКИЕ ДЕТАЛИ")))
                )
                .forEach(x -> {
                    Double hours = x.getWorksCount();
                    String name = x.getDetails().get(0).getName();
                    if(guide.getDetSingles().containsKey(name)) {
                        hours = Double.min(hours, guide.getDetSingles().get(name));
                    }
                    guide.addSingles(name, hours);

                });

        List<List<String>> detailsList = new ArrayList<>();
        List<String> detailsStringList = new ArrayList<>();

        for(Order order : orders) {
            List<String> newDetails = order.getDetails().stream()
                    .map(x -> x.getName())
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
            Double hours = countMin(orders, details);
            Nabor newNabor = new Nabor(details, hours);
            if(details.size() == 1) {
                String name = details.get(0);
                if(!guide.getDetSingles().containsKey(name)) {
                    guide.addSingles(name, hours);
                }
            } else {
                guide.addNaborSets(newNabor);
            }
        }
        return guide;
    }

    private static String getNameFromFileName(String fileName) {
        String[] arr = fileName.split("/");
        arr = arr[arr.length - 1].split("[.]");
        return arr[0];
    }

    public static Double countMin(List<Order> orders, List<String> detailNames) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        if(ordersContainsNames.size() == 0) {
            return 0.0;
        }
        Double worksCount = Double.MAX_VALUE;
        for(Order order : ordersContainsNames) {
            worksCount = Double.min(worksCount, order.getWorksCount());
        }
        return worksCount;
    }

    public static boolean isDetailGood(String name, List<String> details) {
        if(details.size() == 0) {
            return true;
        }
        for(String detName : details) {
            if(name.contains(detName)) {
                return false;
            }
        }
        return true;
    }

}
