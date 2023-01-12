package code.guide.service;

import code.guide.element.Guide;
import code.guide.element.Nabor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * showGuide() - перевести справочник в текстовый вид
 */
public class GuideService {
    public static String showGuide(Guide guide, List<String> mainDetails) {
            StringBuilder builder = new StringBuilder();
            builder.append("Справочник для модели   " + guide.getName() + "\n");
            builder.append("\nНаборы з/ч :\n");
            int i = 1;
            for(Nabor nabor : guide.getDetNaborSets()) {
                builder.append("\n№" + i + "\n" + show2(nabor, mainDetails));
                i++;
            }
            builder.append("\nОдиночные з/ч :\n");
            i = 1;
            for(Map.Entry<String, Double> nabor : guide.getDetSingles().entrySet()) {
                builder.append("\n№" + i + "\nДеталь = " + nabor.getKey() + "\nН/ч = "
                        + String.format("%.2f", nabor.getValue()) + "\n");
                i++;
            }
            return builder.toString();
        }

    private static String show1(Nabor nabor, List<String> mainDetails) {
        String main = nabor.getDetNames().stream()
                .filter(x -> mainDetails.contains(x))
                .collect(Collectors.joining(";   "));
        String simple = nabor.getDetNames().stream()
                .filter(x -> !mainDetails.contains(x))
                .collect(Collectors.joining(";   "));
        return "-> Основные детали = " + main + "\n-> Прочие детали = " + simple +
                "\n-> Н/ч для набора = " + String.format("%.2f", nabor.getCount()) + "\n";
    }

    private static String show2(Nabor nabor, List<String> mainDetails) {
        List<String> mainList = nabor.getDetNames().stream()
                .filter(x -> mainDetails.contains(x))
                .collect(Collectors.toList());
        List<String> simpleList = nabor.getDetNames().stream()
                .filter(x -> !mainDetails.contains(x))
                .collect(Collectors.toList());
        String main = showList(mainList);
        String simple = showList(simpleList);
        String resultText = "-> Основные детали =\n" + main + "\n-> Прочие детали =\n" + simple +
                "\n-> Н/ч для набора = " + String.format("%.2f", nabor.getCount());
        if (nabor.getMax() - nabor.getMin() > 0.01) {
            resultText += "\n-> min = " + String.format("%.2f", nabor.getMin()) +
                    "\n-> max = " + String.format("%.2f", nabor.getMax());
        }
        return resultText + "\n";

    }

    private static String showList(List<String> detList) {
        if(detList.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < detList.size() - 1; i++) {
            builder.append(detList.get(i));
            builder.append(";");
            if((i+1) % 3 == 0) {
                builder.append("\n");
            } else {
                builder.append(" ".repeat(21 - detList.get(i).length()));
            }
        }
        builder.append(detList.get(detList.size() - 1));
        return builder.toString();
    }
}
