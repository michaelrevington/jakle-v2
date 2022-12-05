package dev.revington.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Consumer;

public class CookieUtil {

    public static Cookie getCookie(HttpServletRequest req, String key) {
        Cookie[] cookies;
        if ((cookies = req.getCookies()) == null)
            return null;

        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().compareTo(key) == 0)
                return cookies[i];
        }

        return null;
    }

    public static String getCookieValue(HttpServletRequest req, String key) {
        Cookie cookie = getCookie(req, key);

        if (cookie != null)
            return new String(Base64.getUrlDecoder().decode(cookie.getValue()));

        return null;
    }

    public static void setCookie(HttpServletResponse response, String name, Consumer<Cookie> consumer) {
        Cookie cookie = new Cookie(name, "");
        consumer.accept(cookie);
        cookie.setValue(Base64.getUrlEncoder().encodeToString(cookie.getValue().getBytes(StandardCharsets.UTF_8)));
        response.addCookie(cookie);
    }

}
