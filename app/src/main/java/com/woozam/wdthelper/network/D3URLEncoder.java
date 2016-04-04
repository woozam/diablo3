package com.woozam.wdthelper.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by woozam on 2016-01-31.
 */
public class D3URLEncoder {
    public static String encode(String s, String charsetName) {
        try {
            return URLEncoder.encode(s, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}