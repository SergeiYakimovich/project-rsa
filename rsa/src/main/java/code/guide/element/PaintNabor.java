package code.guide.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static code.guide.calc.Calculator.countHoursForNabor;

@Getter
@Setter
@NoArgsConstructor
public class PaintNabor extends SimpleNabor {
    double prepareHours;
    Map<String, Double> variantsPrepareHours = new HashMap<>(); // варианты для расчета в случае коллизий

    double colorHours;
    Map<String, Double> variantsColorHours = new HashMap<>();

    double colorCost;
    Map<String, Double> variantsColorCost = new HashMap<>();

    public PaintNabor(Collection<String> detNames, Map<String, Double> variantsPrepareHours, Map<String, Double> variantsColorHours, Map<String, Double> variantsColorCost) {
        this.detNames = new ArrayList<>();
        this.detNames.addAll(detNames.stream().sorted().collect(Collectors.toList()));

        this.variantsPrepareHours = variantsPrepareHours;
        this.prepareHours = countHoursForNabor(variantsPrepareHours.values());

        this.variantsColorHours = variantsColorHours;
        this.colorHours = countHoursForNabor(variantsColorHours.values());

        this.variantsColorCost = variantsColorCost;
        this.colorCost = countHoursForNabor(variantsColorCost.values());
    }

}
