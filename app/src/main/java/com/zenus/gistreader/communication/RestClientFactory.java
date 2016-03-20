package com.zenus.gistreader.communication;

public class RestClientFactory {
    /**
     * get default rest client
     *
     * @return
     */
    public static AbstractRestClient getDefault(RestRequestBuilder.ResponseType responseType) {
        return new VolleyRestClient(responseType);
    }

    public static AbstractRestClient getClient(Class<? extends AbstractRestClient> _) {
        AbstractRestClient client = null;
        try {
            client = _.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return new VolleyRestClient(RestRequestBuilder.ResponseType.JsonObject);
        } catch (IllegalAccessException e){
            e.printStackTrace();
            return new VolleyRestClient(RestRequestBuilder.ResponseType.JsonObject);
        }
        return client;
    }
}
