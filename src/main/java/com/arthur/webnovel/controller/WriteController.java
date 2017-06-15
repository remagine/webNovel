package com.arthur.webnovel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/story")
public class WriteController {
    @RequestMapping("/create")
    public String home(Model model){
        return "/write/edit";
    }
}
