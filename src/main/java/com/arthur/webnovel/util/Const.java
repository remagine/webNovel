package com.arthur.webnovel.util;

public class Const {

    public static final String ADMIN_IN_SESSION_KEY = "xy_admin"; // 관리자 세션키
    public static final String MEMBER_IN_SESSION_KEY = "xy_member"; // 유저 세션키
    public static final String CUSTOMER_IN_SESSION_KEY = "xy_customer"; // 커머스 세션키
    public static final String NON_MEMBER_IN_SESSION_KEY = "xy_non_member"; // 비회원 고객문의 관련 세션키

    public static final int FRONT_LIST_SIZE = 12;
    public static final int FRONT_ITEM_LIST_SIZE = 12;
    public static final int ADMIN_LIST_SIZE = 12;
    public static final int PAGES_PER_PAGE_GROUP = 10;

    public static final String VIEW_MESSAGE_FLASH_KEY = "xtdViewMessage";

    public static final String FRAGMENT_MAP_KEY = "x_fragment_map"; // Fragment interceptor map

    // 브랜드 카테고리 기본 위치 값
    public static final Integer BRAND_LOCATION = 9999;

    public static final int CONTENT_ORDERING = 0;

    public static final String IMAGE_PREFIX_PATH = "image";
    public static final String FILE_PREFIX_PATH = "uploadfiles/files";
}