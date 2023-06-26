package org.awesome.controllers.admin.users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.awesome.constants.UserType;

import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserSearch {

    private int page = 1;
    private int limit = 20;

    private String userId;

    private String[] userType;

    private LocalDateTime sdate;
    private LocalDateTime edate;
}