package code.guide.utils;

import code.guide.check.Checker;
import code.guide.check.Result;
import code.guide.element.Guide;
import code.guide.element.Order;
import code.guide.parse.DetailsParser;
import code.guide.parse.GuideParser;
import code.guide.parse.OrderParser;
import code.guide.parse.csvtype.CsvOrder;
import code.guide.parse.csvtype.CsvText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cli {

    public static void fulfill(int n) throws Exception {
        List<Order> orders;
        List<Result> results;
        Guide guide;
        List<String> mainDetails;
        List<String> notMainDetails;
        switch (n) {
            case 1 : //        Проанализировать детали (по частоте и важности)
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                DetailUtils.analizeDetails(orders);
                break;
            case 2 : //        Создание полной модели (без учета важных и не нужных деталей)
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                mainDetails = new ArrayList<>();
                notMainDetails = new ArrayList<>();
                guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails);
                GuideParser.writeGuide(MyConsts.GUIDE_FILE, guide);
                GuideParser.writeGuideAsString(MyConsts.GUIDE_TEXT_FILE, guide, mainDetails);
                break;
            case 3 : //        Создание своей модели (с учетом важных и не нужных деталей)
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                mainDetails = getMainDet();
                notMainDetails = getNotMainDet();
                guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails);
                GuideParser.writeGuide(MyConsts.GUIDE_FILE, guide);
                GuideParser.writeGuideAsString(MyConsts.GUIDE_TEXT_FILE, guide, mainDetails);
                break;
            case 4 : //        Проверка модели на всех данных
                guide = GuideParser.readGuide(MyConsts.GUIDE_FILE);
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                results = Checker.checkOrders(guide, orders);
                Checker.showResults(results, 10);
                break;
            case 5 : //        Создание и проверка модели на всех данных
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                mainDetails = getMainDet();
                notMainDetails = getNotMainDet();
                guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails);
                GuideParser.writeGuide(MyConsts.GUIDE_FILE, guide);
                GuideParser.writeGuideAsString(MyConsts.GUIDE_TEXT_FILE, guide, mainDetails);

                results = Checker.checkOrders(guide, orders);
                Checker.showResults(results, 10);
                break;
            case 6 : //        Проверка на тестовых запросах (тип - csv из з/н)
                guide = GuideParser.readGuide(MyConsts.GUIDE_FILE);
//                orders = OrderParser.getOrdersFromDirectory(TEST_DIR, new CsvOrder());
//                orders = OrderParser.getOrdersFromDirectory(TEST_DIR, new CsvRequest());
                orders = OrderParser.getOrdersFromDirectory(MyConsts.TEST_DIR, new CsvText());
                Checker.checkTestOrders(guide,orders);
                break;
        }
    }
    public static int getChoice() {
        System.out.println("\n1 - Проанализировать детали (по частоте и важности)");
        System.out.println("2 - Создать справочник полный (без учета важных и не нужных деталей)");
        System.out.println("3 - Создать свой справочник (с учетом важных и не нужных деталей)");
        System.out.println("4 - Проверка справочника на всех данных");
        System.out.println("5 - п.3 и п.4 одновременно");
        System.out.println("6 - Проверка справочника на тестовых запросах");
        System.out.println("0 - Выход");
        System.out.print("\nВведите число - ");

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println();
        return n;
    }

    public static List<String> getMainDet() {
        List<String> mainDetails;
        try {
            mainDetails = DetailsParser.readDetNames(MyConsts.DET_MAIN);
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла " + MyConsts.DET_MAIN);
            return new ArrayList<>();
        }
        return mainDetails;
    }

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
