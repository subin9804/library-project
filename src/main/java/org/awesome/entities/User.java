package org.awesome.entities;

import jakarta.persistence.*;
import lombok.*;
import org.awesome.constants.UserType;

import java.util.List;

@Data @Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue
    private Long userNo;

    @Column(length=45, nullable = false, unique=true)
    private String userId;
    @Column(length=65, nullable = false)
    private String userPw;
    @Column(length=45, nullable = false)
    private String userNm;
    @Column(length = 70, nullable = false)
    private String photo;

    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private List<Rental> rental;

}
