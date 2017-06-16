package com.arthur.webnovel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arthur.webnovel.command.RegistForm;
import com.arthur.webnovel.util.BusinessLogics;
import com.arthur.webnovel.util.ViewMessage;

@Controller
@RequestMapping("/member")
public class MemberController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false, name = "redirectUrl", defaultValue = "") String redirectUrl,
            Model model) {

        model.addAttribute("redirectUrl", redirectUrl);

        return "/member/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(required = true, name = "uid") String uid,
            @RequestParam(required = true, name = "password") String password,
            @RequestParam(required = false, name = "redirectUrl") String redirectUrl, RedirectAttributes attrs,
            HttpServletRequest request,
            HttpSession session, HttpServletResponse res) throws Exception {

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
            RegistForm form, Model model) {

        model.addAttribute("redirectUrl", redirectUrl);

        return "/member/regist";
    }
}