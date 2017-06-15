package com.arthur.webnovel.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.google.common.base.Charsets;

public class BaseUtil {

    public static String join(String separator, String... items) {
        StringBuffer sb = new StringBuffer();
        if (ArrayUtils.isNotEmpty(items)) {
            for (String i : items) {
                sb.append(separator);
                sb.append(i);
            }
        }
        return sb.toString();
    }

    public static String getRefineUrlKey(String title) {
        String reg = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        String spaces = "\\s{2,}";
        String urlKey = title.replaceAll(reg, "");
        urlKey = urlKey.trim();
        urlKey = urlKey.replaceAll(spaces, " ");
        urlKey = urlKey.replaceAll("\\p{Z}", "-");

        return urlKey;
    }

    public static String getUrlKeyIndex(String urlKey, String lastUrlKey) {
        int urlKeyIndex = 1;
        if (!lastUrlKey.equals(urlKey)) {
            urlKeyIndex = Integer.parseInt(lastUrlKey.substring(lastUrlKey.length() - 1, lastUrlKey.length()));
            urlKeyIndex++;
        }
        String currentIndex = Integer.toString(urlKeyIndex);
        urlKey = urlKey.concat("_").concat(currentIndex);
        return urlKey;
    }

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int TO_CAPITAL_NUMBER = 65;

    // 랜덤 문자열 생성 (파일 업로드 폴더생성용)
    public static StringBuilder getRandomDir() {
        StringBuilder randomDir = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            randomDir.append((char) ((int) (Math.random() * ALPHABET.length()) + TO_CAPITAL_NUMBER));
        }
        return randomDir;
    }

    // 브라우저 확인
    public static String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.contains("Firefox")) {
            return "Firefox";
        } else if (header.contains("Chrome")) {
            return "Chrome";
        } else if (header.contains("Opera")) {
            return "Opera";
        } else if (header.contains("Safari")) {
            return "Safari";
        } else {
            return "MSIE";
        }
    }

    // 요일 확인
    public static String getDateDay(Date date) {
        String day = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int dayNum = cal.get(Calendar.DAY_OF_WEEK);

            switch (dayNum) {
                case 1:
                    day = "일";
                    break;
                case 2:
                    day = "월";
                    break;
                case 3:
                    day = "화";
                    break;
                case 4:
                    day = "수";
                    break;
                case 5:
                    day = "목";
                    break;
                case 6:
                    day = "금";
                    break;
                case 7:
                    day = "토";
                    break;

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return day;
    }

    public static Date getToday() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calDate = Calendar.getInstance();
        String getDate = formatter.format(calDate.getTime());
        Date today = formatter.parse(getDate);

        return today;
    }

    public static boolean makeFolder(String directory) {

        boolean result = false;

        File dFile = new File(directory);

        if (!dFile.exists() || !dFile.isDirectory()) {
            result = dFile.mkdirs();
        }

        return result;
    }

    public static String getPassword(String str) {
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String fixMobileNo(String src) {
        if (StringUtils.isNotBlank(src)) {
            if (StringUtils.startsWith(src, "01")) {
                int len = src.length();
                if (10 == len) {
                    return StringUtils.join(new String[] {src.substring(0, 3), src.substring(3, 6), src.substring(6)}, "-");
                } else if (11 == len) {
                    return StringUtils.join(new String[] {src.substring(0, 3), src.substring(3, 7), src.substring(7)}, "-");
                }
            }
        }
        return src;
    }

    /**
     * utf8 문자열을 바이트 크기 제한이 넘으면 잘라낸다.
     */
    public static String limitByByteSize(String src, int limit) {
        if (StringUtils.isEmpty(src)) {
            return src;
        }

        String ret = null;
        Charset utf8 = Charsets.UTF_8;
        final byte[] bs = src.getBytes(utf8);
        if (bs.length > 200) {
            CharsetDecoder decoder = utf8.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
            decoder.reset();
            CharBuffer decoded;
            try {
                decoded = decoder.decode(ByteBuffer.wrap(bs, 0, 200));
                ret = decoded.toString();
            } catch (CharacterCodingException e) {
                byte[] tt = new byte[200];
                System.arraycopy(bs, 0, tt, 0, 200);
                ret = new String(tt, utf8);
            }
        } else {
            ret = src;
        }
        return ret;
    }

    /**
     * 안전하게 정수를 파싱. 에러가 나면 def 값을 반환
     */
    public static int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    // make sitemap code - html 페이지 용
    public static void makeXmlCode(String pageUrl, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date now = new Date();

        out.println("<url>");

        out.print("<loc>");
        out.print("http://en.sempio.com");
        out.print(pageUrl);
        out.println("</loc>");

        out.print("<lastmod>");
        out.print(sdf.format(now));
        out.println("</lastmod>");

        out.println("<changefreq>daily</changefreq>");
        out.println("</url>");
    }

    public static Integer[] boxArray(int[] src) {
        if (null == src) {
            return null;
        }
        Integer[] ret = new Integer[src.length];
        int idx = 0;
        for (int val : src) {
            ret[idx++] = Integer.valueOf(val);
        }
        return ret;
    }


    /*
     * 2016.11.01 00:00:00 시점의 timestamp
     * generateSequence는 이 시점 이후로부터 timestamp를 이용하여 sequence를 생성.
     * 이유는 sequence 값이 너무 커지는 것을 방지하기 위해서.
     * 1년의 총 second 수가 대략 3천만(31,536,000)이므로 대략 앞으로 70년 정도 사용 가능 :)
    */
    private static final long STARTING_TIMESTAMP;

    static {
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 10, 1, 0, 0, 0);
        STARTING_TIMESTAMP = cal.getTime().getTime();
    }

    public static int generateSequence() {
        long time = System.currentTimeMillis() - STARTING_TIMESTAMP;
        int ret = (int) (time / 1000);
        return ret;
    }

    public static String buildPhoneNumber(String p1, String p2, String p3) {
        if (StringUtils.isNotBlank(p1) && StringUtils.isNotBlank(p2) && StringUtils.isNotBlank(p3)) {
            return new StringBuilder().append(p1)
                    .append("-")
                    .append(p2)
                    .append("-")
                    .append(p3).toString();
        }
        return null;
    }

    public static String currentDateTime() {
        return DateUtils.format(new Date(), "yyyyMMdd'T'HHmmss");
    }

    public static <T extends Enum<T>> T safeValueOf(Class<T> enumType, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return Enum.valueOf(enumType, name);
        } catch (Exception e) {
            return null;
        }
    }

    private static final Pattern LOG_STR_PATTERN = Pattern.compile("(\\{\\s*\\})");
    public static String logMessage(String message, Object... arguments) {
        try {
            Matcher m = LOG_STR_PATTERN.matcher(message);
            String result = null;
            if (m.find()) {
                if (arguments != null && arguments.length > 0) {
                    result = m.replaceFirst(arguments[0].toString());
                    if (arguments.length > 1) {
                        result = logMessage(result, ArrayUtils.subarray(arguments, 1, arguments.length));
                    }
                }
            }
            return StringUtils.isNoneBlank(result) ? result : message;
        } catch (Exception e) {
            return "";
        }
    }

    public static String escapeHtml(String src) {
        return StringEscapeUtils.escapeHtml4(src);
    }

    public static String formatCurrency(int amount) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(amount);
    }

    public static String safeUrlEncode(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return s;
    }

    public static String encodeBase64(String value) {
        if (StringUtils.isNoneBlank(value)) {
            return Base64.encodeBase64URLSafeString(value.getBytes());
        }
        return value;
    }

    public static String decodeBase64(String value) {
        try {
            if (StringUtils.isNoneBlank(value)) {
                return new String(Base64.decodeBase64(value), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
         // ignore
        }
        return value;
    }

    public static Integer[] StringArrToIntArr(String[] s) {
        Integer[] result = new Integer[s.length];
        for (int i = 0; i < s.length; i++) {
           result[i] = Integer.parseInt(s[i]);
        }
        return result;
     }
}
