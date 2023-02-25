package code.guide.parse.csvtype;

import code.guide.element.Detail;
import code.guide.element.Order;
import code.guide.element.Work;
import code.guide.service.DetailService;
import code.guide.service.OrderService;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * класс для описания з/н - 4 позиции
 */
@Getter
@Setter
@JsonPropertyOrder({ "number", "name", "count", "sum" })
public class CsvOrder extends CsvElement {
    private String number;
    private String name;
    private Double count;
    private Double sum;

    /**
     * создание з/н из списка элементов
     * @param elements - список элементов
     * @param fileName - имя файла
     * @return - з/н
     */
    @Override
    public Order makeOrderFromCsvElement(List<CsvElement> elements, String fileName) {
        Order result = OrderService.makeOrder(fileName);
        List<Detail> details = new ArrayList<>();
        List<Work> works = new ArrayList<>();
        for (CsvElement element : elements) {
            CsvOrder item = (CsvOrder) element;
            if(!item.number.isEmpty()) {
                String normNumber = normalizeNumber(result.getName(),item.number.trim());
                if(!DetailService.isDetailsContains(details, normNumber)) {
                    Detail detail = new Detail();
                    detail.setName(item.name.trim());
                    detail.setNumber(normNumber);
                    detail.setCount(item.count == null ? 0 : item.count);
                    detail.setSum(item.sum == null ? 0 : item.sum);
                    details.add(detail);
                }

            } else if(!item.name.equals("Сумма") && !item.name.isEmpty()) {
                Work work = new Work();
                work.setName(item.name.trim());
                work.setCount(item.count == null ? 0 : item.count);
                works.add(work);
            }
        }
        result.setDetails(details);
        result.setWorks(works);
        return result;
    }

    public static String normalizeNumber(String fileName, String number) {
        if(fileName.contains("RIO") && fileName.contains("DE")) {
            if(number.contains("0419")) return number.replace("0419", "0405");
            if(number.contains("1119")) return number.replace("1119", "1117");
            if(number.contains("1205")) return number.replace("1205", "1201");
            if(number.contains("2600")) return number.replace("2600", "2637");
            if(number.contains("3479")) return number.replace("3479", "3481");
            if(number.contains("3480")) return number.replace("3480", "3482");
            if(number.contains("3935")) return number.replace("3935", "3901");
            if(number.contains("4251")) return number.replace("4251", "4265");
            return number;
        }
        if(fileName.contains("RIO") && (fileName.contains("DX") || fileName.contains("QB"))) {
            if(number.contains("4107")) return number.replace("4107", "4101");
            if(number.contains("4161")) return number.replace("4161", "4101");
            if(number.contains("4108")) return number.replace("4108", "4102");
            if(number.contains("4162")) return number.replace("4162", "4102");
            if(number.contains("4251")) return number.replace("4251", "4265");
            return number;
        }
        return number;
    }

}

