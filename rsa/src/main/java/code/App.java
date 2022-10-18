package code;

import code.calc.Model;
import code.model.Detail;
import code.model.Order;
import code.model.Work;
import code.check.Checker;
import code.service.OrderService;
import code.utils.Handler;
import code.utils.Parser;
import code.check.Result;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.readString;
import static java.nio.file.Files.writeString;

public class App {

    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        List<Order> orders;
        Order order;
        Detail detail;
        List<Detail> details;
        Work work;
        List<Work> works;
        Result result;
        Model model;

//        Проверка модели
//        model = Parser.makeModel("model-all.csv", "model-only.csv");
//        orders = Parser.getOrdersFromDirectory("../data/");
//        List<Result> list = Checker.checkOrders(model, orders);
//        Checker.showResults(list);

//        Handler.makeModelOnly();

//        order = Parser.getOrderFromFile("../data/2 (110).csv");
//        orders = Parser.getOrdersFromDirectory("../data/");


    }

}

//    Создание exe из jar
//    https://javarush.ru/groups/posts/1352-kak-sozdatjh-ispolnjaemihy-jar-v-intellij-idea--how-to-create-jar-in-idea
//    Меню : Build Artifacts
//    https://genuinecoder.com/online-converter/jar-to-exe/
