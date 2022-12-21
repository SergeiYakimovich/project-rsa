package code.parse;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.readString;

/**
 * getDetailsFromFile() - получить из файла список списка деталей
 *                        каждая строка файла - список деталей, разделеннх ";"
 */
public class DetailsParser {


    public static List<List<String>> getDetailsFromFile(String fileName) throws Exception {
        List<List<String>> detailsList = new ArrayList<>();
        String text;
        try {
            text = readString(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("!!! Ошибка чтения из файла " + fileName);
            System.out.println("!!! " + e.getMessage());
            return null;
        }
        String[] stroki = text.split("\n");
        for(int i = 0; i < stroki.length; i++) {
            String[] mas = stroki[i].split(";");
            if(mas.length != 0) {
                List<String> details = Arrays.asList(mas);
                detailsList.add(details);
            }
        }
        return detailsList;
    }

}
