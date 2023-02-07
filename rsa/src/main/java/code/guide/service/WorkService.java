package code.guide.service;

import code.guide.element.Work;

import java.util.List;

/**
 * класс для работы с работами
 */
public class WorkService {
    public static void showWorks(List<Work> works) {
        for(Work work : works) {
            System.out.println(work.toString());
        }
    }

    /**
     * посчитать н/ч для работ из списка, которые содержат заданные имена робот
     * @param works - список работ
     * @param workNames - заданные имена работ
     * @return - к-во н/ч
     */
    public static double countWorksContains(List<Work> works, List<String> workNames) {
        double result = 0;
        for(Work work : works) {
            if(isWorkContains(work, workNames)) {
                result += work.getCount();
            }
        }
        return result;
    }

    /**
     * проверка содежит ли работа имя из списка
     * @param work - работа
     * @param workNames - список имен работ
     * @return - true, если содержит
     */
    public static boolean isWorkContains(Work work, List<String> workNames) {
        String name = work.getName();
        for(String workName : workNames) {
            if(name.contains(workName)) {
                return true;
            }
        }
        return false;
    }
}
