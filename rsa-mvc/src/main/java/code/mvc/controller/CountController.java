package code.mvc.controller;

import code.guide.element.Guide;
import code.mvc.model.Car;
import code.mvc.model.Guidesql;
import code.mvc.mvcutils.GuidesqlUtils;
import code.mvc.repository.CarRepository;
import code.mvc.repository.GuidesqlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.readString;

@AllArgsConstructor
@Controller
@RequestMapping("/count")
public class CountController {
    private final CarRepository carRepository;
    private final GuidesqlRepository guidesqlRepository;

    @GetMapping
    public String show(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "countPage";
    }

    @PostMapping
    public String count(String name, String text, Model model) throws IOException {
        Guidesql guidesql = guidesqlRepository.findByCarName(name).get();
        Guide guide = GuidesqlUtils.makeGuideFromSql(guidesql);
        String result = guide.getName() + " - " + guide.getDetNaborSets().size()
                + " - " + guide.getDetSingles().size()
                + " - " + text.substring(0,10) ;
        model.addAttribute("result", result);
        return "showResult";
    }

}
