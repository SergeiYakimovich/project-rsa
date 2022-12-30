package code.guide.check;

import lombok.Getter;
import lombok.Setter;

/**
 * Результат проверки справочника для з/н
 */
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
        return "Справочник=" + String.format("%.1f",hours) + "н/ч,   эталон=" + String.format("%.1f", expected) +
                "н/ч,   разница=" + String.format("%.1f", diff) + "н/ч или "
                + String.format("%.1f", diffInPercent) + "%   з/н=" + orderName;
    }
}
