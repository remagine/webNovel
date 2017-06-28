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
import com.arthur.webnovel.code.State;
import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.intercepter.MemberRole;
import com.arthur.webnovel.service.MemberService;
import com.arthur.webnovel.util.BaseUtil;
import com.arthur.webnovel.util.BusinessLogics;
import com.arthur.webnovel.util.Const;
import com.arthur.webnovel.util.Logics;
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
            ViewMessage.error().message("이메일 주소와 암호를 입력해 주세요.").register(attrs);
            return "redirect:/member/login?redirectUrl=" + StringUtils.defaultString(redirectUrl);
        }

        Result<Member> result = memberService.authenticate(email, password);

        if (result.isError()) {
            // login fail!
            ViewMessage.error().message("비밀번호가 틀렸거나 존재하지않는 아이디입니다.").register(attrs);
            return "redirect:/member/login?redirectUrl=" + redirectUrl;
        } else {
            Logics.memberToSession(result.payload(), session);
            if (StringUtils.isNotBlank(redirectUrl)) {
                return "redirect:" + BusinessLogics.link(request, redirectUrl);
            } else {
                return "redirect:" + BusinessLogics.link(request, "/");
            }
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(Model model, HttpSession session) {
        session.removeAttribute(Const.MEMBER_IN_SESSION_KEY);
        //세션 소멸이 아니라 무효화
        session.invalidate();

        return "redirect:/";
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
            member.setState(State.on);
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

    @MemberRole
    @RequestMapping(value = "/mypage/profile")
    public String profile(Model model, HttpSession session, RedirectAttributes attrs) {
        Member loginUser = Logics.memberFromSession(session);

        /*if(null == loginUser){
            ViewMessage.error().message("로그인이 필요한 서비스입니다.").register(attrs);
            return "redirect:/member/login";
        }*/

        model.addAttribute("member", loginUser);
        return "/member/mypage/profile";
    }

    @MemberRole
    @RequestMapping(value = "/mypage/update")
    public String update(Model model, Member memberForm, HttpSession session, RedirectAttributes attrs) {
        Member loginUser = Logics.memberFromSession(session);

        /* if(null == loginUser){
            ViewMessage.error().message("로그인이 필요한 서비스입니다.").register(attrs);
            return "redirect:/member/login";
        }*/
        Member origin = memberService.select(loginUser.getId());
        memberForm.setId(origin.getId());
        memberForm.setState(State.on);
        if(StringUtils.isBlank(memberForm.getPassword())){
            memberForm.setPassword(origin.getPassword());
        }
        memberService.update(memberForm);

        loginUser = memberService.select(memberForm.getId());
        ViewMessage.success().message("수정이 완료되었습니다.").register(attrs);
        model.addAttribute("member", loginUser);
        return "redirect:/member/mypage/profile";
    }

}