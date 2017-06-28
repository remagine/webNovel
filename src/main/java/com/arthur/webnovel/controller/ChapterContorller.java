package com.arthur.webnovel.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arthur.webnovel.code.State;
import com.arthur.webnovel.entity.Chapter;
import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.entity.Story;
import com.arthur.webnovel.intercepter.MemberRole;
import com.arthur.webnovel.service.ChapterService;
import com.arthur.webnovel.service.StoryService;
import com.arthur.webnovel.util.Logics;
import com.arthur.webnovel.util.ViewMessage;

@Controller
@RequestMapping("/story/chapter")
public class ChapterContorller {
    @Autowired
    private StoryService storyService;

    @Autowired
    private ChapterService chapterService;

    //처음 story 작성 후 chapter 작성 페이지로 이동
    @MemberRole
    @RequestMapping(value = "/create/{storyId}", method = RequestMethod.GET)
    public String create(@PathVariable("storyId") int storyId, Model model , HttpSession session, RedirectAttributes attrs){
        Member loginUser = Logics.memberFromSession(session);

        Story story = storyService.get(storyId, loginUser);
        model.addAttribute("story", story);
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
    public String registChapter(@PathVariable("storyId") int storyId, Chapter chapter,Model model , HttpSession session, RedirectAttributes attrs){
        Member loginUser = Logics.memberFromSession(session);

        Story story = storyService.get(storyId, loginUser);
        model.addAttribute("story", story);
        if(story != null){
            chapter.setStory(story);
            chapter.setState(State.on);
            chapter.setViews(0);
            Integer id = chapterService.insert(chapter);
            model.addAttribute("chapter", chapter);
            return "redirect:/story/chapter/view/"+story.getId() +"/"+id;
        } else {
            ViewMessage.error().message("서버 오류로 저장에 실패했습니다.").register(attrs);
            return "redirect:/story/create";
        }
    }

    @MemberRole
    @RequestMapping(value = "/update/{storyId}/{chapterId}", method = RequestMethod.POST)
    public String updateChapter(@PathVariable("storyId") int storyId, @PathVariable("chapterId") int chapterId, Chapter chapter, Model model, HttpSession session, RedirectAttributes attrs){
        Member loginUser = Logics.memberFromSession(session);
        Story story = storyService.get(storyId, loginUser);

        model.addAttribute("story", story);
        if(story != null){
            Chapter origin = chapterService.get(story, chapterId);
            chapter.setStory(story);
            chapter.setId(origin.getId());
            chapter.setViews(origin.getViews());
            chapter.setState(origin.getState());
            chapterService.update(chapter);
            model.addAttribute("chapter", chapter);
            ViewMessage.success().message("저장되었습니다.").register(attrs);
            return "redirect:/story/chapter/view/"+story.getId() +"/"+chapter.getId();
        } else {
            ViewMessage.error().message("서버 오류로 저장에 실패했습니다.").register(attrs);
            return "redirect:/story/create";
        }
    }

    @MemberRole
    @RequestMapping(value = "/view/{storyId}/{chapterId}", method = RequestMethod.GET)
    public String view(@PathVariable("storyId") int storyId, @PathVariable("chapterId") int chapterId, Model model, HttpSession session, RedirectAttributes attrs){
        Member loginUser = Logics.memberFromSession(session);
        Story story = storyService.get(storyId, loginUser);

        if(null != story){
            Chapter chapter = chapterService.get(story, chapterId);
            model.addAttribute("story", story);
            model.addAttribute("chapter", chapter);
            return "/story/chapter/view";
        } else {
            ViewMessage.error().message("유효하지 않은 접근입니다.").register(attrs);
            return "redirect:/";
        }
    }

    public String create(Model model){
        return "/story/chapter/edit";
    }
}
