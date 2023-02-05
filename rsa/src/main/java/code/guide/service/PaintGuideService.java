package code.guide.service;

import code.guide.element.Guide;
import code.guide.element.Nabor;
import code.guide.element.PaintGuide;
import code.guide.element.PaintNabor;
import code.guide.utils.MyConsts;
import code.guide.utils.NameUprNumberUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaintGuideService {

    public static String showPaintGuide(PaintGuide guide, List<String> mainDetails) {
        StringBuilder builder = new StringBuilder();
        builder.append("Справочник для модели   " + guide.getName() + "\n");
        builder.append("\n" + "_".repeat(70));

        builder.append("\n\nНаборы з/ч :\n");
        builder.append("_".repeat(70) + "\n");
        int i = 1;
        for(PaintNabor nabor : guide.getDetNaborSets()) {
            builder.append("\n№" + i + " (" + nabor.getDetNames().size() + "шт.)\n"
                    + showPaintNabor(nabor, mainDetails));
            i++;
        }
        return builder.toString();
    }

    private static String showPaintNabor(PaintNabor nabor, List<String> mainDetails) {
        List<String> mainList = nabor.getDetNames().stream()
                .filter(x -> mainDetails.contains(x))
                .sorted()
                .collect(Collectors.toList());
        List<String> simpleList = nabor.getDetNames().stream()
                .filter(x -> !mainDetails.contains(x))
                .sorted()
                .collect(Collectors.toList());
        String main = showList(mainList);
        String simple = showList(simpleList);
        String resultText = "-> Основные детали =\n" + main +
                "\n-> Прочие детали =\n" + simple +
                "\n-> Подготовительные работы = " + String.format("%.2f", nabor.getPrepareHours()) +
                "\n-> Все варианты = " + nabor.getVariantsPrepareHours() +
                "\n-> Окрасочные работы = " + String.format("%.2f", nabor.getColorHours()) +
                "\n-> Все варианты = " + nabor.getVariantsColorHours() +
                "\n-> Стоимость покраски = " + String.format("%.2f", nabor.getColorCost()) +
                "\n-> Все варианты = " + nabor.getVariantsColorCost();
        return resultText + "\n";
    }

    private static String showList(List<String> detList) {
        if(detList.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < detList.size() - 1; i++) {
            String modifiedName = detList.get(i);
            builder.append(modifiedName);
            builder.append(";");
            if((i+1) % 3 == 0) {
                builder.append("\n");
            } else {
                if (modifiedName.length() < 25) {
                    builder.append(" ".repeat(25 - modifiedName.length()));
                }
            }
        }
        builder.append(detList.get(detList.size() - 1));
        return builder.toString();
    }

}
