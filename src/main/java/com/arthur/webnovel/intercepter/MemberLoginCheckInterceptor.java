package com.arthur.webnovel.intercepter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.util.Logics;

public class MemberLoginCheckInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger(MemberLoginCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("handle = {}", handler);
        if (handler instanceof HandlerMethod) {

            HandlerMethod hm = (HandlerMethod) handler;

            MemberRole memberRole = hm.getMethodAnnotation(MemberRole.class);
            if (memberRole == null) {
                memberRole = hm.getBeanType().getAnnotation(MemberRole.class);
            }

            if (memberRole != null) {
                HttpSession session = request.getSession();
                Member member = Logics.memberFromSession(session);
                if(null == member){
                    return gotoLoginPage(request, response);
                }
            }
        }
        return true;
    }

    private final URLCodec codec = new URLCodec("UTF-8"); // immutable and thread-safe
    public boolean gotoLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getServletPath());
        String p = request.getPathInfo();
        String queryString = request.getQueryString();
        String redirectUrl = null;
        if (StringUtils.isNoneBlank(p)) {
            sb.append(p);
        }
        if (StringUtils.isNoneBlank(queryString)) {
            sb.append("?").append(queryString);
        }
        try {
            redirectUrl = codec.encode(sb.toString());
        } catch (EncoderException e) {
            log.error("redirectUrl encode error", e);
            redirectUrl = StringUtils.join(request.getServletPath(), p);
        }
        response.sendRedirect("/member/login?redirectUrl=" + redirectUrl);
        return false;
    }
}
