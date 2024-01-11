package com.chavaillaz.flaresolverr;

import static com.chavaillaz.flaresolverr.JsonBodyHandler.asJson;
import static com.chavaillaz.flaresolverr.JsonBodyPublisher.ofObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FlareSolverrService {

    public static FlareSolverrResponse callFlareSolverr(FlareSolverrRequest request) throws Exception {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8191/v1"))
                .header("Content-Type", "application/json")
                .POST(ofObject(request))
                .build();

        return HttpClient.newBuilder()
                .build()
                .send(httpRequest, asJson(FlareSolverrResponse.class))
                .body();
    }

}
