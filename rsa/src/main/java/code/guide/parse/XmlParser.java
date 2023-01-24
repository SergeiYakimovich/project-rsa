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
import java.util.List;
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

    public static boolean convertOrderFromXmlToCsv (String fileName) throws Exception {
//        System.out.println(fileName);
        StringBuffer buffer = new StringBuffer();
        buffer.append("УПР №;НАЗВАНИЕ;1.0;1.0\n");

        String str1 = findDetailsInXml(fileName);
        if(str1 == null || str1.length() == 0) {
            str1 = "";
//            System.out.println("З/ч под замену не найдены " + fileName);
        } else {
            buffer.append(str1);
        }

        String str2 = findRepaireDetailsInXml(fileName, str1, "<RepTyp>I");
        if(str2 == null || str2.length() == 0) {
            str2 = "";
        } else {
//            System.out.println(str2);
            buffer.append(str2);
        }

        String str3 = findRepaireDetailsInXml(fileName, str1 + str2, "<RepTyp>L");
        if(str3 == null || str3.length() == 0) {
            str3 = "";
        } else {
//            System.out.println(str3);
            buffer.append(str3);
        }

        if(str1 == "" && str2 == "" && str3 == "") {
            System.out.println("З/ч не найдены " + fileName);
            return false;
        }

        String strWorks = findWorksInXml(fileName);
        if(strWorks == null || strWorks.length() == 0) {
            System.out.println("Работы не найдены " + fileName);
            return false;
        } else {
            buffer.append(strWorks);
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
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        do {
            str = reader.readLine();
        } while((str != null) && !(str.contains("УПР № ")
                && str.contains(" НАЗВАНИЕ             № ДЕТАЛИ                         СТОИМ"))
        );
        if(str == null) {
            reader.close();
            return null;
        }
        while(!str.contains("----------") && !str.contains("СИСТЕМА AUDATEX")) {
            str = str.trim();
            List<String> list = Arrays.stream(str.split(" "))
                    .map(x -> x.trim())
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.toList());
            if(list.size() != 0) {
                String uprNumber = str.substring(0,8).trim();
                int n = str.length() < 37 ? str.length() : 37;
                String name = str.substring(16,n).trim().toUpperCase();
//                String number = str.substring(37,70).trim();
//                String count = str.substring(8,16).trim();
//                String sum = str.substring(70,75).trim();

//                System.out.println(str);
//                System.out.println(uprNumber + " " + name + " " + number);
                if(name.length() == 1) {
                    System.out.println("!? : имя з/ч = " + name);
                }
                if(!uprNumber.equals("УПР №")) {
                    result += uprNumber + ";" + name + ";" + "1.0" + ";" + "1.0" + "\n";
                }
            }
            str = reader.readLine();
        }
        reader.close();
        return result;
    }

    public static String findWorksInXml(String fileName) throws IOException {
        String result = "";
        String str;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        do {
            str = reader.readLine();
        } while((str != null) && !str.contains("СТОИМОСТЬ РАБОТ     НОРМА ВРЕМЕНИ   10 РП = 1 ЧАС"));
        if(str == null) {
            reader.close();
            return null;
        }
        str = reader.readLine();
        List<String> list = Arrays.stream(str.split(" "))
                .map(x -> x.trim())
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toList());

        double hours = Double.parseDouble(list.get(1)) / 10;
        str = String.format("%.2f", hours);
        str = str.replaceAll(",", ".");
        result += ";Работы;" + str + ";";

        reader.close();
        return result;
    }

    public static String findRepaireDetailsInXml(String fileName, String details, String repType) throws IOException {
        String result = "";
        String str;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        do {
            str = reader.readLine();
            while((str != null) && str.contains(repType)) {
                int n = str.indexOf(repType);
                str = str.substring(n).trim();
                String[] arr = str.split("[\\>\\<]");
                String uprNumber = arr[6].trim();
                String name = arr[10].replace("РЕМОНТИРОВАТЬ","")
                        .replace("ГЕОМЕТРИЯ","").trim().toUpperCase();
                if(!result.contains(name) && !details.contains(name)) {
                    String s;
                    if(repType.contains(">L")) {
                        s = "3.0";
                    } else {
                        s = "2.0";
                    }
                    result += uprNumber + ";" + name + ";" + s + ";" + s + "\n";
                }
                str = str.substring(2);
            }
        } while(str != null);

        reader.close();
        return result;
    }
}
