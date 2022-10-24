package code.parse;

import code.calc.Model;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readString;

public class ModelParser {

    public static Model makeModel(String fileSingle, String fileSet) throws Exception {
        Model model = new Model();
        model.setName(fileSingle + "+" + fileSet);
        model.setListSingle(getModelFromFile(fileSingle));
        model.setListSet(getModelFromFile(fileSet));
        System.out.println(model.toString());
        return model;
    }

    public static List<Map<List<String>,Double>> getModelFromFile(String file) throws Exception {
        List<Map<List<String>,Double>> list = new ArrayList<>();
        String text = "";
        try {
            text = readString(Paths.get(file), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("!!! Ошибка чтения из файла " + file);
            return null;
        }
        String[] stroki = text.split("\n");
        for(int i = 0; i < stroki.length; i++) {
            String[] mas = stroki[i].split(";");
            Map<List<String>, Double> map = new HashMap<>();
            List<String> detailNames = new ArrayList<>();
            if(mas.length > 1) {
                for(int j = 0; j < mas.length - 1; j++) {
                    detailNames.add(mas[j]);
                }
            } else {
                System.out.println("Ошибка данных в файле " + file + "   " + String.join(";", mas));
            }
            Double hours = 0.0;
            try {
                hours = Double.parseDouble(mas[mas.length - 1]);
            } catch (Exception e) {
                System.out.println("Ошибка данных в файле " + file + "   " + String.join(";", mas));
            }
            map.put(detailNames, hours);
            list.add(map);
        }
        return list;
    }

}
