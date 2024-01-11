package com.chavaillaz.flaresolverr;

import static java.time.Instant.ofEpochMilli;
import static java.time.OffsetDateTime.ofInstant;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class FlareSolverrResponse {

    public static final ZoneId DEFAULT_ZONE = ZoneId.of("UTC");

    private String status;
    private String message;
    private Solution solution;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private String version;

    public void setStartTimestamp(long timestamp) {
        this.start = ofInstant(ofEpochMilli(timestamp), DEFAULT_ZONE);
    }

    public void setEndTimestamp(long timestamp) {
        this.end = ofInstant(ofEpochMilli(timestamp), DEFAULT_ZONE);
    }

    @Data
    public static class Solution {

        private String url;
        private Integer status;
        private List<Cookie> cookies;
        private String userAgent;
        private Map<String, String> headers;
        private String response;

    }

    @Data
    public static class Cookie {

        private String domain;
        private OffsetDateTime expiry;
        private boolean httpOnly;
        private String name;
        private String path;
        private String sameSite;
        private boolean secure;
        private String value;

        public void setExpiry(long timestamp) {
            this.expiry = ofInstant(ofEpochMilli(timestamp), DEFAULT_ZONE);
        }

    }

}
