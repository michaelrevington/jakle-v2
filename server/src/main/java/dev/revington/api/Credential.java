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

    private boolean changeCred(User user) {
        user.setEmail(SecurityUtil.normalizeEmail(user.getEmail()));
        try {
            user.setPassword(SecurityUtil.md5Hash(user.getPassword()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            return false;
        }

        return true;
    }

}
