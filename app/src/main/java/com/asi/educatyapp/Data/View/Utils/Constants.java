package com.asi.educatyapp.Data.View.Utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by ASI on 2/22/2017.
 */

public class Constants {
    public final static String BASEURL="http://educatyapp.890m.com/Apis/";

    public final static  SecureRandom random = new SecureRandom();

    public static   String genrateCode() {
        return new BigInteger(130, random).toString(32);
    }
}
