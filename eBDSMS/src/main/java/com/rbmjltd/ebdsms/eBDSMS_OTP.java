package com.rbmjltd.ebdsms;

import java.util.Random;

public class eBDSMS_OTP {

    String LATTER = "0987654321";
    String NUMBER = "1234567890";

    char[] RANDOM = (LATTER+LATTER.toUpperCase()+NUMBER).toCharArray();

    public String OTPString(int lenght) {
        StringBuilder result = new StringBuilder(lenght);
        for (int i = 0; i < lenght; i++) {
            result.append(RANDOM[new Random().nextInt(RANDOM.length)]);
        }
        return result.toString();
    }



}
