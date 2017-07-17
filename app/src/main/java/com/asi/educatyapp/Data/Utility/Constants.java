package com.asi.educatyapp.Data.Utility;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by ASI on 2/22/2017.
 */

public class Constants {


    public static final String PREF="PREF";
    public static final String TKEY="TKEY";
    public static final String SKEY="SKEY";
    public static final String GKEY="GKEY";
    public static final String TYPE_KEY="TYPE_KEY";
    public static final String T_STUDENT="T_STUDENT";
    public static final String T_TEACHER="T_TEACHER";
    public final static String BASEURL="http://educatyapp.890m.com/Apis/";
    public final static  SecureRandom random = new SecureRandom();
    public static   String genrateCode() {
        return new BigInteger(130, random).toString(32);
    }
}
