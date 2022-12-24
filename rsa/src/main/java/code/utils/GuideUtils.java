package code.utils;

import code.element.Guide;
import code.element.Nabor;
import code.element.Order;
import code.parse.OrderParser;
import code.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static code.App.MODEL_DIR;
import static java.nio.file.Files.writeString;

/**
 * makeGuide() - создание справочника
 */
public class GuideUtils {

    public static Guide makeGuide(List<Order> orders, List<String> mainDetails, List<String> notMainDetails) throws Exception{
        List<List<String>> detailsList = new ArrayList<>();
        List<String> detailsStringList = new ArrayList<>();

        for(Order order : orders) {
            List<String> newDetails = order.getDetails().stream()
                    .map(x -> x.getName())
                    .filter(x -> isDetailInList(x, mainDetails))
                    .filter(x -> !isDetailInList(x, notMainDetails))
                    .sorted()
                    .collect(Collectors.toList());
            String newStr = newDetails.stream()
                    .collect(Collectors.joining());
            if(newStr.length() != 0 && !detailsStringList.contains(newStr)) {
                detailsStringList.add(newStr);
                detailsList.add(newDetails);
            }
        }
        Guide guide = new Guide();
        detailsList.sort((x1,x2) -> x2.size() - x1.size());
        for(List<String> details : detailsList) {
            Double hours = countMin(orders, details);
            Nabor newNabor = new Nabor(details, hours);
            guide.addNabor(newNabor);
        }
        return guide;
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

    public static boolean isDetailInList(String name, List<String> details) {
        if(details.size() == 0) {
            return true;
        }
        for(String detName : details) {
            if(name.contains(detName)) {
                return true;
            }
        }
        return false;
    }

}
