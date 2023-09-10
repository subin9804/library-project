package org.awesome.controllers.files;

import lombok.RequiredArgsConstructor;
import org.awesome.commons.JSONData;
import org.awesome.entities.FileInfo;
import org.awesome.models.file.FileInfoService;
import org.awesome.models.file.FileUploadService;
import org.awesome.repositories.FileInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService uploadService;
    private final FileInfoService infoService;
    private final FileInfoRepository fileRepository;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    /**
     * 글 수정시 파일 정보 json으로 전송
     * @return
     */
    @GetMapping("/{gid}")
    @ResponseBody
    public ResponseEntity<JSONData<List<FileInfo>>> getFiles(@PathVariable String gid) {

        List<FileInfo> files = fileRepository.findByGid(gid);

        /** 파일 url 세팅 (transient로 DB에 저장되지 않음) */
        for(FileInfo f : files) {
            String fileUrl = infoService.getFileUrl(f);
            f.setFileUrl(fileUrl);
        }

        JSONData<List<FileInfo>> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(files);

        System.out.println(data);

        return ResponseEntity.ok(data);
    }

    /**
     * 파일 업로드 처리
     * @param files
     * @param gid
     * @param location
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<JSONData<List<FileInfo>>> uploadPs (MultipartFile[] files, String gid, String location) {
        List<FileInfo> items = uploadService.upload(files, gid, location);

        JSONData<List<FileInfo>> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(items);

        return ResponseEntity.ok(data);
    }

    public String delete(@PathVariable Long id, Model model) {
//        deleteService.delete

        return "commons/execute_script";
    }

}
