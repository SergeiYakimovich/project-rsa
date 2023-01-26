package code.guide.parse;

import code.guide.element.Order;
import code.guide.parse.csvtype.CsvElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static code.guide.utils.MyConsts.BASE_URL;
import static code.guide.utils.MyConsts.ORDERS_DIR;
import static java.nio.file.Files.writeString;

/**
 * convertOrderFromXmlToCsv() - получить Csv з/н из XML файла
 * convertOrdersFromDirectory() - получить список Csv з/н из директории c XML файлами
 */
public class XmlParser {
    final  static Map<String, String> map = getMapNameNumber();

    public static boolean convertOrderFromXmlToCsv (String fileName) throws Exception {
//        System.out.println(fileName);
        StringBuffer buffer = new StringBuffer();
        buffer.append("УПР №;НАЗВАНИЕ;1.0;1.0\n");

        String str1 = findDetailsInXml(fileName);
        if(str1 == null || str1.length() == 0) {
            System.out.println("З/ч не найдены " + fileName);
            return false;
        } else {
            buffer.append(str1);
        }

        Double hours = findWorksInXml(fileName);
        if(hours == null || hours < 0.001) {
            System.out.println("Работы не найдены " + fileName);
            return false;
        } else {
            String strHours = String.format("%.2f", hours);
            strHours = strHours.replaceAll(",", ".");
            buffer.append(";Работы;" + strHours + ";");
        }

        String[] mas = fileName.split(Pattern.quote("\\"));
        String str = mas[mas.length - 1];
        String fileOut = str.substring(0, str.length() - 3) + "csv";
        writeString(Paths.get(ORDERS_DIR + fileOut), buffer.toString(), StandardCharsets.UTF_8);
        return true;
    }

    public static void convertOrdersFromDirectory(String dir) throws Exception {
        File[] files = new File(dir).listFiles();
        int good = 0, bad = 0;
        for(File file : files) {
            String fileName = file.toString();
            if(file.isDirectory()) {
               convertOrdersFromDirectory(fileName);
            } else {
                if(convertOrderFromXmlToCsv(fileName)) {
                    good++;
                } else {
                    bad++;
                }
            }
        }
        System.out.println("Сковертировано=" + good + " из " + files.length + " Ошибок=" + bad);
    }

    public static String findDetailsInXml(String fileName) throws IOException {
        String result = "";
        String str;
        String repType1 = "<RepTyp>I";
        String repType2 = "<RepTyp>L";
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        do {
            do {
                str = reader.readLine();
            } while((str != null)
                    && !(str.contains("УПР № ") && str.contains(" НАЗВАНИЕ             № ДЕТАЛИ                         СТОИМ"))
                    && !str.contains("</EditedOutput>"));
            if(str == null) {
                reader.close();
                return result;
            }
            result = "";
            if(!str.contains("</EditedOutput>")) {
                while(str != null && !str.contains("----------") && !str.contains("СИСТЕМА AUDATEX")) {
                    str = str.trim();
                    List<String> list = Arrays.stream(str.split(" "))
                            .map(x -> x.trim())
                            .filter(x -> !x.isEmpty())
                            .collect(Collectors.toList());
                    if(list.size() != 0) {
                        String uprNumber = str.substring(0,8).trim();
                        int n = str.length() < 37 ? str.length() : 37;
                        String name = str.substring(16,n).trim().toUpperCase();
                        if(name.length() == 1) {
                            System.out.println("!? : имя з/ч = " + name);
                        }
                        if(name.equals("EL")) {
                            name = changeNameEl(uprNumber,map);
                        }
                        if(!uprNumber.equals("УПР №")) {
                            result += uprNumber + ";" + name + ";" + "1.0" + ";" + "1.0" + "\n";
                        }
                    }
                    str = reader.readLine();
                }
            }

            do {
                str = reader.readLine(); // после while перенести
                while((str != null) && (str.contains(repType1) || str.contains(repType2))) {
                    int n1 = str.indexOf(repType1);
                    n1 = n1 == -1 ? Integer.MAX_VALUE : n1;
                    int n2 = str.indexOf(repType2);
                    n2 = n2 == -1 ? Integer.MAX_VALUE : n2;
                    int n = Math.min(n1, n2);
                    str = str.substring(n).trim();
                    String[] arr = str.split("[\\>\\<]");
                    String uprNumber = arr[6].trim();
                    String name = arr[10].replace("РЕМОНТИРОВАТЬ","")
                            .replace("ГЕОМЕТРИЯ","")
                            .replace("ПОЛИРОВ","")
                            .trim().toUpperCase();
                    if(name.equals("EL")) {
                        name = changeNameEl(uprNumber, map);
                    }
                    if(!result.contains(name)) {
                        result += uprNumber + ";" + name + ";2.0;2.0\n";
                    }
                    str = str.substring(2);
                }
            } while(str != null && !str.contains("<EditedOutput><![CDATA"));

        } while(str != null);

        reader.close();
        return result;
    }

    public static Double findWorksInXml(String fileName) throws IOException {
        Double result = 0.0;
        String str;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        do {
            do {
                str = reader.readLine();
            } while((str != null) && !str.contains("СТОИМОСТЬ РАБОТ     НОРМА ВРЕМЕНИ   10 РП = 1 ЧАС"));
            if(str == null) {
                reader.close();
                return result;
            }
            str = reader.readLine();
            List<String> list = Arrays.stream(str.split(" "))
                    .map(x -> x.trim())
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.toList());
            result = Double.parseDouble(list.get(1)) / 10;

            do{
                str = reader.readLine();
            } while((str != null) && !str.contains("<RepTyp>I") && !str.contains("<EditedOutput><![CDATA"));

            if((str != null) && str.contains("<RepTyp>I")) {
                while(str.contains("<RepTyp>I")) { // в цикл выше перенести
                    int n = str.indexOf("<RepTyp>I") + 2;
                    str = str.substring(n).trim();
                    if(str.contains("<WuNet Unit=")) {
                        n = str.indexOf("Val=") + 5;
                        str = str.substring(n).trim();
                        String[] arr = str.split("\"");
                        String strHours = arr[0].trim();
                        result -= Double.parseDouble(strHours) / 10;
                    }
                }
            }

        } while(str != null);

        reader.close();
        return result;
    }

    public static String changeNameEl(String number, Map<String, String> map) {
        if(map.containsKey(number)) {
            return map.get(number);
        } else {
            return "EL";
        }
    }

    public static Map<String, String> getMapNameNumber() {
        Map<String, String> map = new HashMap<>();
        map.putAll(Map.of(
                "1481", "ДВЕРЬ П Л",
                "2583", "БАМПЕР З",
                "0283", "БАМПЕР П",
                "1009", "ПАНЕЛЬ ПЕРЕДКА В СБ",
                "2227",  "А-СТОЙКА НАР Л",
                "3482", "БОКОВИНА З ПР",
                "4280", "ЛОНЖЕРОН ПР ПОЛА",
                "7201", "КОЛЕСНЫЙ ДИСК П Л",
                "9552", "КОЛЁСНЫЙ ДИСК З ПР",
                "0742", "КРЫЛО П ПР"));
        map.putAll(Map.of(
                "2931", "КРЫШКА БАГАЖНИКА",
                "1865", "РУЧКА ДВЕРИ НАР З Л",
                "2711", "ЩИТОК ЗАДКА",
                "0410", "РЕШЕТКА РАДИАТОРА В",
                "0340", "КРОНШ КРЕП БАМПЕРА П",
                "1782", "ДВЕРЬ З ПР",
                "0471", "КАПОТ",
                "2640", "БАЛКА БАМПЕРА З",
                "2656", "ДТЧ З ВНУ ПР ПАРКОВК",
                "2655", "ДТЧ З ВНУ Л ПАРКОВКИ"));
        map.putAll(Map.of(
                "1482", "ДВЕРЬ П ПР",
                "2102", "ПОРОГ ДВЕРИ НАР ПР",
                "3481", "БОКОВИНА З Л",
                "7202", "КОЛЕСНЫЙ ДИСК П ПР",
                "0741", "КРЫЛО П Л",
                "1313", "КРЫШКА БАЧОК",
                "2654", "ДТЧ З НАР ПР ПАРКОВК",
                "9551", "КОЛЁСНЫЙ ДИСК З Л"));

        return map;
    }

}
