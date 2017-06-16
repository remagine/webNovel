package com.arthur.webnovel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arthur.webnovel.code.Result;
import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.service.MemberService;
import com.arthur.webnovel.util.BaseUtil;
import com.arthur.webnovel.util.BusinessLogics;
import com.arthur.webnovel.util.ViewMessage;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false, name = "redirectUrl", defaultValue = "") String redirectUrl,
            Model model) {

        model.addAttribute("redirectUrl", redirectUrl);

        return "/member/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(required = true, name = "email") String email,
            @RequestParam(required = true, name = "password") String password,
            @RequestParam(required = false, name = "redirectUrl") String redirectUrl, RedirectAttributes attrs,
            HttpServletRequest request,
            HttpSession session, HttpServletResponse res) throws Exception {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            ViewMessage.error().message("Please enter your email and password.").register(attrs);
            return "redirect:/member/login?redirectUrl=" + StringUtils.defaultString(redirectUrl);
        }

        Result<Member> result = memberService.authenticate(email, password);

        if (null != null) {
            if (StringUtils.isNotBlank(redirectUrl)) {
                return "redirect:" + BusinessLogics.link(request, redirectUrl);
            } else {
                return "redirect:" + BusinessLogics.link(request, "/");
            }
        } else {
            // login fail!
            ViewMessage.error().message("비밀번호가 틀렸거나 존재하지않는 아이디입니다.").register(attrs);
            return "redirect:/member/login?redirectUrl=" + redirectUrl;
        }
    }

    @RequestMapping(value = "/regist", method = RequestMethod.GET)
    public String regist(@RequestParam(required = false, name = "redirectUrl", defaultValue = "") String redirectUrl,
            Model model) {

        model.addAttribute("redirectUrl", redirectUrl);

        return "/member/regist";
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String regist(@RequestParam(required = false, name = "redirectUrl", defaultValue = "") String redirectUrl,
            Member member, RedirectAttributes attrs, Model model) {
        if(StringUtils.isNotBlank(member.getEmail()) && StringUtils.isNotBlank(member.getPassword())){
            String pw = BaseUtil.getPassword(member.getPassword());
            member.setPassword(pw);
            int result = memberService.insert(member);
            if(result > 0){
                ViewMessage.success().message("등록이 완료되었습니다.").register(attrs);
                return "redirect:/member/login";
            } else {
                ViewMessage.error().message("잘못된 접근입니다.").register(attrs);
                return "redirect:/member/regist";
            }
        } else {
            ViewMessage.error().message("잘못된 접근입니다.").register(attrs);
            return "redirect:/member/regist";
        }
    }
}