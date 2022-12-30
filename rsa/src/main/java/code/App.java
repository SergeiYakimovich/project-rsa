package code;

import code.guide.utils.Cli;

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
