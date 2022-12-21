package code.parse;

import code.element.Detail;
import code.element.Order;
import code.element.Work;
import code.service.DetailService;
import code.service.OrderService;
import code.service.WorkService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import static java.nio.file.Files.readString;

import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
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
        System.out.println("Load " + orders.size() + " orders from " + files.length);
        return orders;
    }

    public static List<Order> getOrdersFromDirectory(String dir) throws Exception {
        return  getOrdersFromDirectory(dir, new CsvOrder());
    }

//    public static Order getOrderFromFile(String fileName) throws Exception {
//        List<Detail> details = new ArrayList<>();
//        List<Work> works = new ArrayList<>();
//        Order result = OrderService.makeOrder(fileName);
//        String text;
//        try {
//            text = readString(Paths.get(fileName), StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            System.out.println("! Ошибка чтения из файла " + fileName);
//            return null;
//        }
//        String[] stroki = text.split("\n");
//        for(int i = 1; i < stroki.length - 1; i++) {
//            String[] mas = new String[4];
//            mas = stroki[i].split(";");
//            if(mas[0] != "") {
//                Detail newDetail = DetailService.makeDetail(mas);
//                details.add(newDetail);
////                System.out.println(newDetail.toString());
//            } else {
//                Work newWork = WorkService.makeWork(mas);
//                works.add(newWork);
////                System.out.println(newWork.toString());
//            }
//        }
//        result.setDetails(details);
//        result.setWorks(works);
////        System.out.println(result.toString());
//        return result;
//    }

}
