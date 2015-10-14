package com.cesarferreira.rxfacebook.library.utils;

/**
 * Created by cesarferreira on 13/10/15.
 */
public class Utils {

    public static String convert(String[] args) {
        String fields = "";
        for (int i = 0; args.length < i; i++) {
            fields += args[i] + ", ";
        }

        return substr(fields);
    }

    public static String substr(String str) {
        str = str.trim();
        if (str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
