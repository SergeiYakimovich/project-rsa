package code.guide.utils;

public class UprNumberNormilizer {

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
