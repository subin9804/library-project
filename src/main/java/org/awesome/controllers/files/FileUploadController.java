package org.awesome.controllers.files;

import lombok.RequiredArgsConstructor;
import org.awesome.entities.FileInfo;
import org.awesome.models.file.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/file/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService uploadService;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @GetMapping
    public String upload() {

        return "front/file/upload";
    }

    /**
     * 파일 업로드 처리
     * @param files
     * @param gid
     * @param location
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<List<FileInfo>> uploadPs (MultipartFile[] files, String gid, String location) {
        List<FileInfo> items = uploadService.upload(files, gid, location);

        return ResponseEntity.ok(items);
    }
}
