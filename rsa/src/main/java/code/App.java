package code;

import code.guide.element.Order;
import code.guide.parse.OrderParser;
import code.guide.parse.XmlParser;
import code.guide.parse.csvtype.CsvOrder;
import code.guide.utils.Cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static code.guide.utils.MyConsts.BASE_URL;
import static code.guide.utils.MyConsts.ORDERS_DIR;
import static code.guide.utils.MyConsts.XML_DIR;
import static java.nio.file.Files.readString;
import static java.nio.file.Files.writeString;

public class App {
    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));

        int n = Cli.getChoice();
        Cli.fulfill(n);


    }

}

//    Создание exe из jar
//    https://javarush.ru/groups/posts/1352-kak-sozdatjh-ispolnjaemihy-jar-v-intellij-idea--how-to-create-jar-in-idea
//    Меню : Build Artifacts -> out/artifacts
//    https://genuinecoder.com/online-converter/jar-to-exe/
