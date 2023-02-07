package code.guide.parse;

import code.guide.element.Guide;
import code.guide.element.PaintGuide;
import code.guide.service.GuideService;
import code.guide.service.PaintGuideService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import static code.guide.utils.MyConsts.GUIDE_SHORT_FILE;
import static java.nio.file.Files.writeString;

/**
 * класс для парсинга справочников по покраске
 */
public class PaintGuideParser {
    /**
     * записать справочник в текстовый файл
     * @param fileName - имя файла
     * @param guide - справочник
     * @param mainDetails - список имен основных деталей
     * @throws IOException
     */
    public static void writeGuideAsString(String fileName, PaintGuide guide, List<String> mainDetails) throws IOException {
        writeString(Paths.get(fileName), PaintGuideService.showPaintGuide(guide, mainDetails), StandardCharsets.UTF_8);
        System.out.println("\nСправочник в текстовом файле - " + fileName);
    }
}
