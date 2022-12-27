package code.parse;

import code.App;
import code.element.Detail;
import code.element.Guide;
import code.element.Order;
import code.element.Work;
import code.parse.csvtype.CsvElement;
import code.parse.csvtype.CsvOrder;
import code.parse.csvtype.CsvText;
import code.service.OrderService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                if(type.getClass() == CsvText.class) {
                    order = getOrderFromTextFile(file.toString());
                } else {
                    order = getOrderFromCsvFile(file.toString(), type);
                }
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
