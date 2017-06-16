package com.arthur.webnovel.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class BusinessLogics {

    private BusinessLogics() {
    }

    public static String redirectUrl(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getServletPath());
        String path = request.getPathInfo();
        if (StringUtils.isNotBlank(path)) {
            sb.append(path);
        }
        int idx = 0;
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            String[] values = request.getParameterValues(key);
            if (null != values && values.length > 0) {
                for (String v : values) {
                    if (0 == idx) {
                        sb.append("?");
                    } else {
                        sb.append("&");
                    }
                    sb.append(key).append("=");
                    sb.append(BaseUtil.safeUrlEncode(v));
                    idx++;
                }
            }
        }
        return BaseUtil.safeUrlEncode(sb.toString());
    }

    public static String loginLink(HttpServletRequest request) {
        String path = request.getServletPath();
        if (StringUtils.isBlank(path)) {
            return link(request, "/member/login");
        }
        if (StringUtils.equals(path, "/")) {
            return link(request, "/member/login");
        }
        if (StringUtils.equals(path, "/home")) {
            return link(request, "/member/login");
        }
        if (StringUtils.startsWith(path, "/mypage")) {
            return link(request, "/member/login");
        }
        if (StringUtils.startsWith(path, "/member/login")) {
            return link(request, "/member/login");
        }
        return link(request, "/member/login?redirectUrl=" + redirectUrl(request));
    }

    public static String link(HttpServletRequest request, String link) {
        String hostname = request.getServerName();
        if (StringUtils.equals("www.sempio.com", hostname) || StringUtils.equals("sempio-test.in.iropke.com", hostname) || StringUtils.equals("sempio-stage.in.iropke.com", hostname)) {
            if (StringUtils.startsWith(link, "/member/login") || StringUtils.startsWith(link, "/member/nonmember") ||
                    StringUtils.startsWith(link, "/mypage") ||
                    StringUtils.startsWith(link, "/market/order")) {
                if (StringUtils.equals("sempio-test.in.iropke.com", hostname) || StringUtils.equals("sempio-stage.in.iropke.com", hostname)){
                    return "http://" + hostname + link;
                } else {
                    return "https://" + hostname + link;
                }
            } else {
                return "http://" + hostname + link;
            }
        }
        return link;
    }
}
