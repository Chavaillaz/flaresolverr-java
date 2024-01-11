package com.chavaillaz.flaresolverr;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.concurrent.ForkJoinPool.commonPool;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonBodyPublisher {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(NON_NULL);

    public static BodyPublisher ofObject(Object jsonData) {
        return HttpRequest.BodyPublishers.ofInputStream(() -> {
            PipedInputStream in = new PipedInputStream();

            commonPool().submit(() -> {
                try (PipedOutputStream out = new PipedOutputStream(in)) {
                    OBJECT_MAPPER.writeValue(OBJECT_MAPPER.getFactory().createGenerator(out), jsonData);
                }
                return null;
            });

            return in;
        });
    }

}
