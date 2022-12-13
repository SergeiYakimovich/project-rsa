package code.parse;

import code.calc.Model;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.nio.file.Files.readString;

/**
 * makeModel() - создать модель из 2-х файлов (Single и Set)
 * getModelFromFile() - получить часть модели из файла
 */
public class ModelParser {

    public static Model makeModel(String fileSingle, String fileSet) throws Exception {
        Model model = new Model();
        model.setName(fileSingle + "+" + fileSet);
        model.setSingleDetails(getModelFromFile(fileSingle));
        model.setSetDetails(getModelFromFile(fileSet));
        System.out.println(model.toString());
        return model;
    }

    public static Map<List<String>,Double> getModelFromFile(String file) throws Exception {
        Map<List<String>,Double> result = new TreeMap<>(new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                int lengthDifference = o2.size() - o1.size();
                if (lengthDifference != 0) return lengthDifference;
                String s1 = o1.stream().collect(Collectors.joining());
                String s2 = o2.stream().collect(Collectors.joining());
                return s2.compareTo(s1);
            }
        });
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
            detailNames = detailNames.stream().sorted().collect(Collectors.toList());
            result.put(detailNames, hours);
        }
        return result;
    }

}
