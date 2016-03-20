package com.zenus.gistreader.communication;

/**
 * provide asynchronous callbacks for Server REST calls
 */
public interface RestCallback<T> {
    /**
     * A callback that do nothing
     */
    public final static RestCallback EMPTY = new EmptyCallback();


    /**
     * will be called when the REST call returns successfully
     *
     * @param response the response object fro the server
     */
    public void onResponse(T response);

    /**
     * will be called in case of REST call error (Timeout, Server not reachable)
     *
     * @param error
     */
    public void onError(Object error);
}
