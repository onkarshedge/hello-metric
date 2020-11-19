package com.example.hellometric.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(0)
public class LoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    private final RequestIdProvider requestIdProvider;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    public LoggingFilter(RequestIdProvider requestIdProvider) {
        this.requestIdProvider = requestIdProvider;
    }

    private boolean skipLogging(String path) {
        return path.equals("/metrics") || path.equals("/") || pathMatcher.match("/actuator", path) || pathMatcher.match("/actuator/*", path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrapper = new ContentCachingRequestWrapper(request);
        String requestId = requestIdProvider.requestId(request);

        MDC.put("request-id", requestId);
        MDC.put("method", request.getMethod());
        MDC.put("path", request.getRequestURI());
        MDC.put("address", request.getRemoteAddr());
        MDC.put("referer", request.getHeader("referer"));

        if (skipLogging(request.getRequestURI())) {
            filterChain.doFilter(wrapper, response);
            return;
        }

        long startTime = System.currentTimeMillis();

        filterChain.doFilter(wrapper, response);

        MDC.put("status-code", String.valueOf(response.getStatus()));
        long responseTime = System.currentTimeMillis() - startTime;
        MDC.put("response-time", String.format("%dms", responseTime));
        log.info("request completed successfully");
        MDC.clear();
    }
}
