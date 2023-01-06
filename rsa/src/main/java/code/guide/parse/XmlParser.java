package code.guide.parse;

import code.guide.element.Order;
import code.guide.parse.csvtype.CsvElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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


public class XmlParser {

    public static boolean convertOrderFromXmlToCsv (String fileName) throws Exception {
//        System.out.println(fileName);
        StringBuffer buffer = new StringBuffer();
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
        String str;

        do {
            str = reader.readLine();
        } while((str != null) && !(str.contains("УПР № ")
                && str.contains(" НАЗВАНИЕ             № ДЕТАЛИ                         СТОИМ"))
        );
        if(str == null) {
            System.out.println("Ошибка чтения из файла " + fileName);
            return false;
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
                String name = str.substring(16,n).trim();
//                String number = str.substring(37,70).trim();
//                String count = str.substring(8,16).trim();
//                String sum = str.substring(70,75).trim();

//                System.out.println(str);
//                System.out.println(uprNumber + " " + name + " " + number);

                buffer.append(uprNumber + ";" + name + ";" + "1.0" + ";" + "1.0" + "\n");
            }
            str = reader.readLine();
        }
        do {
            str = reader.readLine();
        } while((str != null) && !str.contains("СТОИМОСТЬ РАБОТ     НОРМА ВРЕМЕНИ   10 РП = 1 ЧАС"));
        if(str == null) {
            System.out.println("Ошибка чтения из файла " + fileName);
            return false;
        }
        str = reader.readLine();
        List<String> list = Arrays.stream(str.split(" "))
                .map(x -> x.trim())
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toList());

        double hours = Double.parseDouble(list.get(1)) / 10;
        str = String.format("%.2f", hours);
        str = str.replaceAll(",", ".");

//        char last = str.charAt(str.length() - 1);
//        str = str.substring(0,str.length() - 1) + "." + last;
        buffer.append(";Работы;" + str + ";");

        String[] mas = fileName.split(Pattern.quote("\\"));
        str = mas[mas.length - 1];
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
}
