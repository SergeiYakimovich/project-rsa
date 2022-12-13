package code;

import code.calc.Calculator;
import code.calc.Model;
import code.element.Detail;
import code.element.Order;
import code.element.Work;
import code.check.Checker;
import code.parse.DetailsParser;
import code.parse.ModelParser;
import code.parse.OrderParser;
import code.check.Result;
import code.utils.DetailUtils;
import code.utils.Handler;
import code.utils.ModelUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.file.Files.readString;
import static java.nio.file.Files.writeString;

public class App {
    public final static String ORDERS_DIR = "../data/";
    public final static String MODEL_DIR = "../model/";
    public final static String FILE_SINGLE = "single.csv";
    public final static String FILE_SET = "out-set.txt";
//    public final static String FILE_SET = "set.csv";

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

//        Проверка модели
        model = ModelParser.makeModel(MODEL_DIR + FILE_SINGLE, MODEL_DIR + FILE_SET);
        orders = OrderParser.getOrdersFromDirectory(ORDERS_DIR);
        List<Result> list = Checker.checkOrders(model, orders, Calculator.CheckType.ALL);
        Checker.showResults(list, 100);

//        Map<String, Integer> det_Uniq = DetailUtils.countUniqDetails();
//        DetailUtils.makeSetsOfDetails(det_Uniq, 5);


//        ModelUtils.makeModelUniqSets(ORDERS_DIR);


    }

}

//    Создание exe из jar
//    https://javarush.ru/groups/posts/1352-kak-sozdatjh-ispolnjaemihy-jar-v-intellij-idea--how-to-create-jar-in-idea
//    Меню : Build Artifacts
//    https://genuinecoder.com/online-converter/jar-to-exe/
