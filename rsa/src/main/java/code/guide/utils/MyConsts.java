package code.guide.utils;

import java.util.List;

/**
 * константы, которые используются в программе (имена файлов-каталогов и пр.)
 */
public class MyConsts {
    public static boolean IS_NAME_MAIN = false;
    public final static String BASE_URL = "../data/";
    public final static String ORDERS_DIR = BASE_URL + "order/";
    public final static String PAINT_ORDERS_DIR = BASE_URL + "paintorder/";
    public final static String GUIDE_DIR = BASE_URL + "guide/";
    public final static String PAINT_GUIDE_DIR = BASE_URL + "paintguide/";
    public final static String XML_DIR = BASE_URL + "xml/";
    public final static String LIB_DIR = BASE_URL + "lib/";
    public final static String TEST_DIR = BASE_URL + "test/";
    public final static String DET_FREQUENCY = GUIDE_DIR + "det-frequency.csv";
    public final static String PAINT_DET_FREQUENCY = PAINT_GUIDE_DIR + "det-frequency.csv";
    public final static String DET_IMPORTANCE = GUIDE_DIR + "det-importance.csv";
    public final static String DET_RANGE = GUIDE_DIR + "det-range.txt";
    public final static String DET_MAIN = GUIDE_DIR + "det-main.csv";
    public final static String DET_MAIN_E = GUIDE_DIR + "det-main-E.csv";
    public final static String DET_NOT_MAIN = GUIDE_DIR + "det-not-main.csv";
    public final static String GUIDE_FILE_NAME = "KIA-RIO-moda";
//    public final static List<String> MODEL_NAMES = List.of("RIO", "SPORTAGE");
//    public final static List<String> MODEL_NAMES =
//            List.of("RIO", "SPORTAGE", "CAMRY", "LAND CRUISER", "POLO", "TOUAREG");
    public final static List<String> MODEL_NAMES = List.of("DE", "DX", "QB", "YB FB");
//    public final static List<String> MODEL_NAMES = List.of("RIO", "POLO");
    public final static String GUIDE_FILE = LIB_DIR + GUIDE_FILE_NAME + ".json";
    public final static String GUIDE_TEXT_FILE = GUIDE_DIR + GUIDE_FILE_NAME + ".txt";
    public final static String PAINT_GUIDE_TEXT_FILE = PAINT_GUIDE_DIR + GUIDE_FILE_NAME + "-paint.txt";
    public final static String GUIDE_SHORT_FILE = GUIDE_DIR + GUIDE_FILE_NAME + "-short.txt";
    public final static String GUIDE_CHECK_FILE = GUIDE_DIR + GUIDE_FILE_NAME + "-check.txt";
    public final static String GUIDE_FILE_100 = LIB_DIR + GUIDE_FILE_NAME + "-100.json";
    public final static String GUIDE_TEXT_FILE_100 = GUIDE_DIR + GUIDE_FILE_NAME + "-100.txt";

    public final static int SHOW_WRONG = 10;

//    public final static List<String> COLOR_WORK_NAMES = List.of("ОКР","ВЫКРАС", "КОЛЕР");
    public final static List<String> MAIN_DETS = List.of("БАМП","ДВЕР","ПОДКР","СТЕКЛО","РУЧК","ФОНАР",
                "БАГАЖ","РАМА", "БАК", "ДИСК","ЛОНЖЕР","ПАНЕЛ");
    public final static List<String> NOT_MAIN_DETS = List.of("МЕЛКИЕ ДЕТ","АБСОРБ","АКТИВАТОР","АПЛИКАТОР","К-Т", "УПЛ К-ЦО"
                ,"КРЮК", "НИТЬ", "РАСТВОР", "ЗАГЛУШ", "ГРУНТ");
//      ,"АДСОРБ", "КОЖУХ", "УДЛИН"

}
