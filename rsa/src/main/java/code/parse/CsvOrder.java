package code.parse;

import code.element.Detail;
import code.element.Work;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "number", "name", "count", "sum" })
public class CsvOrder extends CsvElement {

    @Override
    public Work makeWorkFromCsvElement() {
        Work work = new Work();
        work.setName(this.getName());
        Double count = this.getCount() == null ? 0 : this.getCount();
        work.setCount(count);
        return work;
    }

    @Override
    public Detail makeDetailFromCsvElement() {
        Detail detail = new Detail();
        detail.setName(this.getName());
        detail.setNumber(this.getNumber());
        Double count = this.getCount() == null ? 0 : this.getCount();
        detail.setCount(count);
        Double sum = this.getSum() == null ? 0 : this.getSum();
        detail.setCount(sum);
        return detail;
    }

}
