package com.example.jobfinder.security.filters;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.security.jwt.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;
    private static final Logger LOGGER = LoggerFactory.getLogger("info");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            if (isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring(7);
            String email = getEmailFromToken(authorizationHeader, token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(email);

                if (jwtTokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            sendUnauthorizedResponse(response);
            return;
        } catch (Exception e) {
            sendUnauthorizedResponse(response);
            return;
        }
    }

    private String getEmailFromToken(String tokenType, String token) {

        if (token != null && tokenType.startsWith("Bearer")) {
            return jwtTokenUtils.extractEmail(token);
        }
        return null;
    }


    private boolean isByPassToken(HttpServletRequest request) {
        LOGGER.info("By pass token: " + request.getServletPath());
        List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of(ApiURL.USER + "/login", "POST"),
                Pair.of(ApiURL.AUTH + "/register", "POST"),
                Pair.of(ApiURL.USER + "/forget-password", "POST"),
                Pair.of(ApiURL.USER + "/active-forget-password", "GET"),
                Pair.of(ApiURL.CANDIDATE + "/active-account", "POST"),
                Pair.of(ApiURL.CANDIDATE + "/active", "GET"),
                Pair.of(ApiURL.USER + "/reset-password-by-token", "POST"),
                Pair.of(ApiURL.MAJOR + "", "GET"),
                Pair.of(ApiURL.SCHEDULE + "", "GET"),
                Pair.of(ApiURL.POSITION + "", "GET"),
                Pair.of(ApiURL.HR + "", "POST"),
                Pair.of(ApiURL.HR + "", "GET"),
                Pair.of("^" + ApiURL.JOB + "/\\d+$", "GET"),
                Pair.of("^" + ApiURL.JOB + "/active", "GET"),
                Pair.of("^" + ApiURL.COMPANY + "/\\d+$", "GET"),
                Pair.of("^" + ApiURL.COMPANY + "/\\d+$", "DELETE"),
                Pair.of("^" + ApiURL.COMPANY + "/name/[\\s\\S]*$", "GET"),
                Pair.of(ApiURL.COMPANY + "", "POST"),
                Pair.of(ApiURL.COMPANY + "", "GET")
        );


        return byPassTokens.stream()
                .anyMatch(token -> request.getServletPath().matches(token.getFirst()) &&
                        request.getMethod().equals(token.getSecond()));
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}









