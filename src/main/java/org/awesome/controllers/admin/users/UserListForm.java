package org.awesome.controllers.admin.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserListForm {

    @NotNull
    private Long userNo;
    @NotBlank
    private String userNm;
    @NotBlank
    private String userId;
    @NotBlank
    private String userPw;
    @NotBlank
    private String userType;

}