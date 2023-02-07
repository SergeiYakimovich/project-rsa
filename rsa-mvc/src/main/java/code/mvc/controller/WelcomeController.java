package code.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * контроллер - приветственная страница
 */
@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public String show() {
        return "welcomePage";
    }
}
