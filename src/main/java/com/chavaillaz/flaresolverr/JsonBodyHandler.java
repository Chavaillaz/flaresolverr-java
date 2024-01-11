package com.chavaillaz.flaresolverr;

import static java.net.http.HttpResponse.BodySubscribers.mapping;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBodyHandler<W> implements HttpResponse.BodyHandler<W> {

    private final Class<W> type;

    public JsonBodyHandler(Class<W> type) {
        this.type = type;
    }

    @Override
    public BodySubscriber<W> apply(ResponseInfo responseInfo) {
        BodySubscriber<String> upstream = BodySubscribers.ofString(UTF_8);
        return mapping(upstream, this::convertModel);
    }

    private W convertModel(String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(body, this.type);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static <T> JsonBodyHandler<T> asJson(Class<T> type) {
        return new JsonBodyHandler<>(type);
    }

}