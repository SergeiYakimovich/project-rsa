package code.service;

import code.model.Detail;

import java.util.List;

public class DetailService {

    public static boolean isDetailsContains(List<Detail> details, String str) {
        for(Detail detail : details) {
            if(detail.getName().contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDetailsContainsAll(List<Detail> details, List<String> list) {
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
