package code.guide.service;

import code.guide.element.Detail;

import java.util.Collection;
import java.util.List;

public class DetailService {

    public static boolean isDetailsContains(List<Detail> details, String str) {
        for(Detail detail : details) {
            if(str.equals(detail.getMain())) {
                return true;
            }
        }
        return false;
    }

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
