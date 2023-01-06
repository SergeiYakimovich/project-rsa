package code.guide.parse.csvtype;

import code.guide.element.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class CsvElement {
    public abstract Order makeOrderFromCsvElement(List<CsvElement> elements, String fileName);

}
