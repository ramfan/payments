package com.planner.payments.config.interceptors;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Configuration
public class RefreshTokenGraphQLInterceptor implements WebGraphQlInterceptor {
    @NotNull
    @Override
    public Mono<WebGraphQlResponse> intercept(@NotNull WebGraphQlRequest request, Chain chain) {
        request.configureExecutionInput((executionInput, builder) ->
                builder
                        .graphQLContext(Collections.singletonMap("cookie", request.getCookies().toSingleValueMap()))
                        .graphQLContext(Collections.singletonMap("headers", request.getHeaders().toSingleValueMap()))
                .build());

        return chain.next(request).doOnNext(response -> {
            if(request.getDocument().contains("login") || request.getDocument().contains("refreshSession")){
                String value = response.getExecutionInput().getGraphQLContext().get("refresh_token");
                Long expiry =  response.getExecutionInput().getGraphQLContext().get("refresh_token_expiry");
                if(value != null && expiry != null){
                    ResponseCookie cookie = ResponseCookie.from("refresh_token", value).httpOnly(true).maxAge(expiry).secure(true).build();
                    response.getResponseHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
                }

            }

        });
    }
}
