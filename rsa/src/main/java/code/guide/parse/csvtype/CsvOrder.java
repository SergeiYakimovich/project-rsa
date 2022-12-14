package code.guide.parse.csvtype;

import code.guide.element.Detail;
import code.guide.element.Order;
import code.guide.element.Work;
import code.guide.service.OrderService;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({ "number", "name", "count", "sum" })
public class CsvOrder extends CsvElement {
    private String number;
    private String name;
    private Double count;
    private Double sum;


    @Override
    public Order makeOrderFromCsvElement(List<CsvElement> elements, String fileName) {
        Order result = OrderService.makeOrder(fileName);
        List<Detail> details = new ArrayList<>();
        List<Work> works = new ArrayList<>();
        for (CsvElement element : elements) {
            CsvOrder item = (CsvOrder) element;
            if(!item.number.isEmpty()) {
                Detail detail = new Detail();
                detail.setName(item.name.trim());
                detail.setNumber(item.number.trim());
                detail.setCount(item.count == null ? 0 : item.count);
                detail.setSum(item.sum == null ? 0 : item.sum);
                details.add(detail);
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

}
