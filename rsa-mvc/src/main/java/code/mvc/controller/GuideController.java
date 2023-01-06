package code.mvc.controller;

import code.guide.element.Guide;
import code.mvc.model.Guidesql;
import code.mvc.mvcutils.GuidesqlUtils;
import code.mvc.repository.GuidesqlRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

import static code.guide.utils.MyConsts.GUIDE_DIR;
import static code.guide.utils.MyConsts.LIB_DIR;

@AllArgsConstructor
@Controller
@RequestMapping("/guide")
public class GuideController {
    private final GuidesqlRepository guidesqlRepository;

    @GetMapping
    public String show(Model model) {
        List<String> cars = guidesqlRepository.findAllNames();
        model.addAttribute("cars", cars);
        return "guidePage";
    }

    @PostMapping
    public String addGuide(String fileName, String fileText, Model model) throws IOException {
//        @RequestParam(value = "fileText")
//        Guide guide;
//        try {
//            guide = GuidesqlUtils.getGuideFromJsonFile(LIB_DIR + url);
//        } catch (IOException e) {
//            String result = "Ошибка чтения из файла " + url;
//            model.addAttribute("result", result);
//            return "showResult";
//        }

        String result;
        Guide guide = GuidesqlUtils.getGuideFromJsonText(fileText);
        Guidesql guidesql = guidesqlRepository.findByName(guide.getName())
                .orElse(null);
        if(guidesql != null) {
            result = "Справочник " + fileName + " уже существует";
        } else {
            guidesql = GuidesqlUtils.makeSqlFromGuide(guide);
            guidesqlRepository.save(guidesql);
            result = "Справочник " + fileName + " добавлен";
        }
        model.addAttribute("result", result);
        return "showResult";
    }

    @PostMapping("/delete")
    public String deleteGuide(String name, Model model) {
        Guidesql guidesql = guidesqlRepository.findByName(name).get();
        guidesqlRepository.delete(guidesql);

        String result = "Справочник " + name + " удален";
        model.addAttribute("result", result);
        return "showResult";
    }


}
