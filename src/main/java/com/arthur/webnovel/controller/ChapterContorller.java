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
import com.arthur.webnovel.intercepter.MemberRole;
import com.arthur.webnovel.service.StoryService;
import com.arthur.webnovel.util.Logics;
import com.arthur.webnovel.util.ViewMessage;

@Controller
@RequestMapping("/story/chapter")
public class ChapterContorller {
    @Autowired
    private StoryService storyService;

    //처음 story 작성 후 chapter 작성 페이지로 이동
    @MemberRole
    @RequestMapping(value = "/create/{storyId}", method = RequestMethod.GET)
    public String create(@PathVariable("storyId") int storyId, Model model , HttpSession session, RedirectAttributes attrs){
        Member loginUser = Logics.memberFromSession(session);

        Story story = storyService.get(storyId, loginUser);
        if(story != null){
            return create(model);
        } else {
            ViewMessage.error().message("서버 오류로 저장에 실패했습니다.").register(attrs);
            return "redirect:/story/create";
        }
    }

    //chapter 등록
    @MemberRole
    @RequestMapping(value = "/create/{storyId}", method = RequestMethod.POST)
    public String registChapter(@PathVariable("storyId") int storyId, Model model , HttpSession session, RedirectAttributes attrs){
        Member loginUser = Logics.memberFromSession(session);

        Story story = storyService.get(storyId, loginUser);
        if(story != null){
            return create(model);
        } else {
            ViewMessage.error().message("서버 오류로 저장에 실패했습니다.").register(attrs);
            return "redirect:/story/create";
        }
    }

    public String create(Model model){
        return "/story/chapter/edit";
    }
}
