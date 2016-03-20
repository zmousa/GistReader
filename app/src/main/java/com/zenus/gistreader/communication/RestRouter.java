package com.zenus.gistreader.communication;

public class RestRouter {
    private static final String BASE_URL = "https://api.github.com/";

    private StringBuilder urlBuilder;

    public enum Route {
        GIST_LIST("users/$user$/gists"),
        GIST_VIEW("gists/"),
        GIST_STAR("gists/$id$/star"),
        GIST_UNSTAR("gists/$id$/star"),
        GIST_DELETE("gists/");

        private String route;

        public String getRoute() {
            return route;
        }

        private Route(String route) {
            this.route = route;
        }
    }

    private RestRouter() {
        urlBuilder = new StringBuilder(BASE_URL);
    }

    public static RestRouter getDefault() {
        return new RestRouter();
    }

    public final String to(Route to) {
        urlBuilder.append(to.route);
        return urlBuilder.toString();
    }

    public final String toId(Route to, String id) {
        urlBuilder.append(to.route).append(id);
        return urlBuilder.toString();
    }
}
