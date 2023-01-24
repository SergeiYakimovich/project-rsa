package code.guide.parse;

import code.guide.element.Guide;
import code.guide.service.GuideService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import static code.guide.utils.MyConsts.GUIDE_SHORT_FILE;
import static java.nio.file.Files.writeString;
/**
 * readGuide() - получить из файла json справочник
 * writeGuide() - записать в файл json справочник
 * writeGuideAsString - записать в файл справочник как текст
 */
public class GuideParser {

    public static Guide readGuide(String fileName) throws IOException {
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        Guide guide = mapper.readValue(myReader, Guide.class);
        return guide;
    }

    public static void writeGuide(String fileName, Guide guide) throws IOException {
        Writer myWriter = new FileWriter(fileName, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(myWriter, guide);
        System.out.println("\nСправочник в json файле - " + fileName);
    }

    public static void writeGuideAsString(String fileName, Guide guide, List<String> mainDetails) throws IOException {
        writeString(Paths.get(fileName), GuideService.showGuide(guide, mainDetails), StandardCharsets.UTF_8);
        System.out.println("\nСправочник в текстовом файле - " + fileName);
        writeString(Paths.get(GUIDE_SHORT_FILE), GuideService.showShortGuide(guide), StandardCharsets.UTF_8);
    }


}
