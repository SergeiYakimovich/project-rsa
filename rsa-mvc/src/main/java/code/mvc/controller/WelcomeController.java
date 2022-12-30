package code.mvc.controller;

import code.mvc.model.Car;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public String show() {
        return "welcomePage";
    }
}
