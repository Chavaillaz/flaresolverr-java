package com.chavaillaz.flaresolverr;

import java.util.List;

import com.chavaillaz.flaresolverr.FlareSolverrResponse.Cookie;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class FlareSolverrRequest {

    private final String cmd;

    @Getter
    @Setter
    public static class FlareSolverrSessionRequest extends FlareSolverrRequest {

        /**
         * Optional. Will send the request from and existing browser instance.
         * If one is not sent it will create a temporary instance that will be destroyed immediately after the request is completed.
         */
        private String session;

        public FlareSolverrSessionRequest(String command) {
            super(command);
        }
    }

    @Getter
    @Setter
    public static class FlareSolverrGetRequest extends FlareSolverrSessionRequest {

        private final String url;

        /**
         * FlareSolverr will automatically rotate expired sessions based on the TTL provided in minutes.
         */
        @JsonProperty("session_ttl_minutes")
        private String sessionTimeToLive;

        /**
         * Max timeout to solve the challenge in milliseconds.
         * Default value 60000.
         */
        private Long maxTimeout;

        /**
         * Will be used by the headless browser.
         */
        private List<Cookie> cookies;

        /**
         * Only returns the cookies. Response data, headers and other parts of the response are removed.
         * Default value false.
         */
        private Boolean returnOnlyCookies;

        /**
         * You must include the proxy schema in the URL.
         * Authorization (username/password) is not supported.
         * Note that when the session parameter is set, the proxy is ignored;
         * a session specific proxy can be set in session creation request.
         */
        private String proxy;

        public FlareSolverrGetRequest(String command, String url) {
            super(command);
            this.url = url;
        }

    }

    @Getter
    @Setter
    public static class FlareSolverrPostRequest extends FlareSolverrGetRequest {

        /**
         * Must be a string with {@code application/x-www-form-urlencoded} (e.g. {@code a=b&c=d})
         */
        private String postData;

        public FlareSolverrPostRequest(String command, String url) {
            super(command, url);
        }

    }

    public static FlareSolverrRequest createSession() {
        return new FlareSolverrSessionRequest("sessions.create");
    }

    public static FlareSolverrRequest deleteSession() {
        return new FlareSolverrSessionRequest("sessions.destroy");
    }

    public static FlareSolverrRequest getRequest(String url) {
        return new FlareSolverrGetRequest("request.get", url);
    }

    public static FlareSolverrRequest postRequest(String url) {
        return new FlareSolverrPostRequest("request.post", url);
    }

}
