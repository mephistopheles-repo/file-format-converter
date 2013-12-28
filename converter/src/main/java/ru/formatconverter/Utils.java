package ru.formatconverter;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Date: 29.12.13
 * Time: 1:53
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    private static final String regEscape = "${escape}";

    public static String escapeRegExp(String str) {
        return StringUtils.replace(str, "\\\\", regEscape);
    }

    public static String unEscapeRegExp(String str) {
        return StringUtils.replace(str, regEscape, "\\");
    }
}
