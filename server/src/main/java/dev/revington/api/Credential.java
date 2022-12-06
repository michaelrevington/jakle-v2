package dev.revington.api;

import dev.revington.entity.Token;
import dev.revington.entity.User;
import dev.revington.repository.TokenRepository;
import dev.revington.repository.UserRepository;
import dev.revington.util.CookieUtil;
import dev.revington.util.SecurityUtil;
import dev.revington.util.TokenUtil;
import dev.revington.variables.Parameter;
import dev.revington.variables.StatusHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v2")
public class Credential {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private final Logger LOGGER = Logger.getLogger(Credential.class.getName());

    private boolean changeCredentials(User user) {
        if (user.getIdentity() != null) {
            if (user.getIdentity().contains("@")) {
                user.setEmail(SecurityUtil.normalizeEmail(user.getIdentity()));
            } else {
                user.setUsername(user.getIdentity());
            }
            user.setIdentity(null);
        } else {
            user.setEmail(SecurityUtil.normalizeEmail(user.getEmail()));
        }
        try {
            user.setPassword(SecurityUtil.md5Hash(user.getPassword()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            return true;
        }

        return false;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> login(HttpServletRequest req, HttpServletResponse resp,
                                            @RequestBody User user,
                                            @RequestParam(required = false, defaultValue = "false") String remember) throws IOException {
         if (user.getIdentity() == null || user.getPassword() == null) {
            resp.sendError(1026, Parameter.E1026);
            return null;
        }

         if (changeCredentials(user)) {
            resp.sendError(500);
            return null;
        }

        User client = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());
        if (client == null) {
            resp.sendError(1022, Parameter.E1022);
            return null;
        }

        if (client.getPassword().equals(user.getPassword())) {
            if (client.getValidity() == Parameter.ACCOUNT_INVALIDATED) {
                resp.sendError(1023, Parameter.E1023);
                return null;
            } else {
                if (client.getStatus() == Parameter.STATUS_DISABLED) {
                    resp.sendError(1024, Parameter.E1024);
                    return null;
                } else {
                    Token token;
                    try {
                        token = TokenUtil.generateToken(Parameter.AUTH_TOKEN, client);
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, null, e);
                        resp.sendError(500);
                        return null;
                    }
                    tokenRepository.save(token);

                    CookieUtil.setCookie(resp, Parameter.TOKEN, cookie -> {
                        cookie.setValue(token.getToken());
                        cookie.setHttpOnly(true);
                        cookie.setPath(Parameter.AUTH_TOKEN_PATH);

                        if (remember.equals("true")) {
                            cookie.setMaxAge(Parameter.AUTH_TOKEN_TIMEOUT);
                        }
                    });
                }
            }
        } else {
            if (client.getStatus() == Parameter.STATUS_ACTIVE) {
                if (client.getAttempts() >= 8) {
                    client.setStatus(Parameter.STATUS_DISABLED);
                    client.setAttempts(0);
                } else {
                    client.setAttempts(client.getAttempts() + 1);
                }
            }

            resp.sendError(1022, Parameter.E1022);
            return null;
        }

        return new ResponseEntity<>(StatusHandler.S200, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<JSONObject> signUp(HttpServletRequest req, HttpServletResponse resp,
                                             @RequestBody User user) throws IOException {
        if (user.getEmail() == null || user.getPassword() == null || user.getFirstname() == null ||
                user.getLastname() == null || user.getUsername() == null) {
            resp.sendError(1026, Parameter.E1026);
            return null;
        }

        if (changeCredentials(user)) {
            resp.sendError(500);
            return null;
        }

        User client = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());
        if (client != null) {
            if (user.getEmail() == null)
                resp.sendError(1027, Parameter.E1027);
            else
                resp.sendError(1025, Parameter.E1025);
            return null;
        }

        user.setName(user.getFirstname() + " " + user.getLastname());
        user.setFirstname(null);
        user.setLastname(null);
        user.setCreated(new Date().getTime());
        user.setValidity(Parameter.ACCOUNT_INVALIDATED);
        user.setLinks(new HashMap<>());

        userRepository.save(user);

        return new ResponseEntity<>(StatusHandler.S200, HttpStatus.OK);
    }
}
