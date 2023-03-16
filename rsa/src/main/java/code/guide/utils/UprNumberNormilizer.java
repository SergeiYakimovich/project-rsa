package code.guide.utils;

public class UprNumberNormilizer {

    public static String normalizeNumberRioPolo(String fileName, String number) {
        if(number.contains("0930")) return number.replace("0930", "1009");

        if(number.contains("1113")) return number.replace("1113", "1117");
        if(number.contains("1119")) return number.replace("1119", "1117");
        if(number.contains("1135")) return number.replace("1135", "1117");
        if(number.contains("1163")) return number.replace("1163", "1117");
        if(number.contains("1165")) return number.replace("1165", "1117");
        if(number.contains("1167")) return number.replace("1167", "1117");
        if(number.contains("1191")) return number.replace("1191", "1117");

        if(number.contains("1114")) return number.replace("1114", "1118");
        if(number.contains("1136")) return number.replace("1136", "1118");
        if(number.contains("1164")) return number.replace("1164", "1118");
        if(number.contains("1168")) return number.replace("1168", "1118");
        if(number.contains("1194")) return number.replace("1194", "1118");
        if(number.contains("1192")) return number.replace("1192", "1118");

        if(number.contains("1205")) return number.replace("1205", "1201");
        if(number.contains("1207")) return number.replace("1207", "1201");
        if(number.contains("1241")) return number.replace("1241", "1201");

        if(number.contains("1208")) return number.replace("1208", "1202");
        if(number.contains("1242")) return number.replace("1242", "1202");

        if(number.contains("1325")) return number.replace("1325", "1321");
        if(number.contains("1327")) return number.replace("1327", "1321");

        if(number.contains("2139")) return number.replace("2139", "2101");

        if(number.contains("2140")) return number.replace("2140", "2102");

        if(number.contains("2583")) return number.replace("2583", "2500");

        if(number.contains("2605")) return number.replace("2605", "2600");
        if(number.contains("2637")) return number.replace("2637", "2600");

        if(number.contains("2713")) return number.replace("2713", "2711");

        if(number.contains("3478")) return number.replace("3478", "3481");
        if(number.contains("2089")) return number.replace("2089", "3481");

        if(number.contains("3480")) return number.replace("3480", "3482");
        if(number.contains("2090")) return number.replace("2090", "3482");

        if(number.contains("3901")) return number.replace("3901", "3900");
        if(number.contains("3902")) return number.replace("3902", "3900");
        if(number.contains("3935")) return number.replace("3935", "3900");
        if(number.contains("3921")) return number.replace("3921", "3900");

        if(number.contains("4001")) return number.replace("4001", "3991");

        if(number.contains("4002")) return number.replace("4002", "3992");

        if(number.contains("4107")) return number.replace("4107", "4101");
        if(number.contains("4165")) return number.replace("4165", "4101");
        if(number.contains("4161")) return number.replace("4161", "4101");

        if(number.contains("4108")) return number.replace("4108", "4102");
        if(number.contains("4166")) return number.replace("4166", "4102");
        if(number.contains("4162")) return number.replace("4162", "4102");

        if(number.contains("4257")) return number.replace("4257", "4200");
        if(number.contains("4258")) return number.replace("4258", "4200");
        if(number.contains("4251")) return number.replace("4251", "4200");
        if(number.contains("4265")) return number.replace("4265", "4200");

        if(number.contains("7441")) return number.replace("7441", "7440");


        return number;
    }

    public static String normalizeNumberCAMRY(String fileName, String number) {
        if(number.contains("0283")) return number.replace("0283", "0200");
        if(number.contains("0285")) return number.replace("0285", "0200");
        if(number.contains("0333")) return number.replace("0333", "0200");

        if(number.contains("1017")) return number.replace("1017", "1002");

        if(number.contains("1019")) return number.replace("1019", "1023");

        if(number.contains("1020")) return number.replace("1020", "1024");

//        if(number.contains("1001")) return number.replace("1001", "1009");
//        if(number.contains("1002")) return number.replace("1002", "1009");

        if(number.contains("1123")) return number.replace("1123", "1117");

        if(number.contains("1124")) return number.replace("1124", "1118");

        if(number.contains("1209")) return number.replace("1209", "1201");
        if(number.contains("1259")) return number.replace("1259", "1201");

        if(number.contains("1210")) return number.replace("1210", "1202");
        if(number.contains("1260")) return number.replace("1260", "1202");

        if(number.contains("2581")) return number.replace("2581", "2583");
        if(number.contains("2595")) return number.replace("2595", "2583");

        if(number.contains("4001")) return number.replace("4001", "3991");

        if(number.contains("4002")) return number.replace("4002", "3992");

        return number;
    }

    public static String normalizeNumberRIO(String fileName, String number) {
            if(number.contains("0283")) return number.replace("0283", "0200");
            if(number.contains("0405")) return number.replace("0405", "0400");
            if(number.contains("2583")) return number.replace("2583", "2500");
            if(number.contains("2637")) return number.replace("2637", "2600");
            if(number.contains("2931")) return number.replace("2931", "2900");
            if(number.contains("3935")) return number.replace("3935", "3900");
            if(number.contains("3901")) return number.replace("3901", "3900");
            if(number.contains("4161")) return number.replace("4161", "4101");
            if(number.contains("4107")) return number.replace("4107", "4101");
            if(number.contains("4108")) return number.replace("4108", "4102");
            if(number.contains("4162")) return number.replace("4162", "4102");
            if(number.contains("4251")) return number.replace("4251", "4200");
            if(number.contains("4265")) return number.replace("4265", "4200");

            if(fileName.contains("DX") || fileName.contains("QB") || fileName.contains("DE")) {
                if(number.contains("0419")) return number.replace("0419", "0400");
                if(number.contains("1119")) return number.replace("1119", "1117");
            }

            if(fileName.contains("YB")) {
                if(number.contains("0284")) return number.replace("0284", "0200");
                if(number.contains("0285")) return number.replace("0285", "0200");
                if(number.contains("0287")) return number.replace("0287", "0200");
                if(number.contains("0288")) return number.replace("0288", "0200");
                if(number.contains("0293")) return number.replace("0293", "0200");
                if(number.contains("2635")) return number.replace("2635", "2500");
                if(number.contains("2937")) return number.replace("2937", "2900");
                if(number.contains("2938")) return number.replace("2938", "2900");
                if(number.contains("3493")) return number.replace("3493", "3481");
                if(number.contains("3494")) return number.replace("3494", "3482");
                if(number.contains("4261")) return number.replace("4261", "4200");
            }

            if(fileName.contains("DE")) {
                if(number.contains("0205")) return number.replace("0205", "0201");
                if(number.contains("3479")) return number.replace("3479", "3481");
                if(number.contains("3480")) return number.replace("3480", "3482");
            }
        return number;
    }

}
