package com.arthur.webnovel.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/search")
public class SearchController {

    @RequestMapping(value = "/tag")
    public String tag(@RequestParam(value = "q") String q, Model model , HttpSession session, RedirectAttributes attrs){


        String[] qArray = q.split(" "); //띄어쓰기를 만날때마다 배열로 구분지어 저장합니다.



        return "redirect:/story/create";
    }

}
