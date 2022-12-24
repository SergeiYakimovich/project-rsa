package code;

import code.element.Guide;
import code.element.Order;
import code.check.Checker;
import code.parse.DetailsParser;
import code.parse.csvtype.CsvDetail;
import code.parse.csvtype.CsvOrder;
import code.parse.GuideParser;
import code.parse.OrderParser;
import code.check.Result;
import code.utils.DetailUtils;
import code.utils.GuideUtils;

import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readString;
import static java.nio.file.Files.writeString;

public class App {
    public final static String ORDERS_ALL_DIR = "../data/all";
    public final static String ORDERS_TEST_DIR = "../data/test";
    public final static String MODEL_DIR = "../model/";

    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        List<Order> orders;
        Guide guide;
        List<String> mainDetails = new ArrayList<>();
        List<String> notMainDetails = new ArrayList<>();

//        mainDetails = List.of("БАМП","ДВЕР","ПОДКР","СТЕКЛО","РУЧК","ФОНАР",
//                "БАГАЖ","РАМА", "БАК", "ДИСК","ЛОНЖЕР","ПАНЕЛ");
        notMainDetails = List.of("МЕЛКИЕ ДЕТ","АБСОРБ","АКТИВАТОР","АПЛИКАТОР","К-Т", "УПЛ К-ЦО"
                ,"КРЮК", "НИТЬ", "РАСТВОР", "ЗАГЛУШ");
//      ,"АДСОРБ", "КОЖУХ", "УДЛИН"

//        Сделать список всех деталей по частоте
//        orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
//        DetailUtils.makeUniqDetails(MODEL_DIR + "details-all.csv", orders);


//        Сделать список всех деталей по важности
//        orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
//        DetailUtils.makeNotMainDetails(MODEL_DIR + "not-main-det-all.csv", orders);


//        Получить список важных и неважных деталей
//        notMainDetails = DetailsParser.readDetNames(MODEL_DIR + "not-main-det.csv");
//        mainDetails = DetailsParser.readDetNames(MODEL_DIR + "main-details.csv");


//        Создание модели
        orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
        guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails);
        GuideParser.writeGuide(MODEL_DIR + "guide.json", guide);
        GuideParser.writeGuideAsString(MODEL_DIR + "guide.txt", guide);

//        Проверка модели
        guide = GuideParser.readGuide(MODEL_DIR + "guide.json");
        orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
        List<Result> list = Checker.checkOrders(guide, orders);
        Checker.showResults(list, 10);

//        Проверка тестовых запросов
//        guide = GuideParser.readGuide(MODEL_DIR + "guide.json");
//        orders = OrderParser.getOrdersFromDirectory(ORDERS_TEST_DIR, new CsvOrder());
//        Checker.checkTestOrders(guide,orders);

    }

}

//    Создание exe из jar
//    https://javarush.ru/groups/posts/1352-kak-sozdatjh-ispolnjaemihy-jar-v-intellij-idea--how-to-create-jar-in-idea
//    Меню : Build Artifacts
//    https://genuinecoder.com/online-converter/jar-to-exe/
