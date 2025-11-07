package com.example.smartspend.config;

import com.example.smartspend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService authService;
    public AuthInterceptor(AuthService authService){ this.authService = authService; }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (path.startsWith("/api/expenses")) {
            String token = request.getHeader("X-AUTH-TOKEN");
            if (token == null) {
                response.setStatus(401);
                response.getWriter().write("{\"message\":\"Missing token\"}");
                return false;
            }
            Long uid = authService.userIdFromToken(token);
            if (uid == null) {
                response.setStatus(401);
                response.getWriter().write("{\"message\":\"Invalid token\"}");
                return false;
            }
            // set the AuthPrincipal (public class in AuthPrincipal.java)
            request.setAttribute("authPrincipal", new com.example.smartspend.config.AuthPrincipal(uid));
        }
        return true;
    }
}