package code.guide.utils;

import code.guide.check.Checker;
import code.guide.check.Result;
import code.guide.element.Guide;
import code.guide.element.Order;
import code.guide.element.PaintGuide;
import code.guide.parse.DetailsParser;
import code.guide.parse.GuideParser;
import code.guide.parse.OrderParser;
import code.guide.parse.PaintGuideParser;
import code.guide.parse.PaintParser;
import code.guide.parse.XmlParser;
import code.guide.parse.csvtype.CsvDetail;
import code.guide.parse.csvtype.CsvOrder;
import code.guide.parse.csvtype.CsvText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static code.guide.utils.MyConsts.*;

/**
 * класс для коммуникации с пользователем (получение выбора пользователя и запуск программ по его выбору)
 */
public class Cli {

    /**
     * выполнить выбранные пользователем действия
     * @param n - выбор пользователя
     * @throws Exception
     */
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
                mainDetails = DetailsParser.getMainDets();
                notMainDetails = DetailsParser.getNotMainDet();
                makeAndCheck(mainDetails, notMainDetails, GUIDE_FILE, GUIDE_TEXT_FILE);
                break;
            case 6 : //        Проверка на тестовых запросах (тип - текст с разделителями [,;\n])
                guide = GuideParser.readGuide(GUIDE_FILE);
//                orders = OrderParser.getOrdersFromDirectory(TEST_DIR, new CsvText());
                orders = OrderParser.getOrdersFromDirectory(TEST_DIR, new CsvOrder());
                Checker.countTestOrders(guide,orders);
                break;
            case 7 : //     Проверка соответствия имен и управляющих номеров
                orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
                NameUprNumberUtils.checkNameNumber(orders,true);
                NameUprNumberUtils.checkNameNumber(orders,false);
                break;
            case 8 : // Покраска конвертация Xml в Csv
                PaintParser.convertOrdersFromDirectory(XML_DIR);
                break;
            case 9 : // Покраска создание полной модели (без учета важных и не нужных деталей)
                mainDetails = new ArrayList<>();
                notMainDetails = new ArrayList<>();
                MyConsts.IS_NAME_MAIN = true;
                orders = OrderParser.getOrdersFromDirectory(MyConsts.PAINT_ORDERS_DIR, new CsvOrder());
                DetailUtils.makeUniqDetails(MyConsts.PAINT_DET_FREQUENCY, orders);
                PaintGuide paintGuide = PaintGuideUtils.makePaintGuide(orders, mainDetails, notMainDetails, PAINT_GUIDE_TEXT_FILE);
                PaintGuideParser.writeGuideAsString(PAINT_GUIDE_TEXT_FILE, paintGuide, mainDetails);
                break;
        }
    }

    /**
     * выдать варианты действий и получить выбор пользователя
     * @return - выбор пользователя
     */
    public static int getChoice() {
        System.out.println("\n1 - Конвертация Xml в Csv");
        System.out.println("2 - Проанализировать детали (по частоте и важности)");
        System.out.println("3 - Создать список лишних детали (из всех и важных)");
        System.out.println("4 - Создать полный справочник (без учета важных и лишних деталей) и проверить его");
        System.out.println("5 - Создать свой справочник (с учетом важных и лишних деталей) и проверить его");
        System.out.println("6 - Проверка справочника на тестовых запросах");
        System.out.println("7 - Проверка соответствия имен и управляющих номеров");
        System.out.println("8 - Покраска - конвертация Xml в Csv");
        System.out.println("9 - Покраска - создание справочника");
        System.out.println("0 - Выход");
        System.out.print("\nВведите число - ");

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println();
        return n;
    }

    /**
     * создать справочник и проверить его на всех данных
     * @param mainDetails - список важных деталей
     * @param notMainDetails  - список не важных деталей
     * @param guideFile - имя json файла для справочника
     * @param guideTextFile - имя текстового файла для справочника
     * @throws Exception
     */
    private static void makeAndCheck(List<String> mainDetails, List<String> notMainDetails, String guideFile,
                                     String guideTextFile) throws Exception {
        List<Order> orders = OrderParser.getOrdersFromDirectory(MyConsts.ORDERS_DIR, new CsvOrder());
        Guide guide = GuideUtils.makeGuide(orders, mainDetails, notMainDetails, guideFile);
        NameUprNumberUtils.makeMapNameNumber();
//        GuideParser.writeGuide(guideFile, guide);
        GuideParser.writeGuideAsText(guideTextFile, guide, mainDetails);
        List<Result> results = Checker.checkOrders(guide, orders);
        Checker.showResults(results);
    }


}
