package org.awesome.controllers.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.awesome.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 회원 가입 추가 유효성 검사
 *
 */
@Component
@RequiredArgsConstructor
public class UserJoinValidator implements Validator {

    @NonNull
    private UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserJoin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserJoin userJoin = (UserJoin)target;
        String userId = userJoin.getUserId();
        String userPw = userJoin.getUserPw();
        String userPwCk = userJoin.getUserPwCk();

        /** 1. 아이디 중복 여부 S */
        if (userId != null && !userId.isBlank() && repository.isUserExists(userId)) {
            errors.rejectValue("userEmail", "user.validation.exists");
        }
        /** 1. 아이디 중복 여부 E */

        /** 2. 비밀번호, 비밀번호 확인 체크 S */
        if (userPw != null && !userPw.isBlank() && userPwCk != null && !userPwCk.isBlank() && !userPw.equals(userPwCk)) {
            errors.rejectValue("userPw", "user.validation.passwordIncorrect");
        }
        /** 2. 비밀번호, 비밀번호 확인 체크 E */


    }
}
