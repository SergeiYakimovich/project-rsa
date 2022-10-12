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
@Table(name = "works")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private Double count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Order order;

    @Override
    public String toString() {
        return "Work {\n" +
                "   name=" + name + "\n" +
                "   count=" + count + "\n" +
//                "   order=" + order.getName() + "\n" +
                "}\n";
    }

    public static Work makeWork(String[] mas) {
        Work work = new Work();
        try {
            work.setName(mas[1]);
            if(mas[2] == "") {
                work.setCount(0.0);
            } else {
                work.setCount(Double.parseDouble(mas[2]));
            }
        } catch (Exception e) {
            System.out.println("Ошибка в данных " + Arrays.toString(mas));
        }
        return work;
    }

}
