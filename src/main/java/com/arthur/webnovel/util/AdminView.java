package com.arthur.webnovel.util;

import org.apache.commons.lang3.StringUtils;


public class AdminView {
    private AdminView() {
    }

    public static String selectIfEquals(String value, String ref) {
        if (StringUtils.equals(value, ref)) {
            return "selected";
        }
        return "";
    }

    public static String selectIfEquals(Integer value, Integer ref) {
        if (value == ref) {
            return "selected";
        }
        return "";
    }

    public static String checkIfEquals(String value, String ref) {
        if (StringUtils.equals(value, ref)) {
            return "checked";
        }
        return "";
    }

    public static String checkIfEquals(Integer value, Integer ref) {
        if (value == ref) {
            return "checked";
        }
        return "";
    }
}
