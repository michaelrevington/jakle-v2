package dev.revington.variables;

import net.minidev.json.JSONObject;

public class StatusHandler {

    public static final JSONObject S200 = createStatus(200, Parameter.S200);

    public static final JSONObject E1022 = createStatus(1022, Parameter.E1022);
    public static final JSONObject E1023 = createStatus(1023, Parameter.E1023);
    public static final JSONObject E1024 = createStatus(1024, Parameter.E1024);
    public static final JSONObject E1025 = createStatus(1025, Parameter.E1025);

    public static final JSONObject createStatus(int code, String query) {
        return new JSONObject()
                .appendField(Parameter.STATUS_CODE, code)
                .appendField(Parameter.STATUS_MESSAGE, query);
    }

}
