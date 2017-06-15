package com.arthur.webnovel.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

public class HttpUtils {

    private HttpUtils() {}

    public static String path(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getServletPath());
        String pp = request.getPathInfo();
        if (StringUtils.isNotBlank(pp)) {
            sb.append(pp);
        }
        return sb.toString();
    }

    public static String viewMessage(HttpServletRequest request) {
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if (null != map && map.containsKey(Const.VIEW_MESSAGE_FLASH_KEY)) {
            ViewMessage vm = (ViewMessage) map.get(Const.VIEW_MESSAGE_FLASH_KEY);
            return vm.getToastrCode();
        }
        return "";
    }

    public static String viewMessageAlert(HttpServletRequest request) {
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if (null != map && map.containsKey(Const.VIEW_MESSAGE_FLASH_KEY)) {
            ViewMessage vm = (ViewMessage) map.get(Const.VIEW_MESSAGE_FLASH_KEY);
            return vm.getAlertCode();
        }
        return "";
    }

    public static String viewMessageNotify(HttpServletRequest request) {
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if (null != map && map.containsKey(Const.VIEW_MESSAGE_FLASH_KEY)) {
            ViewMessage vm = (ViewMessage) map.get(Const.VIEW_MESSAGE_FLASH_KEY);
            return vm.getNotifyCode();
        }
        return "";
    }

    public static String getDoamin(HttpServletRequest request) {
        StringBuilder domain = new StringBuilder();
        try {
            URL url = new URL(request.getRequestURL().toString());
            domain.append(url.getProtocol()).append("://");
            domain.append(url.getHost());
            if (url.getPort() != 80 && url.getPort() != -1) {
                domain.append(":").append(String.valueOf(url.getPort()));
            }
        } catch (MalformedURLException e) {
            return "http://www.sempio.com";
        }
        return domain.toString();
    }

    public static String currentUrl(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        if (request.isSecure()) {
            sb.append("https://");
        } else {
            sb.append("http://");
        }
        sb.append(request.getServerName());
        int port = request.getServerPort();
        if (80 != port && 443 != port) {
            sb.append(":").append(port);
        }
        sb.append(path(request));
        return sb.toString();
    }

    public static String ip(HttpServletRequest request) {
        String ip = null;
        ip = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("HTTP-X-FORWARDED-FOR");
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("REMOTE-ADDR");
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static boolean isIE(String userAgent) {
        return StringUtils.contains(userAgent, "MSIE") ||
                StringUtils.contains(userAgent, "Trident/");
    }

    public static boolean isMobileBrowser(String userAgent) {
        return StringUtils.contains(userAgent, "AppleWebKit") &&
                (StringUtils.contains(userAgent, "iPhone;") ||
                        StringUtils.contains(userAgent, "iPad;") ||
                        StringUtils.contains(userAgent, "Android"));
    }

    /*
     * TODO: 안드로이드 tablet 제외 추가 필요
     */
    public static boolean isSmartPhone(String userAgent) {
        return StringUtils.contains(userAgent, "AppleWebKit") &&
                (StringUtils.contains(userAgent, "iPhone;") ||
                        StringUtils.contains(userAgent, "Android")) &&
                ! StringUtils.contains(userAgent, "iPad;");
    }

    public static String mark(String keyword, String contents) {
        if (StringUtils.isNoneBlank(keyword) && StringUtils.isNoneBlank(contents)) {
            return contents.replaceAll(keyword, "<mark>"+keyword+"</mark>");
        }
        return contents;
    }


    public static String encodeParam(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }
}
