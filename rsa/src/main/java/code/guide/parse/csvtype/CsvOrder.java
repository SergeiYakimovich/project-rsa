package code.guide.parse.csvtype;

import code.guide.element.Detail;
import code.guide.element.Order;
import code.guide.element.Work;
import code.guide.service.DetailService;
import code.guide.service.OrderService;
import code.guide.utils.UprNumberNormilizer;
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
            return UprNumberNormilizer.normalizeNumberRIO(fileName, number);
        }
        if(fileName.contains("CAMRY")) {
            return UprNumberNormilizer.normalizeNumberCAMRY(fileName, number);
        }

        return number;
    }

}

