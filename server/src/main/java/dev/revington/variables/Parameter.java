package dev.revington.variables;

public class Parameter {

    public static final String E1021 = "Unauthorized Access";
    public static final String E1022 = "Credential Mismatch";
    public static final String E1023 = "Account Not Verified";
    public static final String E1024 = "Account Disabled";
    public static final String E1025 = "Account Already Exists";
    public static final String E1026 = "One or more fields are empty";

    public static final String S200 = "Query Successful";

    public static final String TOKEN = "jk_auth";
    public static final String TOKEN_ID = "jk_auth_id";
    public static final String CLIENT_ID = "client_id";

    public static final String STATUS_CODE = "code";
    public static final String STATUS_MESSAGE = "query";

    public static final int ACCOUNT_VALIDATED = 10;
    public static final int ACCOUNT_INVALIDATED = 11;

    public static final int STATUS_ACTIVE = 50;
    public static final int STATUS_DISABLED = 51;

    public static final int AUTH_TOKEN_TIMEOUT = 60 * 60 * 24 * 365;
    public static final int VALIDATION_TOKEN_TIMEOUT = 60 * 60 * 6;
    public static final int ACTIVATION_TOKEN_TIMEOUT = 60 * 60;

    public static final int GRANT_ALL = 98;
    public static final int GRANT_VALIDATION = 99;
    public static final int GRANT_ACTIVATION = 100;

    public static final int AUTH_TOKEN = 65;
    public static final int ACTIVATION_TOKEN = 66;
    public static final int VALIDATION_TOKEN = 67;

    public static final String AUTH_TOKEN_PATH = "/";

}
