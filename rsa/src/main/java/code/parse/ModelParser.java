package code.parse;

import code.calc.Model;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.nio.file.Files.readString;

/**
 * makeModel() - создать модель из 2-х файлов (Single и Set)
 * getModelFromCsvFile() - получить часть модели из файла
 */
public class ModelParser {

    public static Model makeModel(String fileSingle, String fileSet) throws Exception {
        Model model = new Model();
        String[] arr = fileSingle.split("/");
        String nameSingle = arr[arr.length - 1];
        arr = fileSet.split("/");
        String nameSet = arr[arr.length - 1];
        model.setName(nameSingle + "/" + nameSet);
        model.setSingleDetails(getModelFromCsvFile(fileSingle));
        model.setSetDetails(getModelFromCsvFile(fileSet));
        System.out.println(model.toString());
        return model;
    }

    public static Map<List<String>,Double> getModelFromCsvFile(String fileName) throws Exception {
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
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        CsvMapper mapper = new CsvMapper().enable(CsvParser.Feature.WRAP_AS_ARRAY);
        CsvSchema schema = mapper.schemaFor(List.class).withColumnSeparator(';');
        MappingIterator<List<String>> iterator = mapper
                .readerFor(List.class)
                .with(schema)
                .readValues(myReader);
        Map<List<String>, Double> elements;

        try {
             elements = iterator.readAll().stream().collect(Collectors.toMap(
                     x -> x.stream().limit(x.size() - 1).sorted().collect(Collectors.toList()),
                     x -> Double.valueOf(x.get(x.size() - 1))
             ));
        } catch (Exception e) {
            System.out.println("! Ошибка чтения из файла " + fileName);
            return null;
        }
        result.putAll(elements);
        return result;
    }

//    public static Map<List<String>,Double> getModelFromFile(String file) throws Exception {
//        Map<List<String>,Double> result = new TreeMap<>(new Comparator<List<String>>() {
//            @Override
//            public int compare(List<String> o1, List<String> o2) {
//                int lengthDifference = o2.size() - o1.size();
//                if (lengthDifference != 0) return lengthDifference;
//                String s1 = o1.stream().collect(Collectors.joining());
//                String s2 = o2.stream().collect(Collectors.joining());
//                return s2.compareTo(s1);
//            }
//        });
//        String text = "";
//        try {
//            text = readString(Paths.get(file), StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            System.out.println("! Ошибка чтения из файла " + file);
//            return null;
//        }
//        String[] stroki = text.split("\n");
//        for(int i = 0; i < stroki.length; i++) {
//            String[] mas = stroki[i].split(";");
//            List<String> detailNames = new ArrayList<>();
//            if(mas.length > 1) {
//                for(int j = 0; j < mas.length - 1; j++) {
//                    detailNames.add(mas[j]);
//                }
//            } else {
//                System.out.println("Ошибка данных в файле " + file + "   " + String.join(";", mas));
//            }
//            Double hours = 0.0;
//            try {
//                hours = Double.parseDouble(mas[mas.length - 1]);
//            } catch (Exception e) {
//                System.out.println("Ошибка данных в файле " + file + "   " + String.join(";", mas));
//            }
//            detailNames = detailNames.stream().sorted().collect(Collectors.toList());
//            result.put(detailNames, hours);
//        }
//        return result;
//    }

}
