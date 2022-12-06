package dev.revington.controller;

import dev.revington.variables.Parameter;
import dev.revington.variables.StatusHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Enumeration;

@Controller
public class Error implements ErrorController {

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> error(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Throwable throwable = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        int error = (int) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String message;
        if (throwable != null)
            message = throwable.getMessage();
        else
            message = req.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();

        if (error == 404)
            resp.sendRedirect("/app/_error");

        model.addAttribute(Parameter.STATUS_CODE, error);
        model.addAttribute(Parameter.STATUS_MESSAGE, message);

        return new ResponseEntity<>(StatusHandler.createStatus(error, message), HttpStatus.OK);
    }



}
