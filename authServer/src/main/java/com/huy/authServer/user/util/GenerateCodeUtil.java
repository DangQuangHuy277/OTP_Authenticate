package com.huy.authServer.user.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GenerateCodeUtil {
    public static String generateCode() {
        String code;
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            int c = random.nextInt(0, 8999) + 1000;
            code = String.valueOf(c);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the random code");
        }

        return code;
    }
}
