package com.driftycode.productsassignment.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nagendra on 12/11/17.
 */

public class Common {
    private static String LIST_SEPARATOR = "__,__";

    // Move this code utils file
    public static String convertListToString(List<String> stringList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : stringList) {
            stringBuffer.append(str).append(LIST_SEPARATOR);
        }

        // Remove last separator
        int lastIndex = stringBuffer.lastIndexOf(LIST_SEPARATOR);
        stringBuffer.delete(lastIndex, lastIndex + LIST_SEPARATOR.length() + 1);

        return stringBuffer.toString();
    }

    public static List<String> convertStringToList(String str) {
        return Arrays.asList(str.split(LIST_SEPARATOR));
    }
}
