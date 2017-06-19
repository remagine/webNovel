package com.arthur.webnovel.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arthur.webnovel.code.State;
import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.entity.Story;
import com.arthur.webnovel.service.StoryService;
import com.arthur.webnovel.util.Logics;
import com.arthur.webnovel.util.ViewMessage;

@Controller
@RequestMapping("/story")
public class WriteController {
    private static Logger log = LoggerFactory.getLogger(WriteController.class);

    @Autowired
    private StoryService storyService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        return "/story/edit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model, Story story, HttpSession session, RedirectAttributes attrs){
        Member loginUser = Logics.memberFromSession(session);
        log.debug("1. loginUser ::::: {} " , loginUser );
        story.setMember(loginUser);
        story.setState(State.on);

        Integer id = storyService.insert(story);

        if(id < 0) {
            ViewMessage.error().message("서버 오류로 저장에 실패했습니다.").register(attrs);
            return "redirect:/story/edit";
        }

        ViewMessage.success().message("등록이 완료되었습니다.").register(attrs);
        return "/story/edit/" + id;
    }
}
