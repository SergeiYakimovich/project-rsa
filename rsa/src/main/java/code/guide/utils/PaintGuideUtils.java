package code.guide.utils;

import code.guide.element.Guide;
import code.guide.element.Nabor;
import code.guide.element.Order;
import code.guide.element.PaintGuide;
import code.guide.element.PaintNabor;
import code.guide.service.OrderService;
import code.guide.service.WorkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * класс для создания справочника покраски
 */
public class PaintGuideUtils {

    /**
     * создание справочника покраски
     * @param orders - список з/н
     * @param mainDetails - список важных деталей
     * @param notMainDetails - список лишних деталей
     * @param fileName - имя файла
     * @return - справочник покраски
     * @throws Exception
     */
    public static PaintGuide makePaintGuide(List<Order> orders, List<String> mainDetails,
                                       List<String> notMainDetails, String fileName) throws Exception {
        PaintGuide guide = new PaintGuide();
        guide.setName(GuideUtils.getNameFromFileName(fileName));

        List<List<String>> detailsList = new ArrayList<>();
        List<String> detailsStringList = new ArrayList<>();
        for(Order order : orders) {
            List<String> newDetails = order.getDetails().stream()
                    .map(x -> x.getMain())
                    .filter(x -> GuideUtils.isDetailGood(x, notMainDetails))
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
            List<Map<String, Double>> variants = countVariants(orders, details, notMainDetails);
            PaintNabor newNabor = new PaintNabor(details, variants.get(0),variants.get(1),variants.get(2));
            guide.addNaborSets(newNabor);
        }
        return guide;
    }

    /**
     * расчет возможных вариантов при коллизиях
     * @param orders - список з/н
     * @param detailNames - список имен деталей, которые должен содержать з/н
     * @param notMainDetails - список лишних деталей
     * @return - список map (имя з/н : к-во н/ч подготовки,
     *                       имя з/н : к-во н/ч покраски, имя з/н : стоимость краски)
     */
    public static List<Map<String, Double>> countVariants(List<Order> orders, List<String> detailNames, List<String> notMainDetails) {
        List<Order> ordersContainsNames = OrderService.getOrdersContainsAll(orders, detailNames);
        Map<String, Double> prepareHours = new HashMap<>();
        Map<String, Double> colorHours = new HashMap<>();
        Map<String, Double> colorCost = new HashMap<>();

        if(ordersContainsNames.size() == 0) {
            return List.of(prepareHours, colorHours, colorCost);
        }

        for(Order order : ordersContainsNames) {
            Long detSize = order.getDetails().stream()
                    .map(x -> x.getMain())
                    .filter(x -> GuideUtils.isDetailGood(x, notMainDetails))
                    .count();
            if(detSize == detailNames.size()) {
                double x = WorkService.countWorksContains(order.getWorks(), List.of("Подготовительные работы"));
                prepareHours.put(order.getName(), x);
                x = WorkService.countWorksContains(order.getWorks(), List.of("Окрасочные работы"));
                colorHours.put(order.getName(), x);
                x = WorkService.countWorksContains(order.getWorks(), List.of("Стоимость покраски"));
                colorCost.put(order.getName(), x);
            }
        }
        return List.of(prepareHours, colorHours, colorCost);
    }

}
