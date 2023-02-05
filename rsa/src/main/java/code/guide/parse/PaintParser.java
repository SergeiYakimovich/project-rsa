package code.guide.parse;

import code.guide.utils.MyConsts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.nio.file.Files.writeString;

public class PaintParser {
    public static boolean convertOrderFromXmlToCsv (String fileName) throws Exception {
//        System.out.println(fileName);
        StringBuffer buffer = new StringBuffer();
        buffer.append("УПР №;НАЗВАНИЕ; 1.0; 1.0\n");

        String str1 = findPaintInXml(fileName);
        if(str1 == null || str1.length() == 0) {
            System.out.println("З/ч или покраска не найдены " + fileName);
            return false;
        }
        if(!str1.contains(";1.0;1.0")) {
            System.out.println("З/ч не найдены " + fileName);
            return false;
        }
        if(!str1.contains("Подготовительные работы") &&
                !str1.contains("Окрасочные работы") &&
                !str1.contains("Стоимость покраски")) {
            System.out.println("Покраска не найдена " + fileName);
            return false;
        }

        buffer.append(str1);

        String[] mas = fileName.split(Pattern.quote("\\"));
        String str = mas[mas.length - 1];
        String fileOut = str.substring(0, str.length() - 3) + "csv";
        writeString(Paths.get(MyConsts.PAINT_ORDERS_DIR + fileOut), buffer.toString(), StandardCharsets.UTF_8);
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

    public static String findPaintInXml(String fileName) throws IOException {
        String result = "";
        String str;
        String name, uprNumber;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        do {
            do {
                str = reader.readLine();
            } while((str != null)
                    && !(str.contains("О К Р А С К А         (СИСТЕМА AZT С ДАННЫМИ АУДАТЭКС)")));
            if(str == null) {
                reader.close();
                return result;
            }

            result = "";
            double prepareHours = 0;
            double colorHours = 0;
            double colorCost = 0;

            str = reader.readLine();
            str = reader.readLine();
            str = reader.readLine();
            str = reader.readLine();
            while(str != null && !str.contains("ЛАКОКРАСОЧНЫЙ МАТЕРИАЛ ЗА ДЕТАЛЬ")
                    && !str.contains("СИСТЕМА AUDATEX")
                    && !str.contains("ЗАТРАТЫ ВРЕМЕНИ НА ОКРАСКУ")) {
                str = str.trim();
                List<String> list = Arrays.stream(str.split(" "))
                        .map(x -> x.trim())
                        .filter(x -> !x.isEmpty())
                        .collect(Collectors.toList());
                if(list.size() != 0 && !str.contains("ЕЩЕ ОСТАЛАСЬ")
                        && !str.contains("АКРИЛОВ ОКРАСКА")
                        && !str.contains("ПРОЗР КРАСКА")
                        && !str.contains("МОКРЫМ ПО МО")) {
                    if(str.length() > 15) {
                        uprNumber = str.substring(0,5).trim();
                        name = str.substring(5,str.length() - 5).trim();
                    } else {
                        uprNumber = "0000";
                        name = str;
                    }
                    name = name.replaceAll("   "," ")
                            .replaceAll("   "," ")
                            .replaceAll("   "," ");
                    result += uprNumber + ";" + name + ";1.0;1.0\n";
                }
                str = reader.readLine();
            }
            if(str == null) {
                reader.close();
                return result;
            }
            if(!str.contains("ЗАТРАТЫ ВРЕМЕНИ НА ОКРАСКУ")) {
                do {
                    str = reader.readLine();
                } while((str != null)
                        && !str.contains("ЗАТРАТЫ ВРЕМЕНИ НА ОКРАСКУ")
                        && !str.contains("О К О Н Ч А Т Е Л Ь Н А Я  К А Л Ь К У Л Я Ц И Я"));
                if(str == null) {
                    reader.close();
                    return result;
                }
            }

            if(str.contains("ЗАТРАТЫ ВРЕМЕНИ НА ОКРАСКУ")) {
                str = reader.readLine();
                str = reader.readLine();

                while(str != null && !str.contains("ОБЩЕЕ ВРЕМЯ ОКРАС.")) {
                    str = str.trim();
                    List<String> list = Arrays.stream(str.split(" "))
                            .map(x -> x.trim())
                            .filter(x -> !x.isEmpty())
                            .collect(Collectors.toList());
                    if(list.size() != 0) {
                        name = str.substring(0,str.length() - 5).trim();
                        String s = str.substring(str.length() - 5)
                                .replaceAll("\\*","").trim();
                        if(name.contains("ВРЕМЯ ОКРАСКИ")) {
                            colorHours += Double.parseDouble(s) / 10;
                        } else {
                            if(name.contains("ПОДГ")) {
                                prepareHours += Double.parseDouble(s) / 10;
                            }
                        }
                    }
                    str = reader.readLine();
                }

                do {
                    str = reader.readLine();
                } while((str != null)
                        && !(str.contains("О К О Н Ч А Т Е Л Ь Н А Я  К А Л Ь К У Л Я Ц И Я")));
                if(str == null) {
                    reader.close();
                    return result;
                }
            }

            do {
                str = reader.readLine();
            } while((str != null)
                    && !(str.contains("ЗАТРАТЫ НА МАТЕРИАЛ")));
            if(str == null) {
                reader.close();
                return result;
            }
            String s = str.replaceAll("\\*","").trim();
            s = s.substring(s.length() - 8).replaceAll(" ", "");
            colorCost += Double.parseDouble(s);

            String strHours = String.format("%.2f", prepareHours);
            strHours = strHours.replaceAll(",", ".");
            result += ";Подготовительные работы;" + strHours + ";\n";

            strHours = String.format("%.2f", colorHours);
            strHours = strHours.replaceAll(",", ".");
            result += ";Окрасочные работы;" + strHours + ";\n";

            strHours = String.format("%.2f", colorCost);
            strHours = strHours.replaceAll(",", ".");
            result += ";Стоимость покраски;" + strHours + ";\n";

            do {
                str = reader.readLine();
            } while(str != null && !str.contains("<EditedOutput><![CDATA")
                    && !str.contains("<PaintSystemTyp>"));
            if(str != null && str.contains("<PaintSystemTyp>")) {
                int n = str.indexOf("PaintSystemTyp>");
                String subStr = str.substring(n);
                String[] arr = subStr.split("[\\>\\<]");
                name = arr[1].trim();
                uprNumber = name;
                result = uprNumber + ";" + name + ";" + "1.0" + ";" + "1.0" + "\n" + result;
            }

            while(str != null && !str.contains("<EditedOutput><![CDATA")) {
                str = reader.readLine();
            }

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
                while((str != null) && str.contains("<RepTyp>I")) {
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
            } while((str != null) && !str.contains("<EditedOutput><![CDATA"));

        } while(str != null);

        reader.close();
        return result;
    }
}
