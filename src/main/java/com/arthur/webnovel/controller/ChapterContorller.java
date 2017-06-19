package com.arthur.webnovel.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.entity.Story;
import com.arthur.webnovel.service.StoryService;
import com.arthur.webnovel.util.Logics;

@Controller
@RequestMapping("/story/chapter")
public class ChapterContorller {
    @Autowired
    private StoryService storyService;

    @RequestMapping(value = "/create/{storyId}", method = RequestMethod.GET)
    public String create(@PathVariable("storyId") int storyId, Model model , HttpSession session, RedirectAttributes attrs){
        Member loginUser = Logics.memberFromSession(session);
        Story story = storyService.get(storyId);
        return "/story/chapter/edit";
    }
}
