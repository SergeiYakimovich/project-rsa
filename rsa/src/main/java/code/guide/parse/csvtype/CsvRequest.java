package code.guide.parse.csvtype;

import code.guide.element.Order;
import code.guide.service.OrderService;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * класс для описания делатей - 1 позиция
 */
@Getter
@Setter
@JsonPropertyOrder({"name"})
public class CsvRequest extends CsvElement {
    private String name;

    /**
     * создание з/н из списка элементов
     * @param elements - список элементов
     * @param fileName - имя файла
     * @return - з/н
     */
    @Override
    public Order makeOrderFromCsvElement(List<CsvElement> elements, String fileName) {
        List<String> list = elements.stream()
                .map(x -> (CsvRequest) x)
                .map(x -> x.name)
                .collect(Collectors.toList());
        return OrderService.makeSimpleOrder(list, fileName);
    }
}
