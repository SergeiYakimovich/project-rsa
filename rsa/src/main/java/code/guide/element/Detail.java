package code.guide.element;

import code.guide.utils.MyConsts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * класс для описания деталей
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Detail {

    private String number; // упр номер
    private String name; // имя
    private Double count; // количество
    private Double sum; // стоимость

    @Override
    public String toString() {
        return "Detail { " + "number=" + this.number + "   name=" + this.name +
                "   count=" + this.count + "   sum=" + this.sum + " " + "}\n";
    }

    public String getMain() {
        return MyConsts.IS_NAME_MAIN ? name : number;
    }

    public Double getCount() {
        return count;
    }

    public Double getSum() {
        return sum;
    }

    public String getNumber1() {
        return number;
    }

    public String getName1() {
        return name;
    }
}
