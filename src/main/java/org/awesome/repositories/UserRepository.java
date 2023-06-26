package org.awesome.repositories;

import com.querydsl.core.BooleanBuilder;
import org.awesome.constants.UserType;
import org.awesome.controllers.admin.users.UserSearch;
import org.awesome.entities.QUser;
import org.awesome.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor {
    User findByUserId(String userId);

    default Page<User> getUsers(UserSearch userSearch) {
        /** 페이징 처리 s */
        int page = userSearch.getPage();
        page = page < 1 ? 1 : page;
        int limit = userSearch.getLimit();
        limit = limit < 1 ? 20 : limit;

        Pageable pageable = PageRequest.of(page - 1, limit);
        /** 페이징 처리 E */

        /** 검색조건 처리 S */
        BooleanBuilder builder = new BooleanBuilder();
        QUser user = QUser.user;
        String userId = userSearch.getUserId();
        String[] userType = userSearch.getUserType();
        LocalDateTime sdate = userSearch.getSdate();
        LocalDateTime edate = userSearch.getEdate();

        if(userId != null && !userId.isBlank()) {
            builder.and(user.userId.contains(userId));
        }

        if(userType != null && userType.length > 0) {
            List<UserType> types = Arrays.stream(userType).map(UserType::valueOf).toList();
            builder.and(user.userType.in(types));
        }

        if(sdate != null && sdate.isBefore(edate) && edate != null && !sdate.isAfter(edate)) {
            builder.and(user.regDt.after(sdate))
                    .and(user.regDt.before(edate));
        }
        /** 검색 조건 처리 E */

        Page<User> data = findAll(builder, pageable);

        return data;
    }

    default boolean isUserExists(String userId) {
        BooleanBuilder builder = new BooleanBuilder();
        QUser user = QUser.user;
        builder.and(user.userId.eq(userId));
        long cnt = count(builder);
        return cnt > 0;
    }

}