package com.sentinelmind.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            response.sendRedirect("/dashboard");
        } else if (role.equals("ROLE_DOCTOR")) {
            response.sendRedirect("/department/ER"); // can be dynamic later
        } else if (role.equals("ROLE_ANALYST")) {
            response.sendRedirect("/risk-trends");
        }
    }
}
