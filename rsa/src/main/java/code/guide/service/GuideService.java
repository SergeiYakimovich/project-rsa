package code.guide.service;

import code.guide.element.Guide;
import code.guide.element.Nabor;
import code.guide.utils.MyConsts;
import code.guide.utils.NameUprNumberUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static code.guide.calc.Calculator.countHoursForSingles;

/**
 * showGuide() - перевести справочник в текстовый вид
 */
public class GuideService {
    public static String showGuide(Guide guide, List<String> mainDetails) {
            StringBuilder builder = new StringBuilder();
            builder.append("Справочник для модели   " + guide.getName() + "\n");
            builder.append("\n" + "_".repeat(70));

            builder.append("\n\nНаборы з/ч :\n");
            builder.append("_".repeat(70) + "\n");
            int i = 1;
            for(Nabor nabor : guide.getDetNaborSets()) {
                builder.append("\n№" + i + " (" + nabor.getDetNames().size() + "шт.)\n" + showNabor(nabor, mainDetails));
                i++;
            }

            builder.append("\n\n\n" + "_".repeat(70));
            builder.append("\n\nОдиночные з/ч :\n");
            builder.append("_".repeat(70) + "\n");
            i = 1;
            for(Map.Entry<String, Map<String,Double>> nabor : guide.getDetSingles().entrySet()) {
                String name = nabor.getKey();
                if(mainDetails.contains(name)) {
                    Double hours = countHoursForSingles(nabor.getValue().values());
                    builder.append("\n№" + i + "\n-> Деталь = " + modifyName(name) + "\n-> Н/ч = "
                            + String.format("%.2f", hours) + "\n");
                    builder.append("-> Все варианты = " + nabor.getValue() + "\n");
                    i++;
                }
            }

            return builder.toString();
        }

    private static String showNabor(Nabor nabor, List<String> mainDetails) {
        List<String> mainList = nabor.getDetNames().stream()
                .filter(x -> mainDetails.contains(x))
                .sorted((x1,x2) -> MyConsts.IS_NAME_MAIN ? x1.compareTo(x2) :
                        NameUprNumberUtils.mapNameNumber.get(x1).compareTo(NameUprNumberUtils.mapNameNumber.get(x2)))
                .collect(Collectors.toList());
        List<String> simpleList = nabor.getDetNames().stream()
                .filter(x -> !mainDetails.contains(x))
                .collect(Collectors.toList());
        String main = showList(mainList);
        String simple = showList(simpleList);
        String resultText = "-> Основные детали =\n" + main + "\n-> Прочие детали =\n" + simple +
                "\n-> Н/ч для набора = " + String.format("%.2f", nabor.getCount());
        resultText += "\n-> Все варианты = " + nabor.getAllVariants();
        return resultText + "\n";
    }

    private static String showList(List<String> detList) {
        if(detList.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < detList.size() - 1; i++) {
            String modifiedName = modifyName(detList.get(i));
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
        builder.append(modifyName(detList.get(detList.size() - 1)));
        return builder.toString();
    }

    public static String showShortGuide(Guide guide) {
        StringBuilder builder = new StringBuilder();
        builder.append("номер набора (№)\tтрудоемкость\n");
        int i = 1;
        for(Nabor nabor : guide.getDetNaborSets()) {
            for(Double variant : nabor.getAllVariants().values().stream()
                    .sorted().collect(Collectors.toList())) {
                builder.append("\n№" + i + "\t\t\t"  + variant + "\n");
            }
            builder.append("_".repeat(50) + "\n");
            i++;
        }

        return builder.toString();
    }

    public static String modifyName(String name) {
        if(NameUprNumberUtils.mapNameNumber.size() == 0) {
            return name;
        } else {
            String str = NameUprNumberUtils.mapNameNumber.get(name);
            str = str == null ? "" : "-" + str;
            return name + str;
        }
    }
}
