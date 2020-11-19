package com.example.hellometric.filter;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Component
public class HeaderRequestIdProvider implements RequestIdProvider {
    public static final String X_REQUEST_ID_HEADER = "x-request-id";

    @Override
    public String requestId(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(X_REQUEST_ID_HEADER)).orElseGet(() -> UUID.randomUUID().toString());
    }
}
