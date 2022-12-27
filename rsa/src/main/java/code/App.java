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
import code.parse.csvtype.CsvRequest;
import code.parse.csvtype.CsvText;
import code.utils.DetailUtils;
import code.utils.GuideUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.Files.readString;
import static java.nio.file.Files.size;
import static java.nio.file.Files.writeString;

public class App {
    public final static String ORDERS_ALL_DIR = "../data/all/";
    public final static String TEST_ORDER_DIR = "../data/test_order/";
    public final static String TEST_DETAIL_DIR = "../data/test_detail/";
    public final static String TEST_TEXT_DIR = "../data/test_text/";
    public final static String MODEL_DIR = "../model/";
    public final static String DET_FREQUENCY = MODEL_DIR + "det-frequency.csv";
    public final static String DET_IMPORTANCE = MODEL_DIR + "det-importance.csv";
    public final static String DET_MAIN = MODEL_DIR + "det-main.csv";
    public final static String DET_NOT_MAIN = MODEL_DIR + "det-not-main.csv";
    public final static String GUIDE_FILE = MODEL_DIR + "VW-Polo.json";
    public final static String GUIDE_TEXT_FILE = MODEL_DIR + "VW-Polo.txt";
    public final static List<String> COLOR_WORK_NAMES = List.of("ОКР","ВЫКРАС", "КОЛЕР");

    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        List<Order> orders;
        List<Result> results;
        Guide guide;
        List<String> mainDetails;
        List<String> notMainDetails;

        int n = getChoice();
        switch (n) {
            case 1 : //        Проанализировать детали (по частоте и важности)
                orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
                DetailUtils.analizeDetails(orders);
                break;
            case 2 : //        Создание модели (с учетом важных и не нужных деталей)
                orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
                mainDetails = getMainDet();
                notMainDetails = getNotMainDet();
                guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails);
                GuideParser.writeGuide(GUIDE_FILE, guide);
                GuideParser.writeGuideAsString(GUIDE_TEXT_FILE, guide);
                break;
            case 3 : //        Проверка модели на всех данных
                guide = GuideParser.readGuide(GUIDE_FILE);
                orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
                results = Checker.checkOrders(guide, orders);
                Checker.showResults(results, 10);
                break;
            case 4 : //        Создание и проверка модели на всех данных
                orders = OrderParser.getOrdersFromDirectory(ORDERS_ALL_DIR, new CsvOrder());
                mainDetails = getMainDet();
                notMainDetails = getNotMainDet();
                guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails);
                GuideParser.writeGuide(GUIDE_FILE, guide);
                GuideParser.writeGuideAsString(GUIDE_TEXT_FILE, guide);

                results = Checker.checkOrders(guide, orders);
                Checker.showResults(results, 10);
                break;
            case 5 : //        Проверка на тестовых запросах (тип - csv из з/н)
                guide = GuideParser.readGuide(GUIDE_FILE);
                orders = OrderParser.getOrdersFromDirectory(TEST_ORDER_DIR, new CsvOrder());
                Checker.checkTestOrders(guide,orders);
                break;
            case 6 : //        Проверка на тестовых запросах (тип - csv из имен з/ч)
                guide = GuideParser.readGuide(GUIDE_FILE);
                orders = OrderParser.getOrdersFromDirectory(TEST_DETAIL_DIR, new CsvRequest());
                Checker.checkTestOrders(guide,orders);
                break;
            case 7 : //        Проверка на тестовых запросах (тип - текстовый файл с разделителями)
                guide = GuideParser.readGuide(GUIDE_FILE);
                orders = OrderParser.getOrdersFromDirectory(TEST_TEXT_DIR, new CsvText());
                Checker.checkTestOrders(guide,orders);
                break;
        }
    }

    public static int getChoice(){
        System.out.println("\n1 - Проанализировать детали (по частоте и важности)");
        System.out.println("2 - Создать справочник (с учетом важных и не нужных деталей)");
        System.out.println("3 - Проверка справочника на всех данных");
        System.out.println("4 - п.2 и п.3 одновременно");
        System.out.println("5 - Проверка справочника на запросах (тип - csv из з/н)");
        System.out.println("6 - Проверка справочника на запросах (тип - csv из имен з/ч)");
        System.out.println("7 - Проверка справочника на запросах (тип - текстовый файл с разделителями)");
        System.out.println("0 - Выход");
        System.out.print("\nВведите число - ");

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println();
        return n;
    }

    public static List<String> getMainDet() {
        List<String> mainDetails = new ArrayList<>();
//        mainDetails = List.of("БАМП","ДВЕР","ПОДКР","СТЕКЛО","РУЧК","ФОНАР",
//                "БАГАЖ","РАМА", "БАК", "ДИСК","ЛОНЖЕР","ПАНЕЛ");
        try {
            mainDetails = DetailsParser.readDetNames(DET_MAIN);
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла " + DET_MAIN);
            return new ArrayList<>();
        }
        return mainDetails;
    }

    public static List<String> getNotMainDet() {
        List<String> notMainDetails = new ArrayList<>();
//        notMainDetails = List.of("МЕЛКИЕ ДЕТ","АБСОРБ","АКТИВАТОР","АПЛИКАТОР","К-Т", "УПЛ К-ЦО"
//                ,"КРЮК", "НИТЬ", "РАСТВОР", "ЗАГЛУШ", "ГРУНТ");
//      ,"АДСОРБ", "КОЖУХ", "УДЛИН"
        try {
            notMainDetails = DetailsParser.readDetNames(DET_NOT_MAIN);
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла " + DET_NOT_MAIN);
            return new ArrayList<>();
        }
        return notMainDetails;
    }

}

//    Создание exe из jar
//    https://javarush.ru/groups/posts/1352-kak-sozdatjh-ispolnjaemihy-jar-v-intellij-idea--how-to-create-jar-in-idea
//    Меню : Build Artifacts -> out/artifacts
//    https://genuinecoder.com/online-converter/jar-to-exe/
