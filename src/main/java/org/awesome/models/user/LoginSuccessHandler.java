package org.awesome.models.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.removeAttribute("requiredUserId");
        session.removeAttribute("requiredUserPw");
        session.removeAttribute("global");
        session.removeAttribute("userId");

        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        session.setAttribute("userInfo", userInfo);

        response.sendRedirect(request.getContextPath() + "/index");
    }
}
