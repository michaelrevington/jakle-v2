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

import java.io.IOException;
import java.util.HashMap;

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

    @PostMapping("/update")
    public ResponseEntity<JSONObject> update(HttpServletRequest request, HttpServletResponse response,
                                             @RequestBody User user) {
        User client = userRepository.findById(request.getAttribute(Parameter.CLIENT_ID).toString()).get();

        if (user.getName() != null) {
            client.setName(user.getName());
        }

        if (user.getDateOfBirth() != null) {
            client.setDateOfBirth(user.getDateOfBirth());
        }

        if (user.getBio() != null) {
            client.setBio(user.getBio());
        }

        if (user.getLinks() != null) {
            HashMap<String, String> links = client.getLinks();
            user.getLinks().forEach((key, val) -> {
                if (links.containsKey(key))
                    links.replace(key, val);
                else
                    links.put(key, val);
            });
        }

        userRepository.save(client);

        return new ResponseEntity<>(StatusHandler.S200, HttpStatus.OK);
    }

    @PostMapping("/username")
    public ResponseEntity<JSONObject> updateUsername(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestBody User user) throws IOException {
        User client = userRepository.findByUsername(user.getUsername());
        if (client != null) {
            response.sendError(1027, Parameter.E1027);
            return null;
        }

        client = userRepository.findById(request.getAttribute(Parameter.CLIENT_ID).toString()).get();

        client.setUsername(user.getUsername());
        userRepository.save(client);

        return new ResponseEntity<>(StatusHandler.S200, HttpStatus.OK);
    }

}
