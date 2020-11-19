package com.example.hellometric.filter;

import javax.servlet.http.HttpServletRequest;

public interface RequestIdProvider {

    String requestId(HttpServletRequest request);
}
