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

    /**
     * нормализация управляющих номеров - замена на правильные
     * @param fileName - имя файла=имяз/н
     * @param number - управляющий номер
     * @return - правильный управляющий номер
     */
    public static String normalizeNumber(String fileName, String number) {
        if(fileName.contains("RIO")) {

            if(number.contains("0283")) return number.replace("0283", "0200");
            if(number.contains("0405")) return number.replace("0405", "0400");
            if(number.contains("2583")) return number.replace("2583", "2500");
            if(number.contains("2637")) return number.replace("2637", "2600");
            if(number.contains("2931")) return number.replace("2931", "2900");
            if(number.contains("3935")) return number.replace("3935", "3900");
            if(number.contains("3901")) return number.replace("3901", "3900");
            if(number.contains("4161")) return number.replace("4161", "4101");
            if(number.contains("4107")) return number.replace("4107", "4101");
            if(number.contains("4108")) return number.replace("4108", "4102");
            if(number.contains("4162")) return number.replace("4162", "4102");
            if(number.contains("4251")) return number.replace("4251", "4200");
            if(number.contains("4265")) return number.replace("4265", "4200");

            if(fileName.contains("DX") || fileName.contains("QB") || fileName.contains("DE")) {
                if(number.contains("0419")) return number.replace("0419", "0400");
                if(number.contains("1119")) return number.replace("1119", "1117");
            }

            if(fileName.contains("YB")) {
                if(number.contains("0284")) return number.replace("0284", "0200");
                if(number.contains("0285")) return number.replace("0285", "0200");
                if(number.contains("0287")) return number.replace("0287", "0200");
                if(number.contains("0288")) return number.replace("0288", "0200");
                if(number.contains("0293")) return number.replace("0293", "0200");
                if(number.contains("2635")) return number.replace("2635", "2500");
                if(number.contains("2937")) return number.replace("2937", "2900");
                if(number.contains("2938")) return number.replace("2938", "2900");
                if(number.contains("3493")) return number.replace("3493", "3481");
                if(number.contains("3494")) return number.replace("3494", "3482");
                if(number.contains("4261")) return number.replace("4261", "4200");
            }

            if(fileName.contains("DE")) {
                if(number.contains("0205")) return number.replace("0205", "0201");
                if(number.contains("3479")) return number.replace("3479", "3481");
                if(number.contains("3480")) return number.replace("3480", "3482");
            }
        }

        return number;
    }

}

