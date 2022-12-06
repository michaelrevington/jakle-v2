package dev.revington.api;

import dev.revington.entity.Token;
import dev.revington.entity.User;
import dev.revington.repository.TokenRepository;
import dev.revington.repository.UserRepository;
import dev.revington.util.CookieUtil;
import dev.revington.variables.Parameter;
import dev.revington.variables.StatusHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/account")
public class Account {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @DeleteMapping("/logout")
    public ResponseEntity<JSONObject> logOut(HttpServletRequest req, HttpServletResponse resp) {
        tokenRepository.deleteById(req.getAttribute(Parameter.TOKEN_ID).toString());

        CookieUtil.setCookie(resp, Parameter.TOKEN, cookie -> {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
        });

        return new ResponseEntity<>(StatusHandler.S200, HttpStatus.OK);
    }

}
