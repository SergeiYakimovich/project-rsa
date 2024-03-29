package code.guide.parse;

import code.guide.parse.csvtype.CsvDetail;
import code.guide.utils.MyConsts;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.readString;
import static java.nio.file.Files.writeString;

/**
 * класс для парсинга деталей
 */
public class DetailsParser {

    /**
     * получить список деталей из файла (тип файла CsvDetail - 2 позиции)
     * @param fileName - имя файла
     * @return - список деталей - 2 позиции
     * @throws IOException
     */
    public static List<CsvDetail> readDetNameNumber(String fileName) throws IOException {
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvDetail.class)
                .withColumnSeparator(';').withSkipFirstDataRow(true);
        MappingIterator<CsvDetail> iterator = mapper
                .readerFor(CsvDetail.class)
                .with(schema)
                .readValues(myReader);
        List<CsvDetail> elements = iterator.readAll();

        return elements;
    }

    /**
     * получить список имен з/ч из файла (тип файла CsvDetail - 2 позиции)
     * @param fileName - имя файла
     * @return - список имен з/ч
     * @throws IOException
     */
    public static List<String> readDetNames(String fileName) throws IOException {
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvDetail.class)
                .withColumnSeparator(';').withSkipFirstDataRow(true);
        MappingIterator<CsvDetail> iterator = mapper
                .readerFor(CsvDetail.class)
                .with(schema)
                .readValues(myReader);
        List<CsvDetail> elements = iterator.readAll();

        return elements.stream()
                .map(x -> x.getName())
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * записать список имен з/ч в файл (тип файла CsvDetail - 2 позиции)
     * @param fileName - имя файла
     * @param elements - список деталей - 2 позиции
     * @throws IOException
     */
    public static void writeDetNames(String fileName, List<CsvDetail> elements ) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvDetail.class)
                .withColumnSeparator(';')
                .withoutQuoteChar()
                .withHeader();
        ObjectWriter writer = mapper.writer(schema);
        writer.writeValue(new FileWriter(fileName, StandardCharsets.UTF_8),elements);
    }

    /**
     * получить список имен з/ч из файла (только имена в файле)
     * @param fileName - имя файла
     * @return - список имен з/ч
     * @throws IOException
     */

    public static List<String> readDetOnlyNames(String fileName) throws IOException {
        String text = readString(Paths.get(fileName), StandardCharsets.UTF_8);
        List<String> list = Arrays.stream(text.split("[\n,;]"))
                .map(x -> x.trim())
                .filter(s -> !s.isEmpty())
                .filter(s -> !s.contains("Наименование"))
                .sorted()
                .collect(Collectors.toList());
        return list;
    }

    /**
     * записать список имен з/ч в файл (только имена)
     * @param fileName - имя файла
     *  @param elements - список имен з/ч
     * @throws IOException
     */
    public static void writeDetOnlyNames(String fileName, List<String> elements ) throws IOException {
        String text = elements.stream().collect(Collectors.joining("\n"));
        writeString(Paths.get(fileName), text, StandardCharsets.UTF_8);
    }
    /**
     * получение списка важных деталей из 2-х файлов (DET_MAIN и DET_MAIN_E)
     * @return - список важных деталей
     */
    public static List<String> getMainDets() {
        List<String> mainDetails = new ArrayList<>();

        List<String> list1 = getMainDetFromFile(MyConsts.DET_MAIN);
        mainDetails.addAll(list1);

        List<String> list2 = getMainDetFromFile(MyConsts.DET_MAIN_E);
        list2 = list2.stream()
                .map(x -> x + " ЗАМЕНА")
                .collect(Collectors.toList());
        mainDetails.addAll(list2);


        return mainDetails;
    }

    /**
     * получение списка важных деталей из файла
     * @param fileName - имя файла
     * @return - список важных деталей
     */
    public static List<String> getMainDetFromFile(String fileName) {
        List<CsvDetail> elements;

        try {
            elements = DetailsParser.readDetNameNumber(fileName);
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла " + fileName);
            return new ArrayList<>();
        }

        return elements.stream()
                .map(x -> MyConsts.IS_NAME_MAIN ? x.getName() : x.getCount())
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * получение списка лишних деталей из файла DET_NOT_MAIN
     * @return - список лишних деталей
     */
    public static List<String> getNotMainDet() {
        List<String> notMainDetails;
        try {
            notMainDetails = DetailsParser.readDetNames(MyConsts.DET_NOT_MAIN);
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла " + MyConsts.DET_NOT_MAIN);
            return new ArrayList<>();
        }
        return notMainDetails;
    }

}
