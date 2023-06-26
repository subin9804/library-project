package org.awesome.models.user;

import lombok.RequiredArgsConstructor;
import org.awesome.constants.UserType;
import org.awesome.controllers.admin.users.UserListForm;
import org.awesome.entities.User;
import org.awesome.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEditService {

    private final UserRepository userRepository;
    public void edit(UserListForm userForm) {

        Long userNo = userForm.getUserNo();
        User user = userRepository.findById(userNo).orElseGet(User::new);

        System.out.println("user123" + user);
        if(user != null) {
            user.setUserNo(userNo);
            user.setUserNm(userForm.getUserNm());
            user.setUserId(userForm.getUserId());
            user.setUserPw(userForm.getUserPw());
            user.setUserType(UserType.valueOf(userForm.getUserType()));
        }

        userRepository.saveAndFlush(user);
    }

}
