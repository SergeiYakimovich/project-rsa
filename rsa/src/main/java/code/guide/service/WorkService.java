package code.guide.service;

import code.guide.element.Work;

import java.util.List;

public class WorkService {
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
