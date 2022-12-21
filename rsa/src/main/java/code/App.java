package code;

import code.calc.Calculator;
import code.calc.Model;
import code.element.Detail;
import code.element.Order;
import code.element.Work;
import code.check.Checker;
import code.parse.CsvOrder;
import code.parse.DetailsParser;
import code.parse.ModelParser;
import code.parse.OrderParser;
import code.check.Result;
import code.service.DetailService;
import code.service.WorkService;
import code.utils.DetailUtils;
import code.utils.Handler;
import code.utils.ModelUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.nio.file.Files.readString;
import static java.nio.file.Files.writeString;

public class App {
    public final static String ORDERS_ALL_DIR = "../data/all";
    public final static String ORDERS_MANY_DIR = "../data/all/many";
    public final static String ORDERS_TEST_DIR = "../data/test";
    public final static String MODEL_DIR = "../model/";
    public final static String FILE_SINGLE = "single.csv";
    public final static String FILE_SET = "set.csv";
    public final static String FILE_UNIQ_SET = "uniq-sets.txt";

    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        List<Order> orders;
        Order order;
        Detail detail;
        List<Detail> details;
        Work work;
        List<Work> works;
        Result result;
        Model model;
        List<String> notMainDetails = new ArrayList<>();
//        notMainDetails = List.of("МЕЛКИЕ ДЕТ","АБСОРБ","АКТИВАТОР","АПЛИКАТОР","К-Т", "УПЛ К-ЦО"
//                ,"КРЮК", "НИТЬ", "РАСТВОР", "ЗАГЛУШ");
//      ,"АДСОРБ", "КОЖУХ", "УДЛИН"


//        Сделать список всех деталей по важности
//        DetailUtils.makeNotMainDetails();

//        Получить список не важных деталей
//        notMainDetails = DetailUtils.getNotMainDetails();


//        Создание модели
        ModelUtils.makeModelUniqSets(ORDERS_MANY_DIR, notMainDetails);

//        Проверка модели
        model = ModelParser.makeModel(MODEL_DIR + FILE_SINGLE, MODEL_DIR + FILE_UNIQ_SET);
        orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
        List<Result> list = Checker.checkOrders(model, orders, Calculator.CheckType.ALL);
        Checker.showResults(list, 10);

//        Проверка тестовых запросов
        model = ModelParser.makeModel(MODEL_DIR + FILE_SINGLE, MODEL_DIR + FILE_UNIQ_SET);
        orders = OrderParser.getOrdersFromDirectory(ORDERS_TEST_DIR, new CsvOrder());
        Calculator.calcOrders(model,orders, Calculator.CheckType.ALL);



    }

}

//    Создание exe из jar
//    https://javarush.ru/groups/posts/1352-kak-sozdatjh-ispolnjaemihy-jar-v-intellij-idea--how-to-create-jar-in-idea
//    Меню : Build Artifacts
//    https://genuinecoder.com/online-converter/jar-to-exe/
