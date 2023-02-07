package code.guide.utils;

import code.guide.element.Detail;
import code.guide.element.Order;
import code.guide.parse.OrderParser;
import code.guide.parse.csvtype.CsvOrder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static code.guide.utils.MyConsts.BASE_URL;
import static code.guide.utils.MyConsts.ORDERS_DIR;
import static java.nio.file.Files.readString;

/**
 * класс для прочих действий/расчетов
 */
public class Handler {

    /**
     * автоматический перенос ненужных файлов в другую директорию
     * @throws IOException
     */
    public static void moveFiles() throws IOException {
        String text = readString(Paths.get(BASE_URL + "badcsv DX-4.txt"), StandardCharsets.ISO_8859_1);
        File[] files = new File(ORDERS_DIR).listFiles();
        for(File file : files) {
            String str = file.toString().substring(14);
            if(text.contains(str)) {
                file.renameTo(new File(BASE_URL + str));
            }
        }
    }

    /**
     * расчет к-ва возможных комбинаций з/ч
     * @param n - к-во з/ч
     * @return - к-во комбинаций
     */
    public static int countVariants(int n) {
        int sum = 0;
        for(int i = 1; i <=n; i++) {
            sum += factorial(n)/factorial(i)/factorial(n - i);
        }
        return sum;
    }

    public static int factorial(int n) {
        if(n < 2) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

}
