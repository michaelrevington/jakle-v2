package dev.revington.util;

import dev.revington.entity.Token;
import dev.revington.entity.User;
import dev.revington.variables.Parameter;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

public class TokenUtil {

    public static Token generateToken(int type, User user) throws NoSuchAlgorithmException {
        Token token = new Token();

        token.setClientId(user.getId());
        Timestamp timestamp = new Timestamp(new Date().getTime());
        token.setCreated(timestamp.getTime());

        token.setToken(SecurityUtil.sha256Hash(user.getName()) +
                SecurityUtil.sha256Hash(user.getEmail()) +
                SecurityUtil.sha256Hash(timestamp.getTime() + "") +
                SecurityUtil.sha256Hash(user.getId()));

        switch (type) {
            case Parameter.AUTH_TOKEN -> {
                token.setExpires(timestamp.getTime() + (1000L * Parameter.AUTH_TOKEN_TIMEOUT));
                token.setGrants(Parameter.GRANT_ALL);
            }
            case Parameter.ACTIVATION_TOKEN -> {
                token.setExpires(timestamp.getTime() + (1000L * Parameter.ACTIVATION_TOKEN_TIMEOUT));
                token.setGrants(Parameter.GRANT_ACTIVATION);
            }
            case Parameter.VALIDATION_TOKEN -> {
                token.setExpires(timestamp.getTime() + (1000L * Parameter.VALIDATION_TOKEN_TIMEOUT));
                token.setGrants(Parameter.GRANT_VALIDATION);
            }
        }

        return token;
    }

}
