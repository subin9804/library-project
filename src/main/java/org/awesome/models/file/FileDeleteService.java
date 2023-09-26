package org.awesome.models.file;

import lombok.RequiredArgsConstructor;
import org.awesome.entities.FileInfo;
import org.awesome.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final FileInfoService infoService;
    private final FileInfoRepository repository;

    public void delete(Long id) {
        FileInfo item = infoService.get(id);

        /**
         * 1. 파일 삭제
         * 2. 파일 정보 삭제
         */
        File file = new File(item.getFilePath());
        if(file.exists()) file.delete();

        repository.delete(item);
        repository.flush();
    }
}
