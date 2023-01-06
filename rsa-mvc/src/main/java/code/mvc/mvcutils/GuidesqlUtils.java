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


public class GuidesqlUtils {
    public static Guide getGuideFromJsonFile(String fileName) throws IOException {
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        Guide guide = mapper.readValue(myReader, Guide.class);
        return guide;
    }

    public static Guide getGuideFromJsonText(String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Guide guide = mapper.readValue(text, Guide.class);
        return guide;
    }

    public static String makeJsonTextFromGuide(Guide guide) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(guide);
    }

    public static Guidesql makeSqlFromGuide(Guide guide) throws IOException {
        Guidesql guidesql = new Guidesql();
        guidesql.setName(guide.getName());
        guidesql.setText(makeJsonTextFromGuide(guide));
        return guidesql;
    }
    public static Guide makeGuideFromSql(Guidesql guidesql) throws IOException {
        Guide guide = getGuideFromJsonText(guidesql.getText());
        return guide;
    }

    public static void SQLtestGuide(ConfigurableApplicationContext context) throws IOException {
//        GuidesqlRepository guidesqlRepository = context.getBean(GuidesqlRepository.class);
//        CarRepository carRepository = context.getBean(CarRepository.class);
//
//        Guide guide1 = getGuideFromJsonFile(GUIDE_FILE);
//        Guidesql guidesql1 = makeSqlFromGuide(guide1);
//        guidesqlRepository.save(guidesql1);
//
//        List<Guidesql> listGuides = guidesqlRepository.findAll();
//        List<Car> listCars = carRepository.findAll();
//        Guidesql guidesql = guidesqlRepository.findByName("VW-Polo").get();
//
//        Guide guide3 = GuidesqlUtils.makeGuideFromSql(guidesql);
//
//        System.out.println(guide3.getName());
    }

}