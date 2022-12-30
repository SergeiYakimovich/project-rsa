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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

import static code.guide.utils.MyConsts.GUIDE_DIR;

@AllArgsConstructor
@Controller
@RequestMapping("/guide")
public class GuideController {
    private final CarRepository carRepository;
    private final GuidesqlRepository guidesqlRepository;

    @GetMapping
    public String show(Model model) {
        List<Car> cars = carRepository.findAll();
        List<Guidesql> guideqls = guidesqlRepository.findAll();

        model.addAttribute("cars", cars);
        model.addAttribute("guides", guideqls);
        return "guidePage";
    }

    @PostMapping
    public String addGuide(String url, Model model) throws IOException {
        Guide guide;
        try {
            guide = GuidesqlUtils.getGuideFromJsonFile(GUIDE_DIR + url);
        } catch (IOException e) {
            String result = "Ошибка чтения из файла " + url;
            model.addAttribute("result", result);
            return "showResult";
        }
        Guidesql guidesql = GuidesqlUtils.makeSqlFromGuide(guide);
        guidesqlRepository.save(guidesql);
        String result = "Справочник " + url + " добавлен";
        model.addAttribute("result", result);
        return "showResult";
    }

    @PostMapping("/delete")
    public String deleteGuide(Long id, Model model) {
        carRepository.deleteById(id);
        String result = "Справочник удален";
        model.addAttribute("result", result);
        return "showResult";
    }


}
