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
import java.util.Scanner;

import static java.nio.file.Files.readString;
import static java.nio.file.Files.size;
import static java.nio.file.Files.writeString;

public class App {
    public final static String ORDERS_ALL_DIR = "../data/all";
    public final static String ORDERS_TEST_DIR = "../data/test";
    public final static String MODEL_DIR = "../model/";
    public final static String DET_FREQUENCY = MODEL_DIR + "det-frequency.csv";
    public final static String DET_IMPORTANCE = MODEL_DIR + "det-importance.csv";
    public final static String DET_MAIN = MODEL_DIR + "det-main.csv";
    public final static String DET_NOT_MAIN = MODEL_DIR + "det-not-main.csv";
    public final static String GUIDE_FILE = MODEL_DIR + "guide.json";
    public final static String GUIDE_TEXT_FILE = MODEL_DIR + "guide.txt";
    public final static List<String> COLOR_WORK_NAMES = List.of("ОКР","ВЫКРАС", "КОЛЕР");

    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        List<Order> orders;
        Guide guide;

        List<String> mainDetails = new ArrayList<>();
//        mainDetails = List.of("БАМП","ДВЕР","ПОДКР","СТЕКЛО","РУЧК","ФОНАР",
//                "БАГАЖ","РАМА", "БАК", "ДИСК","ЛОНЖЕР","ПАНЕЛ");

        List<String> notMainDetails = new ArrayList<>();
        notMainDetails = List.of("МЕЛКИЕ ДЕТ","АБСОРБ","АКТИВАТОР","АПЛИКАТОР","К-Т", "УПЛ К-ЦО"
                ,"КРЮК", "НИТЬ", "РАСТВОР", "ЗАГЛУШ", "ГРУНТ","АДСОРБ", "КОЖУХ", "УДЛИН");
//      ,"АДСОРБ", "КОЖУХ", "УДЛИН"
//        notMainDetails = DetailsParser.readDetNames(DET_NOT_MAIN);


        Scanner scanner = new Scanner(System.in);
        showHelp();
        int n = scanner.nextInt();
        System.out.println();
        switch (n) {
            case 1 : //        Проанализировать детали (по частоте и важности)
                orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
                DetailUtils.analizeDetails(orders);
                break;
            case 2 : //        Создание модели
                orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
                mainDetails = DetailsParser.readDetNames(DET_MAIN);
//                notMainDetails = DetailsParser.readDetNames(DET_NOT_MAIN);
                guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails);
                GuideParser.writeGuide(GUIDE_FILE, guide);
                GuideParser.writeGuideAsString(GUIDE_TEXT_FILE, guide);
                break;
            case 3 : //        Проверка модели
                guide = GuideParser.readGuide(GUIDE_FILE);
                orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
                List<Result> list = Checker.checkOrders(guide, orders);
                Checker.showResults(list, 10);
                break;
            case 4 : //        Проверка тестовых запросов
                guide = GuideParser.readGuide(GUIDE_FILE);
                orders = OrderParser.getOrdersFromDirectory(ORDERS_TEST_DIR, new CsvOrder());
                Checker.checkTestOrders(guide,orders);
                break;
        }
    }

    public static void showHelp(){
        System.out.println("\n1 - Проанализировать детали (по частоте и важности)");
        System.out.println("2 - Создать справочник");
        System.out.println("3 - Проверка справочника на всех данных");
        System.out.println("4 - Проверка справочника на тестовых запросах");
        System.out.println("0 - Выход");
        System.out.print("\nВведите число - ");
    }

}

//    Создание exe из jar
//    https://javarush.ru/groups/posts/1352-kak-sozdatjh-ispolnjaemihy-jar-v-intellij-idea--how-to-create-jar-in-idea
//    Меню : Build Artifacts
//    https://genuinecoder.com/online-converter/jar-to-exe/
