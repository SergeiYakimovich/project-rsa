package code.mvc.mvcutils;

import code.guide.element.Guide;
import code.mvc.model.Guidesql;
import code.mvc.repository.GuidesqlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static code.guide.utils.MyConsts.GUIDE_FILE;

/**
 * класс для работы со справочником-сущностью
 */
public class GuidesqlUtils {

    /**
     * получение справочника из json-файла
     * @param fileName - имя справочника
     * @return - справочник
     * @throws IOException
     */
    public static Guide getGuideFromJsonFile(String fileName) throws IOException {
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        Guide guide = mapper.readValue(myReader, Guide.class);
        return guide;
    }

    /**
     * получение справочника из json-строки
     * @param text - json-строка
     * @return - справочник
     * @throws IOException
     */
    public static Guide getGuideFromJsonText(String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Guide guide = mapper.readValue(text, Guide.class);
        return guide;
    }

    /**
     * конвертация справочника в Json-строку
     * @param guide - справочник
     * @return - Json-строка
     * @throws IOException
     */
    public static String makeJsonTextFromGuide(Guide guide) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(guide);
    }

    /**
     * конвертация справочника в сущность для SQL БД
     * @param guide - справочник
     * @return - сущность для SQL БД
     * @throws IOException
     */
    public static Guidesql makeSqlFromGuide(Guide guide) throws IOException {
        Guidesql guidesql = new Guidesql();
        guidesql.setName(guide.getName());
        guidesql.setText(makeJsonTextFromGuide(guide));
        return guidesql;
    }

    /**
     * конвертация справочника из сущности для SQL БД
     * @param guidesql - сущность для SQL БД
     * @return - справочник
     * @throws IOException
     */
    public static Guide makeGuideFromSql(Guidesql guidesql) throws IOException {
        Guide guide = getGuideFromJsonText(guidesql.getText());
        return guide;
    }

    /**
     * ручная работа со справочником
     * @param context - контекст SpringBoot
     * @throws IOException
     */
    public static void handleSQLGuide(ConfigurableApplicationContext context) throws IOException {
        GuidesqlRepository guidesqlRepository = context.getBean(GuidesqlRepository.class);

        Guide guide = getGuideFromJsonFile(GUIDE_FILE);
        Guidesql guidesql = makeSqlFromGuide(guide);
        guidesqlRepository.save(guidesql);

        List<Guidesql> listGuides = guidesqlRepository.findAll();
        guidesql = guidesqlRepository.findByName("VW-Polo").get();

        guide = GuidesqlUtils.makeGuideFromSql(guidesql);

//        System.out.println(guide.getName());
    }

}