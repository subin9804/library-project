package org.awesome.models.user;

import org.awesome.entities.User;
import org.awesome.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserId(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        // 사용자 권한
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getUserType().toString()));

        return UserInfo.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userNm(user.getUserNm())
                .userPw(user.getUserPw())
                .authorities(authorities) // 사용자 권한 설정
                .build();
    }
}
