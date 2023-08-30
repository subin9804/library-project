package org.awesome.controllers.files;

import lombok.RequiredArgsConstructor;
import org.awesome.entities.FileInfo;
import org.awesome.models.file.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    @PostMapping
    @ResponseBody
    public void uploadPs(@RequestParam("file") MultipartFile[] files, String gid, String location) {

        for(MultipartFile file : files) {
            FileInfo info = uploadService.upload(file, gid, location);
            if(info == null) {
                continue;
            }

            String uploadPath = fileUploadPath + info.getFileNo();

            try {
                file.transferTo(new File(uploadPath));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
