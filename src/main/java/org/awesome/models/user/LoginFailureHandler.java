package org.awesome.models.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.removeAttribute("requiredUserId");
        session.removeAttribute("requiredUserPw");
        session.removeAttribute("global");
        session.removeAttribute("userId");

        try {
            String userId = request.getParameter("userId");
            String userPw = request.getParameter("userPw");

            session.setAttribute("userId", userId);

            if(userId == null || userId.isBlank()) {
                throw new LoginValidationException("requiredUserId", "NotBlank.userId");
            }

            if(userPw == null || userPw.isBlank()) {
                throw new LoginValidationException("requiredUserPw", "NotBlank.userPw");
            }

            throw new LoginValidationException("global", "user.login.fail");

        } catch (LoginValidationException e) {
            String field = e.getField();
            String message = e.getMessage();
            session.setAttribute(field, message);
        }

        response.sendRedirect(request.getContextPath() + "/user/login");
    }
}