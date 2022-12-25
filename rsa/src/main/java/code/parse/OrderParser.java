package code.parse;

import code.element.Detail;
import code.element.Order;
import code.element.Work;
import code.parse.csvtype.CsvElement;
import code.parse.csvtype.CsvOrder;
import code.service.OrderService;
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
 */
public class OrderParser {

    public static <T extends CsvElement> Order getOrderFromCsvFile (String fileName, T element ) throws Exception {
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(element.getClass())
                .withColumnSeparator(';').withSkipFirstDataRow(true);
        MappingIterator<T> iterator = mapper
                .readerFor(element.getClass())
                .with(schema)
                .readValues(myReader);
        List<T> elements;
        try {
            elements = iterator.readAll();
        } catch (Exception e) {
            System.out.println("Ошибка чтения из файла " + fileName);
            return null;
        }
        List<Detail> details = new ArrayList<>();
        List<Work> works = new ArrayList<>();
        Order result = OrderService.makeOrder(fileName);
        for (T item : elements) {
            if(!item.getNumber().isEmpty()) {
                Detail newDetail = item.makeDetailFromCsvElement();
                details.add(newDetail);
            } else if(!item.getName().equals("Сумма") && !item.getName().isEmpty()) {
                Work newWork = item.makeWorkFromCsvElement();
                works.add(newWork);
            }
        }
        result.setDetails(details);
        result.setWorks(works);
        return result;
    }

    public static List<Order> getOrdersFromDirectory(String dir, CsvElement type) throws Exception {
        List<Order> orders = new ArrayList<>();
        File[] files = new File(dir).listFiles();
        for(File file : files) {
            String s = file.toString();
            if(file.isDirectory()) {
                orders.addAll(getOrdersFromDirectory(s,type));
            } else {
                Order order = getOrderFromCsvFile(file.toString(), type);
                if (order != null && !order.isOrderEmpty()) {
                    orders.add(order);
                }
            }
        }
        System.out.println("Прочитано " + orders.size() + " з/н из " + files.length);
        return orders;
    }

    public static List<Order> getOrdersFromDirectory(String dir) throws Exception {
        return  getOrdersFromDirectory(dir, new CsvOrder());
    }

}
