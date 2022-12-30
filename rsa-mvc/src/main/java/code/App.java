package code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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



