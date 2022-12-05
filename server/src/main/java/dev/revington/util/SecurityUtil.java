package dev.revington.util;

import org.apache.tomcat.util.security.MD5Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

    public static String normalizeEmail(String email) {
        email = email.toLowerCase();
        int at = email.indexOf('@');
        if (at < 0)
            return null;
        return email.substring(0, at).replace(".", "").replace("+", "") + email.substring(at);
    }

    public static String md5Hash(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(text.getBytes(StandardCharsets.UTF_8));
        return new String(messageDigest.digest());
    }

}
