package dev.kangoo.pirullometro.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        String requestURI = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");
        String xForwardedFor = request.getHeader("X-Forwarded-For");

        logger.info("Request from IP: " + request.getRemoteAddr()
                + " | Request URI: " + requestURI
                + " | User-Agent: " + userAgent
                + " | Referer: " + referer
                + " | X-Forwarded-For: " + xForwardedFor);

        filterChain.doFilter(request, response);
    }
}
