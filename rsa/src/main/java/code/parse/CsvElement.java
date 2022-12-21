package code.parse;

import code.element.Detail;
import code.element.Work;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class CsvElement {
    private String number;
    private String name;
    private Double count;
    private Double sum;
    abstract Work makeWorkFromCsvElement();
    abstract Detail makeDetailFromCsvElement();

    public String toString() {
        return "Element { " + "number=" + number + ", name="
                + name + ", count=" + count + ", sum=" + sum + " }\n";
    }
}
