package code.mvc.controller;

import code.guide.calc.Calculator;
import code.guide.element.Guide;
import code.guide.element.Order;
import code.guide.service.OrderService;
import code.mvc.model.Guidesql;
import code.mvc.mvcutils.GuidesqlUtils;
import code.mvc.repository.GuidesqlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * контроллер для расчета н/ч по справочнику
 */
@AllArgsConstructor
@Controller
@RequestMapping("/count")
public class CountController {
    private final GuidesqlRepository guidesqlRepository;

    /**
     * основная страница
     */
    @GetMapping
    public String show(Model model) {
        List<String> cars = guidesqlRepository.findAllNames();
        model.addAttribute("cars", cars);
        return "countPage";
    }

    /**
     * расчет н/ч по справочнику для заданного списка з/ч (файл или текст в поле ввода)
     * @param name - имя справочника
     * @param fileName - имя файла
     * @param fileText - текст из файла
     * @param text - текст из поля для ввода
     * @param model - модель
     * @return - к-во н/ч
     * @throws IOException
     */
    @PostMapping
    public String count(String name, String fileName, String fileText, String text, Model model) throws IOException {
        Guidesql guidesql = guidesqlRepository.findByName(name).get();
        Guide guide = GuidesqlUtils.makeGuideFromSql(guidesql);
        String result;
        if(fileText.isEmpty() && text.isEmpty()) {
            result = "Введите з/ч для расчета";
        } else {
            if(text.isEmpty()) {
                text = fileText;
            }
            List<String> details = Arrays.stream(text.split("[\n,;]"))
                    .map(x -> x.trim())
                    .filter(s -> !s.isEmpty())
                    .filter(s -> !s.contains("Наименование"))
                    .sorted()
                    .collect(Collectors.toList());
            Order order = OrderService.makeSimpleOrder(details, fileName);
            double hours = Calculator.calculate(guide, order);
            result = "Для списка з/ч и справочника " + guide.getName()
                    + " результат = " + String.format("%.2f", hours) + " н/ч" ;
        }
        model.addAttribute("result", result);
        return "showResult";
    }

}
