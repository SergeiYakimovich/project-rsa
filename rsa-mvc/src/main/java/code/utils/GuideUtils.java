package code.utils;

import code.element.Guide;
import code.model.Car;
import code.model.Detail;
import code.model.Guidesql;
import code.model.Order;
import code.model.Work;
import code.repository.CarRepository;
import code.repository.GuidesqlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GuideUtils {
    public final static String MODEL_DIR = "../model/";
    public final static String GUIDE_FILE1 = MODEL_DIR + "VW-Polo.json";
    public final static String GUIDE_FILE2 = MODEL_DIR + "VW-Polo-100.json";
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
        Car car = new Car();
        car.setName(guide.getName());
        Guidesql guidesql = new Guidesql();
        guidesql.setCar(car);
        guidesql.setText(makeJsonTextFromGuide(guide));
        return guidesql;
    }
    public static Guide makeGuideFromSql(Guidesql guidesql) throws IOException {
        Guide guide = getGuideFromJsonText(guidesql.getText());
        return guide;
    }

    public static void SQLtestGuide(ConfigurableApplicationContext context) throws IOException {
        GuidesqlRepository guidesqlRepository = context.getBean(GuidesqlRepository.class);
        CarRepository carRepository = context.getBean(CarRepository.class);

        Guide guide1 = getGuideFromJsonFile(GUIDE_FILE1);
        Guidesql guidesql1 = makeSqlFromGuide(guide1);
        guidesqlRepository.save(guidesql1);

        Guide guide2 = getGuideFromJsonFile(GUIDE_FILE2);
        Guidesql guidesql2 = makeSqlFromGuide(guide2);
        guidesqlRepository.save(guidesql2);

        List<Guidesql> list = guidesqlRepository.findAll();
        List<Car> list1 = carRepository.findAll();
        Guidesql guidesql = guidesqlRepository.findByCarName("VW-Polo-100").get();

        Guide guide3 = GuideUtils.makeGuideFromSql(guidesql);

        System.out.println(guide3.getName());
    }

}