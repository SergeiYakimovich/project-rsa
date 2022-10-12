package code;

import code.model.Detail;
import code.model.Order;
import code.model.Work;
import code.repository.DetailRepository;
import code.repository.OrderRepository;
import code.repository.WorkRepository;
import code.utils.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableAutoConfiguration
public class App {

    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));

        ConfigurableApplicationContext context = SpringApplication.run(App.class);
        WorkRepository workRepository = context.getBean(WorkRepository.class);
        DetailRepository detailRepository = context.getBean(DetailRepository.class);
        OrderRepository orderRepository = context.getBean(OrderRepository.class);

        List<Order> orders;
        Order order;
        Detail detail;
        List<Detail> details;
        Work work;
        List<Work> works;
        

//        order = Parser.getOrderFromFile("../data/2 (110).csv");
        orders = Parser.getOrdersFromDirectory("../data/");

        orderRepository.save(orders.get(0));
        orderRepository.save(orders.get(1));
        orderRepository.save(orders.get(2));

        orders = orderRepository.findAll();
        details = detailRepository.findAllByOrderName("data\\2 (100).csv");
        works = workRepository.findAllByOrderName("data\\2 (100).csv");

        order = orders.get(0);
        detail = details.get(0);
        work = works.get(0);

        System.out.println(order.toString());
        System.out.println(detail.toString());
        System.out.println(work.toString());

        System.out.println(orders.size());
        System.out.println(details.size());
        System.out.println(works.size());

        context.close();
    }

}


