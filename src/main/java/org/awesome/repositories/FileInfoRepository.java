package org.awesome.repositories;

import org.awesome.entities.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, QuerydslPredicateExecutor<FileInfo> {
    List<FileInfo> findByGidOrderByRegDtAsc(String gid);
    List<FileInfo> findByGidAndLocationOrderByRegDtAsc(String gid, String location);
}
