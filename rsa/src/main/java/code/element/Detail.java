package code.element;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
