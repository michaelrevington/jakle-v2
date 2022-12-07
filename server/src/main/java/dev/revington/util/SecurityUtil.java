package dev.revington.util;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.tomcat.util.security.MD5Encoder;

import java.net.URLEncoder;
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

    public static String sha256Hash(String text) throws NoSuchAlgorithmException {
        HashFunction hashFunction = Hashing.sha256();
        return hashFunction.hashString(text, StandardCharsets.UTF_8).toString();
    }

}
