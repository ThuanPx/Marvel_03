package com.hyperion.ths.marvel_03.utils;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ths on 27/05/2017.
 */

public final class Constant {
    private Constant() {

    }

    public static final String END_POINT_URL = "https://gateway.marvel.com";
    public static final String PUBLIC_KEY = "4582ff7d7509676253b6bc474a0020fb";
    public static final String PRIVATE_KEY = "4acceb2f9d2ab768c604c81a5f371b2e114b554e";
    public static final String IMAGEFORMAT = "/standard_xlarge.jpg";
    public static final long TIMESTAMP = 131710;
    public static final int CONNECTION_TIMEOUT = 60;
    public static final String BUNDLE_HERO = "BUNDLE_HERO";
    public static final int POINT = 0;

    public static String getHashKey() throws UnsupportedEncodingException {
        String hashKey = getMD5EncryptedString(TIMESTAMP + PRIVATE_KEY + PUBLIC_KEY);
        return hashKey;
    }

    public static String getMD5EncryptedString(String encTarget)
            throws UnsupportedEncodingException {
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.e("Error", e.getLocalizedMessage());
        }
        byte[] bytes = encTarget.getBytes("UTF-8");
        mdEnc.update(bytes, 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }
        return md5;
    }
}
