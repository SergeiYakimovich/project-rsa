package code.guide.parse;

import code.guide.element.Order;
import code.guide.parse.csvtype.CsvElement;
import code.guide.parse.csvtype.CsvText;
import code.guide.service.OrderService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import static java.nio.file.Files.readString;

import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * getOrderFromCsvFile() - получить з/н из Csv файла
 * getOrdersFromDirectory() - получить список з/н из директории
 * getOrderFromTextFile() - получить з/н из текстового файла
 */
public class OrderParser {

    public static Order getOrderFromCsvFile (String fileName, CsvElement element) throws Exception {
        if(element.getClass() == CsvText.class) {
            Order order = getOrderFromTextFile(fileName);
            return order;
        }
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(element.getClass())
                .withColumnSeparator(';').withSkipFirstDataRow(true);
        MappingIterator<CsvElement> iterator = mapper
                .readerFor(element.getClass())
                .with(schema)
                .readValues(myReader);
        List<CsvElement> csvElements;
        try {
            csvElements = iterator.readAll();
        } catch (Exception e) {
            System.out.println("Ошибка чтения из файла " + fileName);
            return null;
        }
        return element.makeOrderFromCsvElement(csvElements, fileName);
    }

    public static <T> List<Order> getOrdersFromDirectory(String dir, CsvElement type) throws Exception {
        List<Order> orders = new ArrayList<>();
        File[] files = new File(dir).listFiles();
        Order order;
        for(File file : files) {
            String s = file.toString();
            if(file.isDirectory()) {
                orders.addAll(getOrdersFromDirectory(s,type));
            } else {
                order = getOrderFromCsvFile(file.toString(), type);
                if (order != null && !order.isOrderEmpty()) {
                    orders.add(order);
                }
            }
        }
        System.out.println("Прочитано " + orders.size() + " з/н из " + files.length);
        return orders;
    }

    public static Order getOrderFromTextFile(String fileName) {
        List<String> elements = new ArrayList<>();
        try {
            elements = DetailsParser.readDetOnlyNames(fileName);
        } catch (Exception e) {
            System.out.println("Ошибка чтения из файла " + fileName);
            return null;
        }
        return OrderService.makeSimpleOrder(elements, fileName);
    }

}
