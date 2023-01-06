package code.guide.utils;

import java.util.List;

public class MyConsts {
    public final static String BASE_URL = "../data/";
    public final static String ORDERS_DIR = BASE_URL + "order/";
    public final static String TEST_DIR = BASE_URL + "test/";
    public final static String GUIDE_DIR = BASE_URL + "guide/";
    public final static String XML_DIR = BASE_URL + "xml/";
    public final static String LIB_DIR = BASE_URL + "lib/";
    public final static String DET_FREQUENCY = GUIDE_DIR + "det-frequency.csv";
    public final static String DET_IMPORTANCE = GUIDE_DIR + "det-importance.csv";
    public final static String DET_MAIN = GUIDE_DIR + "det-main.csv";
    public final static String DET_NOT_MAIN = GUIDE_DIR + "det-not-main.csv";
    public final static String GUIDE_FILE_NAME = "Kia-Rio-DX4";
    public final static String GUIDE_FILE = GUIDE_DIR + GUIDE_FILE_NAME + ".json";
    public final static String GUIDE_TEXT_FILE = GUIDE_DIR + GUIDE_FILE_NAME + ".txt";
    public final static String GUIDE_FILE_100 = GUIDE_DIR + GUIDE_FILE_NAME + "-100.json";
    public final static String GUIDE_TEXT_FILE_100 = GUIDE_DIR + GUIDE_FILE_NAME + "-100.txt";

    public final static List<String> COLOR_WORK_NAMES = List.of("ОКР","ВЫКРАС", "КОЛЕР");
    public final static List<String> MAIN_DETS = List.of("БАМП","ДВЕР","ПОДКР","СТЕКЛО","РУЧК","ФОНАР",
                "БАГАЖ","РАМА", "БАК", "ДИСК","ЛОНЖЕР","ПАНЕЛ");
    public final static List<String> NOT_MAIN_DETS = List.of("МЕЛКИЕ ДЕТ","АБСОРБ","АКТИВАТОР","АПЛИКАТОР","К-Т", "УПЛ К-ЦО"
                ,"КРЮК", "НИТЬ", "РАСТВОР", "ЗАГЛУШ", "ГРУНТ");
//      ,"АДСОРБ", "КОЖУХ", "УДЛИН"

}
