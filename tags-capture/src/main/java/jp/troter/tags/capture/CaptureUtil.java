package jp.troter.tags.capture;

import java.util.LinkedList;

/**
 * キャプチャで利用するユーティリティ
 */
public class CaptureUtil {

    /** プレフィックス */
    public static final String CONTENTS_FOR_PREFIX = "_contentFor";

    /**
     * キャプチャに利用する名前を取得する
     * @param name
     * @return
     */
    public static String captureName(String name) {
        return CaptureUtil.CONTENTS_FOR_PREFIX + capitalize(name);
    }

    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuffer(strLen)
            .append(Character.toTitleCase(str.charAt(0)))
            .append(str.substring(1))
            .toString();
    }

    public static String nullToEmpty(String string) {
        return (string == null) ? "" : string;
    }

    public static LinkedList<String> nullToEmptyList(LinkedList<String> list) {
        return (list == null) ? new LinkedList<String>() : list;
    }
}
