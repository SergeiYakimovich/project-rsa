package code.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;

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

    public static Detail makeDetail(String[] mas) {
        Detail detail = new Detail();
        try {
            detail.setNumber(mas[0]);
            detail.setName(mas[1]);
            detail.setCount(Double.parseDouble(mas[2]));
            if(mas[3] == "") {
                detail.setSum(0.0);
            } else {
                detail.setSum(Double.parseDouble(mas[3]));
            }
        } catch (Exception e) {
            System.out.println("Ошибка в данных " + Arrays.toString(mas));
        }
        return detail;
    }

}
