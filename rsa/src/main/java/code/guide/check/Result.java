package code.guide.check;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс для хранения результата расчета н/ч по справочнику
 */
@Getter
@Setter
public class Result {

    private Double hours; // результат
    private Double expected; // эталон
    private Double diffInPercent; // разница в %
    private Double diff; // разница с эталоном
    private String orderName;

    @Override
    public String toString() {
        return "Справочник=" + String.format("%.1f",hours) + "н/ч,   эталон=" + String.format("%.1f", expected) +
                "н/ч,   разница=" + String.format("%.1f", diff) + "н/ч или "
                + String.format("%.1f", diffInPercent) + "%   з/н=" + orderName;
    }
}
