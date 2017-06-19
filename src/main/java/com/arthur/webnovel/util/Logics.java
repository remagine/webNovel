package com.arthur.webnovel.util;

import javax.servlet.http.HttpSession;

import com.arthur.webnovel.entity.Member;

public class Logics {

    public static Member memberFromSession(HttpSession session) {
        return (Member) session.getAttribute(Const.MEMBER_IN_SESSION_KEY);
    }

    public static void memberToSession(Member member, HttpSession session) {
        session.setAttribute(Const.MEMBER_IN_SESSION_KEY, member);
    }

    public static boolean isAdminLogin(HttpSession session) {
        Member member = memberFromSession(session);
        return null != member;
    }
}
