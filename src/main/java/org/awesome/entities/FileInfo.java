package org.awesome.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name="idx_fileInfo_gid", columnList = "gid"),
        @Index(name="idx_fileInfo_gid_location", columnList = "gid, location, regDt")
})
public class FileInfo extends BaseUserEntity {
    @Id
    @GeneratedValue
    private Long fileNo;    // 실제 서버 fileNo. 확장자

    @Column(length = 60, nullable = false)
    private String gid; // 그룹id

    @Column(length = 60)
    private String location; // 그룹내 위치

    @Column(length = 100, nullable = false)
    private String fileName;    // 원본파일이름

    @Column(length = 60, nullable = false)
    private String contentType; // 파일형식

    private boolean success;    // 그룹 작업 완료 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userNo")
    private User user;  // 파일을 올린 사용자

}
