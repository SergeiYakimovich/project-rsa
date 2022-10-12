package code.check;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

    private Double hours;
    private Double expected;
    private Double diffInPercent;
    private Double diff;
    private String orderName;

    @Override
    public String toString() {
        return "Result {" + " Модель=" + String.format("%.1f",hours) + "   Эталон=" + String.format("%.1f", expected) +
                "   разница=" + String.format("%.1f", diff) + "   в%=" + String.format("%.1f", diffInPercent) +
                "   з/н=" + orderName + " }";
    }
}
