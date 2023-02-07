package code.guide.service;

import code.guide.element.Detail;

import java.util.Collection;
import java.util.List;

/**
 * класс для работы с деталями
 */
public class DetailService {

    /**
     * проверка содержит ли список деталей имя детали
     * @param details - список деталей
     * @param str - имя детали
     * @return - true, если содержит
     */
    public static boolean isDetailsContains(List<Detail> details, String str) {
        for(Detail detail : details) {
            if(str.equals(detail.getMain())) {
                return true;
            }
        }
        return false;
    }

    /**
     * проверка содержит ли список деталей все имена деталей
     * @param details - список деталей
     * @param list - список имен деталей
     * @return - true, если содержит
     */
    public static boolean isDetailsContainsAll(List<Detail> details, Collection<String> list) {
        for(String str : list) {
            if(!isDetailsContains(details, str)) {
                return false;
            }
        }
        return true;
    }

    public static void showDetails(List<Detail> details) {
        for(Detail detail : details) {
            System.out.println(detail.toString());
        }
    }

}
