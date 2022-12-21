package code.element;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Detail {

    private String number;
    private String name;
    private Double count;
    private Double sum;

    @Override
    public String toString() {
        return "Detail { " + "number=" + this.number + "   name=" + this.name +
                "   count=" + this.count + "   sum=" + this.sum + " " + "}\n";
    }

}
