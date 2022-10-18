package code.service;

import code.model.Detail;

import java.util.Arrays;
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
