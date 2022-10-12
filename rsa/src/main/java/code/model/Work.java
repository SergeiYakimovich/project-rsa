package code.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;

@Getter
@Setter
public class Work {

    private String name;
    private Double count;

    @Override
    public String toString() {
        return "Work { " + "name=" + name + "   count=" + count + " }\n";
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
