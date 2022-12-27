package code;

import code.element.Guide;
import code.model.Car;
import code.model.Detail;
import code.model.Guidesql;
import code.model.Order;
import code.model.Work;
import code.repository.CarRepository;
import code.repository.DetailRepository;
import code.repository.GuidesqlRepository;
import code.repository.OrderRepository;
import code.repository.WorkRepository;
import code.utils.GuideUtils;
import code.utils.OrderUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

//@Configuration
//@EnableAutoConfiguration
//public class App {
//
//    public static void main(String[] args) throws Exception {
//        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
//
//        ConfigurableApplicationContext context = SpringApplication.run(App.class);
//
//        context.close();
//    }
//
//}


