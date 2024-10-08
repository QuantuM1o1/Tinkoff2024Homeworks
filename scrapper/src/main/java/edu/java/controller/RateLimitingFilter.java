package edu.java.controller;

import dto.ApiErrorResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import static io.netty.handler.codec.http.HttpResponseStatus.TOO_MANY_REQUESTS;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    private final int numberOfTokens = 10;
    private final Map<String, Bucket> ipBuckets = new ConcurrentHashMap<>();

    private final Bandwidth limit = Bandwidth.builder()
        .capacity(numberOfTokens)
        .refillIntervallyAlignedWithAdaptiveInitialTokens(numberOfTokens, Duration.ofMinutes(1), Instant.now())
        .build();

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String ipAddress = request.getRemoteAddr();
        Bucket bucket = ipBuckets.computeIfAbsent(ipAddress, k -> Bucket.builder().addLimit(limit).build());
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(TOO_MANY_REQUESTS.code());
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                "Too many requests",
                "429",
                "Too Many Requests",
                "You're sending too many requests",
                new ArrayList<>()
            );
            response.getWriter().write(errorResponse.toString());
            response.getWriter().flush();
        }
    }
}
