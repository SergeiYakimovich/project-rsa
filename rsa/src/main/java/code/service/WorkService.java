package code.service;

import code.element.Work;

import java.util.Arrays;

public class WorkService {
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
