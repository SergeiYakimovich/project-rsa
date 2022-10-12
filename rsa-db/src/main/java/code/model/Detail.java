package code.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.FetchType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;

@Getter
@Setter
@Entity
@Table(name = "details")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String number;
    private String name;
    private Double count;
    private Double sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Order order;

    @Override
    public String toString() {
        return "Detail {\n" +
                "   number=" + this.number + "\n" +
                "   name=" + this.name + "\n" +
                "   count=" + this.count + "\n" +
                "   sum=" + this.sum + "\n" +
//                "   order=" + order.getName() + "\n" +
                "}\n";
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
