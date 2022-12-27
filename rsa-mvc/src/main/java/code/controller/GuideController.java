package code.controller;

import code.model.Car;
import code.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@AllArgsConstructor
//@Controller
//@RequestMapping("/")
//public class GuideController {
//    private final CarRepository carRepository;
//
//    @GetMapping
//    public String showMain(Model model) {
//        Car car1 = new Car(); car1.setName("VW");
//        Car car2 = new Car(); car2.setName("BMW");
//        Car car3 = new Car(); car3.setName("Ford");
//        List<Car> cars = List.of(car1, car2, car3);
//        model.addAttribute("cars", cars);
//        model.addAttribute("name", "12345");
//        return "greeting";
//    }
//
//}

@Controller
public class GuideController {

    @RequestMapping(value = "/")
    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}