package code.guide.service;

import code.guide.element.Guide;
import code.guide.element.Nabor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuideService {
    public static String showGuide(Guide guide, List<String> mainDetails) {
            StringBuilder builder = new StringBuilder();
            builder.append("Справочник для модели   " + guide.getName() + "\n");
            builder.append("\nНаборы з/ч :\n");
            int i = 1;
            for(Nabor nabor : guide.getDetNaborSets()) {
                builder.append("\n№" + i + "\n" + show(nabor, mainDetails));
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

    private static String show(Nabor nabor, List<String> mainDetails) {
        String main = nabor.getDetNames().stream()
                .filter(x -> mainDetails.contains(x))
                .collect(Collectors.joining(";   "));
        String simple = nabor.getDetNames().stream()
                .filter(x -> !mainDetails.contains(x))
                .collect(Collectors.joining(";   "));
        return "-> Основные детали = " + main + "\n-> Прочие детали = " + simple +
                "\n-> Н/ч для набора = " + String.format("%.2f", nabor.getCount()) + "\n";
    }
}
