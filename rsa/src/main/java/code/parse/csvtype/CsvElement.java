package code.parse.csvtype;

import code.element.Detail;
import code.element.Order;
import code.element.Work;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class CsvElement {
    public abstract Order makeOrderFromCsvElement(List<CsvElement> elements, String fileName);

}
