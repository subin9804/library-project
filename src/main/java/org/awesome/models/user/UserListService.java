package org.awesome.models.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.awesome.controllers.admin.users.UserSearch;
import org.awesome.entities.User;
import org.awesome.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userList")
@RequiredArgsConstructor
public class UserListService {

    private final EntityManager em;
    private final UserRepository userRepository;

    private Page<User> data;

    public UserListService getUserList(UserSearch userSearch) {
        data = userRepository.getUsers(userSearch);

        return this;
    }

    public List<User> toList() {
        List<User> users = data.getContent();
        users.stream().forEach(em::detach);     // 영속성 분리

        return users;
    }

    public Page<User> getPage() {
        return data;
    }
}