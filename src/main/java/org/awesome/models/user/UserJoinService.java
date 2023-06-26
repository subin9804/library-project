package org.awesome.models.user;

import lombok.RequiredArgsConstructor;
import org.awesome.constants.UserType;
import org.awesome.controllers.user.UserJoin;
import org.awesome.entities.User;
import org.awesome.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserJoinService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public void join(UserJoin join){

        String hash = passwordEncoder.encode(join.getUserPw());

        User user = User.builder()
                .userId(join.getUserId())
                .userPw(hash)
                .userType(UserType.USER)
                .userNm(join.getUserNm())
                .build();

        repository.saveAndFlush(user);


    }
}
