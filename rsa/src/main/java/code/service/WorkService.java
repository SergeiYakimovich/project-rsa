package code.service;

import code.element.Detail;
import code.element.Order;
import code.element.Work;

import java.util.Arrays;
import java.util.List;

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

    public static void showWorks(List<Work> works) {
        for(Work work : works) {
            System.out.println(work.toString());
        }
    }

    public static double countWorksContains(List<Work> works, List<String> workNames) {
        double result = 0;
        for(Work work : works) {
            if(workContains(work, workNames)) {
                result += work.getCount();
            }
        }
        return result;
    }
    public static boolean workContains(Work work, List<String> workNames) {
        String name = work.getName();
        for(String workName : workNames) {
            if(name.contains(workName)) {
                return true;
            }
        }
        return false;
    }
}
