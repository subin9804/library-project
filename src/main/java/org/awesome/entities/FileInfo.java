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

    @Column(length = 45)
    private String extension;   // 확장자

    @Column(length = 60, nullable = false)
    private String contentType; // 파일형식

    private boolean done;    // 그룹 작업 완료 여부

    @Transient
    private String filePath;    // 파일 업로드 경로

    @Transient
    private String fileUrl;   // 파일 URL
}
