package com.zenus.gistreader.communication;

public abstract class AbstractRestClient implements RestClient {
    protected int timeOut;
    protected int maxRetries;
    protected RestRequestBuilder.ResponseType responseType;

    public AbstractRestClient(){
        this.timeOut = 30;
        this.maxRetries = 0;
    }

    public AbstractRestClient setTimeout(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public int getTimeout() {
        return timeOut;
    }

    public AbstractRestClient setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public RestRequestBuilder.ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(RestRequestBuilder.ResponseType responseType) {
        this.responseType = responseType;
    }
}