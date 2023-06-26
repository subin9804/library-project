package org.awesome.controllers.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 로그인 커맨드 객체 구현 클래스
 *
 */
@Data
public class UserLogin {

    @NotBlank(message="이메일을 입력해주세요")
    private String userId;

    @NotBlank(message="비밀번호를 입력해주세요")
    private String userPw;

    private boolean saveId;

    private String success;
}
