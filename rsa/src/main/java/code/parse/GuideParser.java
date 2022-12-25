package code.parse;

import code.element.Guide;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static code.App.DET_MAIN;
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

    public static void writeGuideAsString(String fileName, Guide guide) throws IOException {
        writeString(Paths.get(fileName), guide.toString(), StandardCharsets.UTF_8);
        System.out.println("\nСправочник в текстовом файле - " + fileName);
    }


}
