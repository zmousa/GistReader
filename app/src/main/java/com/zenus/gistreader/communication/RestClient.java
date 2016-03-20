package com.zenus.gistreader.communication;

import java.util.Map;

/**
 * The Basic REST calls based on HTTPClient, each call should take a urlBuilder to the REST service and
 * a callback to be executed asynchronously when the server respond.
 */
public interface RestClient {
    /**
     * send a GET request to the server with urlBuilder parameters to be added to the generated URL
     *
     * @param url
     * @param urlParams
     * @param callback
     */
    public abstract void doGet(String url, Map urlParams, Map headers, RestCallback callback);

    /**
     * send a GET request to the server with query parameters included in URL
     *
     * @param url
     * @param callback
     */
    public abstract void doGet(String url, RestCallback callback);

    /**
     * send a POST request to the server with post parameters to be added to the POST request
     *
     * @param url
     * @param params
     * @param callback
     */
    public abstract void doPost(String url, Map params, Map headers, RestCallback callback);

    /**
     * send a POST request to the server where the json is the raw data to be sent to the server
     *
     * @param url
     * @param json json to send
     * @param callback
     */
    public abstract void doRawPost(String url, Map json, RestCallback callback);

    /**
     * send a DELETE request to the server to delete some resources
     *
     * @param url
     * @param callback
     */
    public abstract void doDelete(String url, Map params, RestCallback callback);

    /**
     * send a PUT request to the server to delete some resources
     *
     * @param url
     * @param callback
     */
    public abstract void doPut(String url, Map params, RestCallback callback);


    /**
     * This part of code is added by Maher alsafadi [maher.safadi@gmail.com]
     * The reason is to allow set custom retries number and request timeout.
     */
    public RestClient setTimeout(int timeout);

    public RestClient setMaxRetries(int maxRetries);

    public int getTimeout();

    public int getMaxRetries();
}
