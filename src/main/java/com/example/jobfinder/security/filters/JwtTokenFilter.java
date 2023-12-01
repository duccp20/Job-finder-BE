package com.example.jobfinder.security.filters;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.security.jwt.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    private String getEmailFromToken(String token, String tokenType) {

        if (tokenType != null && tokenType.startsWith("Bearer")) {
            return jwtTokenUtils.extractEmail(token);
        }
        return null;
    }


    private boolean isByPassToken(HttpServletRequest request) {
        List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of(ApiURL.USER + "/login", "POST"),
                Pair.of(ApiURL.AUTH + "/register", "POST")
        );

        return byPassTokens.stream()
                .anyMatch(token -> request.getServletPath().contains(token.getFirst()) &&
                        request.getMethod().equals(token.getSecond()));
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}









