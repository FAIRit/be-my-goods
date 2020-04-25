package com.slyszmarta.bemygoods.display;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MainPageDisplayController {

    @GetMapping(value = "/")
    public String showMainPage(Model model) {
        return "main.html";
    }
}
