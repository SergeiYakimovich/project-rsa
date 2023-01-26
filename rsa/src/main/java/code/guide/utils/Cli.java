package code.guide.utils;

import code.guide.check.Checker;
import code.guide.check.Result;
import code.guide.element.Guide;
import code.guide.element.Order;
import code.guide.parse.DetailsParser;
import code.guide.parse.GuideParser;
import code.guide.parse.OrderParser;
import code.guide.parse.XmlParser;
import code.guide.parse.csvtype.CsvOrder;
import code.guide.parse.csvtype.CsvText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static code.guide.utils.Handler.checkNameNumber;
import static code.guide.utils.MyConsts.BASE_URL;
import static code.guide.utils.MyConsts.GUIDE_FILE;
import static code.guide.utils.MyConsts.GUIDE_FILE_100;
import static code.guide.utils.MyConsts.GUIDE_TEXT_FILE;
import static code.guide.utils.MyConsts.GUIDE_TEXT_FILE_100;
import static code.guide.utils.MyConsts.ORDERS_DIR;
import static code.guide.utils.MyConsts.SHOW_WRONG;
import static code.guide.utils.MyConsts.TEST_DIR;
import static code.guide.utils.MyConsts.XML_DIR;

/**
 * getChoice() - выдать варианты и получить выбор пользователя
 * fulfill() - выполнить выбранные действия
 */
public class Cli {

    public static void fulfill(int n) throws Exception {
        List<Order> orders;
        List<Result> results;
        Guide guide;
        List<String> mainDetails;
        List<String> notMainDetails;
        switch (n) {
            case 1 : //        Конвертация Xml в Csv
                XmlParser.convertOrdersFromDirectory(XML_DIR);
                break;
            case 2 : //        Проанализировать детали (по частоте и важности)
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                DetailUtils.analizeDetails(orders);
                break;
            case 3 : //        Создать список лишних детали (из всех и важных)
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                DetailUtils.makeNotMainFromMainAndAll(orders);
                break;
            case 4 : //        Создание полной модели (без учета важных и не нужных деталей)
                mainDetails = new ArrayList<>();
                notMainDetails = new ArrayList<>();
                makeAndCheck(mainDetails, notMainDetails, GUIDE_FILE_100, GUIDE_TEXT_FILE_100);
                break;
            case 5 : //        Создание своей модели (с учетом важных и не нужных деталей)
                mainDetails = getMainDet();
                notMainDetails = getNotMainDet();
                makeAndCheck(mainDetails, notMainDetails, GUIDE_FILE, GUIDE_TEXT_FILE);
                break;
            case 6 : //        Проверка на тестовых запросах (тип - текст с разделителями [,;\n])
                guide = GuideParser.readGuide(GUIDE_FILE_100);
//                List<Order> orders1 = OrderParser.getOrdersFromDirectory(ORDERS_DIR, new CsvOrder());
//                orders = OrderParser.getOrdersFromDirectory(TEST_DIR, new CsvRequest());
                orders = OrderParser.getOrdersFromDirectory(TEST_DIR, new CsvText());
                Checker.countTestOrders(guide,orders);
                break;
            case 7 : //     Проверка соответствия имен и управляющих номеров
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                checkNameNumber(orders,true);
                checkNameNumber(orders,false);
                break;
        }
    }

    public static int getChoice() {
        System.out.println("\n1 - Конвертация Xml в Csv");
        System.out.println("2 - Проанализировать детали (по частоте и важности)");
        System.out.println("3 - Создать список лишних детали (из всех и важных)");
        System.out.println("4 - Создать полный справочник (без учета важных и лишних деталей) и проверить его");
        System.out.println("5 - Создать свой справочник (с учетом важных и лишних деталей) и проверить его");
        System.out.println("6 - Проверка справочника на тестовых запросах");
        System.out.println("7 - Проверка соответствия имен и управляющих номеров");
        System.out.println("0 - Выход");
        System.out.print("\nВведите число - ");

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println();
        return n;
    }

    private static void makeAndCheck(List<String> mainDetails, List<String> notMainDetails, String guideFile,
                                     String guideTextFile) throws Exception {
        List<Order> orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
        Guide guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails, guideFile);
        GuideParser.writeGuide(guideFile, guide);
        GuideParser.writeGuideAsString(guideTextFile, guide, mainDetails);
        List<Result> results = Checker.checkOrders(guide, orders);
        Checker.showResults(results, SHOW_WRONG);
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
