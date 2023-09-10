package org.awesome.models.file;

import lombok.RequiredArgsConstructor;
import org.awesome.entities.FileInfo;
import org.awesome.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileInfoRepository infoRepository;
    private final FileInfoService infoService;

//    public FileInfo upload(MultipartFile file, String gid, String location) {
//        List<FileInfo> items = upload(new MultipartFile[] {file
//          }, gid, location);
//        return items.size() > 0 ? items.get(0) : null;
//    }

    public List<FileInfo> upload(MultipartFile[] files) {
        return upload(files, null);
    }

    public List<FileInfo> upload(MultipartFile[] files, String gid) {
        return upload(files, gid, null);
    }

    public List<FileInfo> upload(MultipartFile[] files, String gid, String location) {
        // gid(그룹ID)가 없는 경우 자동 생성
        gid = gid == null || gid.isBlank() ? UUID.randomUUID().toString() : gid;

        /** 파일 정보 저장 처리 */
        List<FileInfo> items = new ArrayList<>();

        for(MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String fileType = file.getContentType();
            String extension = fileName.substring(fileName.lastIndexOf(".") +1);
            FileInfo item = FileInfo.builder()
                    .fileName(fileName)
                    .contentType(fileType)
                    .extension(extension)
                    .gid(gid)
                    .location(location)
                    .build();

            infoRepository.saveAndFlush(item);
            infoService.addFileInfo(item); // 파일 경로, 파일 URL 등의 추가 정보

            /** 파일 업로드 처리 */
            try {
                System.out.println("item: " + item);
                File _file = new File(item.getFilePath());
                file.transferTo(_file);

                items.add(item);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return items;
    }

}
