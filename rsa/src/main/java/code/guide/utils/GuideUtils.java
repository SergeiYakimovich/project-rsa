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

    public static Guide makeGuide(List<Order> orders, List<String> mainDetails,
                                  List<String> notMainDetails, String fileName) throws Exception{
        Guide guide = new Guide();
        guide.setName(getNameFromFileName(fileName));
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
            double[] minmax = countMinMax(orders, details, notMainDetails);
            Nabor newNabor = new Nabor(details, minmax[0], minmax[1]);
            if(details.size() == 1) {
                String name = details.get(0);
                if(!guide.getDetSingles().containsKey(name)) {
                    guide.addSingles(name, Double.sum(minmax[0], minmax[1])/2);
                }
            } else {
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

    public static double[] countMinMax(List<Order> orders, List<String> detailNames, List<String> notMainDetails) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        if(ordersContainsNames.size() == 0) {
            return new double[]{0.0, 0.0};
        }

        Double worksMin = Double.MAX_VALUE;
        Double worksMax = Double.MIN_VALUE;
        for(Order order : ordersContainsNames) {
            Long detSize = order.getDetails().stream()
                    .map(x -> x.getName())
                    .filter(x -> isDetailGood(x, notMainDetails))
                    .count();
            if(detSize == detailNames.size()) {
                double newHours = order.getWorksCount();
                worksMin = Double.min(worksMin, newHours);
                worksMax = Double.max(worksMax, newHours);
//                if(worksMax - worksMin > 0.1) {
//                    System.out.println((order.getName()));
//                }
            }
        }
        double[] minmax= new double[]{worksMin, worksMax};
        return minmax;
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
