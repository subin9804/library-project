package org.awesome.commons;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.awesome.models.user.UserInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmPasswordService {

    private final HttpSession session;

    public String getpassword() {
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        String password = userInfo.getPassword();

        return password;
    }

    public boolean isCorrect(String password) {

        return false;
    }

}
