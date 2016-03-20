package com.zenus.gistreader.communication;


import com.zenus.gistreader.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An abstract implementation of {@link RestCallback} that processes the response
 * as a {@link JSONObject}, An info method to check whether the request succeeded or not.
 */
public abstract class JSONObjectRestCallback implements RestCallback<JSONObject> {

    private static final String SUCCESS = "SUCCESS";

    /**
     * check the response of the server is the rest succeeded!.
     *
     * @param response
     * @return
     */
    public boolean isSuccess(JSONObject response) {
        Logger.log("JSONCallback", String.valueOf(response));
        try {
            return response.has("status") && (response.getString("status").equalsIgnoreCase("ok") ||
                    response.getString("status").equalsIgnoreCase(SUCCESS));
        } catch (JSONException e) {
        }
        return false;
    }
}
