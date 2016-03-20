package com.zenus.gistreader.controller;

import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.communication.Callback;
import com.zenus.gistreader.communication.RestCallback;
import com.zenus.gistreader.communication.RestClientFactory;
import com.zenus.gistreader.communication.RestRequestBuilder;
import com.zenus.gistreader.communication.RestRouter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiController {

    public String[] gistList(final Callback callback){
        if (callback != null)
            callback.before(this);
        final String msg[] = {""};

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("access_token", GistApplication.ACCESS_TOKEN);
        String route = RestRouter.getDefault().to(RestRouter.Route.GIST_LIST);
        route = route.replace("$user$", GistApplication.GIST_USER);
        RestClientFactory.getDefault(RestRequestBuilder.ResponseType.JsonArray).doGet(route, urlParams,  new HashMap<>(), new RestCallback<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (callback != null)
                    callback.after(response);
            }

            @Override
            public void onError(Object error) {
                if (error != null) {
                    msg[0] = error.toString();
                    callback.error(error);
                }
            }
        });
        return msg;
    }

    public String[] gistById(String gistId, final Callback callback){
        if (callback != null)
            callback.before(this);
        final String msg[] = {""};

        if (gistId == null) {
            msg[0] = "Gist View Problem - ID not found";
        } else {
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("access_token", GistApplication.ACCESS_TOKEN);
            String route = RestRouter.getDefault().toId(RestRouter.Route.GIST_VIEW, gistId);
            RestClientFactory.getDefault(RestRequestBuilder.ResponseType.JsonObject).doGet(route, urlParams,  new HashMap<>(), new RestCallback<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (callback != null)
                        callback.after(response);
                }

                @Override
                public void onError(Object error) {
                    if (error != null) {
                        msg[0] = error.toString();
                        callback.error(error);
                    }
                }
            });
        }
        return msg;
    }

    public String[] starGist(String gistId, final Callback callback){
        if (callback != null)
            callback.before(this);
        final String msg[] = {""};

        if (gistId == null) {
            msg[0] = "Gist Star Problem - ID not found";
        } else {
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("access_token", GistApplication.ACCESS_TOKEN);
            String route = RestRouter.getDefault().to(RestRouter.Route.GIST_STAR);
            route = route.replace("$id$", gistId);
            RestClientFactory.getDefault(RestRequestBuilder.ResponseType.String).doPut(route, urlParams, new RestCallback<String>() {
                @Override
                public void onResponse(String response) {
                    if (callback != null)
                        callback.after(response);
                }

                @Override
                public void onError(Object error) {
                    if (error != null) {
                        msg[0] = error.toString();
                        callback.error(error);
                    }
                }
            });
        }
        return msg;
    }

    public String[] unStarGist(String gistId, final Callback callback){
        if (callback != null)
            callback.before(this);
        final String msg[] = {""};

        if (gistId == null) {
            msg[0] = "Gist UnStar Problem - ID not found";
        } else {
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("access_token", GistApplication.ACCESS_TOKEN);
            String route = RestRouter.getDefault().to(RestRouter.Route.GIST_UNSTAR);
            route = route.replace("$id$", gistId);
            RestClientFactory.getDefault(RestRequestBuilder.ResponseType.String).doDelete(route, urlParams, new RestCallback<String>() {
                @Override
                public void onResponse(String response) {
                    if (callback != null)
                        callback.after(response);
                }

                @Override
                public void onError(Object error) {
                    if (error != null) {
                        msg[0] = error.toString();
                        callback.error(error);
                    }
                }
            });
        }
        return msg;
    }

    public String[] deleteGist(String gistId, final Callback callback){
        if (callback != null)
            callback.before(this);
        final String msg[] = {""};

        if (gistId == null) {
            msg[0] = "Gist Delete Problem - ID not found";
        } else {
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("access_token", GistApplication.ACCESS_TOKEN);
            String route = RestRouter.getDefault().toId(RestRouter.Route.GIST_DELETE, gistId);
            RestClientFactory.getDefault(RestRequestBuilder.ResponseType.String).doDelete(route, urlParams, new RestCallback<String>() {
                @Override
                public void onResponse(String response) {
                    if (callback != null)
                        callback.after(response);
                }

                @Override
                public void onError(Object error) {
                    if (error != null) {
                        msg[0] = error.toString();
                        callback.error(error);
                    }
                }
            });
        }
        return msg;
    }
}
