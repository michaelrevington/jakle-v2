package dev.revington.api;

import dev.revington.entity.User;
import dev.revington.repository.MessageRepository;
import dev.revington.repository.UserRepository;
import dev.revington.variables.Parameter;
import dev.revington.variables.StatusHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v2/social")
public class Social {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @DeleteMapping("/delete/messages")
    public ResponseEntity<JSONObject> deleteViewedMessage(HttpServletRequest request, HttpServletResponse response,
                                                          @RequestParam String username) {
        User user = userRepository.findById(request.getAttribute(Parameter.CLIENT_ID).toString()).get();

        messageRepository.deleteByUsername(user.getId(), username);

        return new ResponseEntity<>(StatusHandler.S200, HttpStatus.OK);
    }

}
