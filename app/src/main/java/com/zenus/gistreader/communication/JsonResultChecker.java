package com.zenus.gistreader.communication;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonResultChecker {
    /**
     *
     * @param data represent json data
     * @return the error message or empty in case no error found
     */
    public static String checkResultContainsData(JSONObject data){
        String result = null;
        try {
            result = data.getString("hasError");
            if (result != null && result.equals("true")){
                String message = data.getString("message");
                if (message != null && !message.isEmpty())
                    return message;
            }
        } catch (JSONException e) {

        }
        return "";
    }
}
