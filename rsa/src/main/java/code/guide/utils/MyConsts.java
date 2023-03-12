package code.guide.utils;

import java.util.List;

/**
 * константы, которые используются в программе (имена файлов-каталогов и пр.)
 */
public class MyConsts {
    // true - если справчник по именам, false - если по управляющим номерам
    public static boolean IS_NAME_MAIN = false;
    // базовый каталог
    public final static String BASE_URL = "../data/";
    // каталог с з/н ремонта
    public final static String ORDERS_DIR = BASE_URL + "order/";
    // каталог с з/н покраски
    public final static String PAINT_ORDERS_DIR = BASE_URL + "paintorder/";
    // каталог для справочников по ремонту
    public final static String GUIDE_DIR = BASE_URL + "guide/";
    // каталог для справочников по покраске
    public final static String PAINT_GUIDE_DIR = BASE_URL + "paintguide/";
    // каталог для xml
    public final static String XML_DIR = BASE_URL + "xml/";
    // // каталог для справочников в json
    public final static String LIB_DIR = BASE_URL + "lib/";
    // каталог для тестовых запросов
    public final static String TEST_DIR = BASE_URL + "test/";
    // файл с частотой з/ч по ремонту
    public final static String DET_FREQUENCY = GUIDE_DIR + "det-frequency.csv";
    // файл с частотой з/ч по покраске
    public final static String PAINT_DET_FREQUENCY = PAINT_GUIDE_DIR + "det-frequency.csv";
    // файл с важностью з/ч
    public final static String DET_IMPORTANCE = GUIDE_DIR + "det-importance.csv";
    // файл с ранжированием з/ч
    public final static String DET_RANGE = GUIDE_DIR + "det-range.txt";
    // файл с главными деталями
    public final static String DET_MAIN = GUIDE_DIR + "det-main.csv";
    // файл с главными деталями "замена"
    public final static String DET_MAIN_E = GUIDE_DIR + "det-main-E.csv";
    // файл с лишними деталями
    public final static String DET_NOT_MAIN = GUIDE_DIR + "det-not-main.csv";

    // имя справочника
    public final static String GUIDE_FILE_NAME = "CAMRY";

    // Вывод в справочнике наборов размером больше, чем MIN_COUNT (0=все)
    public static final int MIN_COUNT = 0;

    // список марок-моделей для выделения в справочнике
    public final static List<String> MODEL_NAMES = List.of("_CR_", "_A1_", "_97_", "_90_", "_39_");
//    public final static List<String> MODEL_NAMES = List.of();
//    public final static List<String> MODEL_NAMES = List.of("DE", "DX", "QB", "YB FB");
//    public final static List<String> MODEL_NAMES = List.of("RIO", "POLO");
//    public final static List<String> MODEL_NAMES = List.of("RIO", "SPORTAGE");
//    public final static List<String> MODEL_NAMES = List.of("RIO", "SPORTAGE", "CAMRY", "LAND CRUISER", "POLO", "TOUAREG");

    // файл справочника json
    public final static String GUIDE_FILE = LIB_DIR + GUIDE_FILE_NAME + ".json";
    // файл со справочником
    public final static String GUIDE_TEXT_FILE = GUIDE_DIR + GUIDE_FILE_NAME + ".txt";
    // файл со справочником по покраске
    public final static String PAINT_GUIDE_TEXT_FILE = PAINT_GUIDE_DIR + GUIDE_FILE_NAME + "-paint.txt";
    // файл с упрощенным справочниокм
    public final static String GUIDE_SHORT_FILE = GUIDE_DIR + GUIDE_FILE_NAME + "-short.txt";
    // файл с результатами проверки
    public final static String GUIDE_CHECK_FILE = GUIDE_DIR + GUIDE_FILE_NAME + "-check.txt";
    // файл со 100%-м справочником json
    public final static String GUIDE_FILE_100 = LIB_DIR + GUIDE_FILE_NAME + "-100.json";
    // файл со 100%-м справочником
    public final static String GUIDE_TEXT_FILE_100 = GUIDE_DIR + GUIDE_FILE_NAME + "-100.txt";

    public final static int SHOW_WRONG = 10;

    public final static List<String> COLOR_WORK_NAMES = List.of("ОКР","ВЫКРАС", "КОЛЕР");
    public final static List<String> MAIN_DETS = List.of("БАМП","ДВЕР","ПОДКР","СТЕКЛО","РУЧК","ФОНАР",
                "БАГАЖ","РАМА", "БАК", "ДИСК","ЛОНЖЕР","ПАНЕЛ");
    public final static List<String> NOT_MAIN_DETS = List.of("МЕЛКИЕ ДЕТ","АБСОРБ","АКТИВАТОР","АПЛИКАТОР","К-Т", "УПЛ К-ЦО"
                ,"КРЮК", "НИТЬ", "РАСТВОР", "ЗАГЛУШ", "ГРУНТ","АДСОРБ", "КОЖУХ", "УДЛИН");

}
