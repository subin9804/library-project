package org.awesome.controllers.user;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.awesome.entities.User;
import org.modelmapper.ModelMapper;

/**
 * 회원 가입 커맨드 객체 생성 클래스
 *
 */
@Data
public class UserJoin {

    @NotBlank
    @Size(min=6)
    private String userId; // 아이디

    @NotBlank
    @Size(min=8)
    private String userPw; // 비밀번호

    @NotBlank
    private String userPwCk; // 비밀번호 확인


    @NotBlank
    private String userNm; // 회원명


    @AssertTrue
    private boolean termsAgree; // 회원가입 약관 동의

    // User 엔티티로 변환
    public static User of(UserJoin userJoin) {

        return new ModelMapper().map(userJoin, User.class);
    }
}
