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
        return "Result {" + " Model=" + String.format("%.1f",hours) + "   standard=" + String.format("%.1f", expected) +
                "   difference=" + String.format("%.1f", diff) + "   in%=" + String.format("%.1f", diffInPercent) +
                "   order=" + orderName + " }";
    }
}
