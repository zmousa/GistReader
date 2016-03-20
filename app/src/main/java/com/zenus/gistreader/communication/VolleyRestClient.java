package com.zenus.gistreader.communication;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.util.Logger;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyRestClient extends AbstractRestClient {

    public VolleyRestClient(RestRequestBuilder.ResponseType responseType){
        this.responseType = responseType;
    }

    @Override
    public void doGet(String url, Map urlParams, Map headers, final RestCallback callback) {
        Logger.log("VolleyRestClient.doGet", url);
        Request request = new RestRequestBuilder(RestRequestBuilder.RequestType.GET, url)
                .setCallback(new RestCallback() {
                    @Override
                    public void onResponse(Object response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Object error) {
                        callback.onError(error);
                    }
                })
                .setURLParams(urlParams)
                .setHeaders(headers)
                .setResponseType(responseType)
                .build();
        request = this.setRetryPolicy(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        GistApplication.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void doGet(String url, RestCallback callback) {
        doGet(url, new HashMap(), new HashMap(), callback);
    }

    @Override
    public void doPost(String url, Map params, Map headers, final RestCallback callback) {
        Logger.log("VolleyRestClient.doPost", url);
        Request request = new RestRequestBuilder(RestRequestBuilder.RequestType.POST, url)
                .setCallback(new RestCallback() {
                    @Override
                    public void onResponse(Object response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Object error) {
                        callback.onError(error);
                    }
                }).setPostParams(params)
                .setHeaders(headers)
                .setResponseType(responseType)
                .build();

        request = this.setRetryPolicy(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        GistApplication.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void doRawPost(String url, Map json, final RestCallback callback) {
        //use logger!.
        Logger.log("VolleyRestClient.doGet", url);
        Request request = new RestRequestBuilder(RestRequestBuilder.RequestType.POST_BODY, url)
                .setCallback(new RestCallback() {
                    @Override
                    public void onResponse(Object response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Object error) {
                        callback.onError(error);
                    }
                })
                .setHeaders(new HashMap<String, String>() {{
                    put("Content-Type", "application/json");
                }})
                .setPostBody(new JSONObject(json).toString())
                .setResponseType(responseType)
                .build();
        request = this.setRetryPolicy(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        GistApplication.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void doDelete(String url, Map params, final RestCallback callback) {
        //use logger!.
        Logger.log("VolleyRestClient.doDelete", url);
        Request request = new RestRequestBuilder(RestRequestBuilder.RequestType.DELETE, url)
                .setCallback(new RestCallback() {
                    @Override
                    public void onResponse(Object response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Object error) {
                        callback.onError(error);
                    }
                })
                .setURLParams(params)
                .setResponseType(responseType)
                .build();
        request = this.setRetryPolicy(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        GistApplication.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void doPut(String url, Map params, final RestCallback callback) {
        Logger.log("VolleyRestClient.doPut", url);
        Request request = new RestRequestBuilder(RestRequestBuilder.RequestType.PUT, url)
                .setCallback(new RestCallback() {
                    @Override
                    public void onResponse(Object response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Object error) {
                        callback.onError(error);
                    }
                })
                .setURLParams(params)
                .setResponseType(responseType)
                .build();
        request = this.setRetryPolicy(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        GistApplication.getInstance().getRequestQueue().add(request);
    }

    private Request setRetryPolicy(Request jr) {
        boolean ok = false;
        if (this.getTimeout() > 0) {
            ok = true;
        }

        if (this.getMaxRetries() > 0) {
            ok = true;
        }
        if (ok) {
            //jr.setRetryPolicy((RetryPolicy) rp);
            jr.setRetryPolicy(new DefaultRetryPolicy(this.getTimeout(),
                    this.getMaxRetries(),
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

        return jr;
    }
}
