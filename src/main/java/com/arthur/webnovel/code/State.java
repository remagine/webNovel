package com.arthur.webnovel.code;

import org.apache.commons.lang3.StringUtils;

public enum State {
    on("공개"), off("비공개"), wait("대기"), end("종료"), deleted("삭제");
    private String desc;

    private State(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static boolean contains(String value) {
        for (State item : State.values()) {
            if (StringUtils.equals(value, item.name())) {
                return true;
            }
        }
        return false;
    }

}
