package org.awesome.models.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.awesome.entities.FileInfo;
import org.awesome.repositories.FileInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileInfoService {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.url}")
    private String uploadUrl;

    private final HttpServletRequest request;
    private final FileInfoRepository infoRepository;

    public FileInfo get(Long id) {
        FileInfo item = infoRepository.findById(id).orElseThrow(FileNotFoundException::new);

        addFileInfo(item);

        return item;
    }

    public List<FileInfo> getList(Options opts) {
        List<FileInfo> items = infoRepository.getFiles(opts.getGid(), opts.getLocation(), opts.getMode().name());

        items.stream().forEach(this::addFileInfo);
        return items;
    }

    public List<FileInfo> getListAll(String gid, String location) {
        Options opts = Options.builder()
                .gid(gid)
                .location(location)
                .mode(SearchMode.ALL)
                .build();
        return getList(opts);
    }

    public List<FileInfo> getListAll(String gid) {
        return getListAll(gid, null);
    }

    public List<FileInfo> getListDone(String gid, String location) {
        Options opts = Options.builder()
                .gid(gid)
                .location(location)
                .mode(SearchMode.DONE)
                .build();
        return getList(opts);
    }

    public List<FileInfo> getListDone(String gid) {
        return getListDone(gid, null);
    }

    /**
     * 파일 업로드 서버 경로 및 파일 서버 접속 URL 데이터 생성
     * @param item
     */
    public void addFileInfo(FileInfo item) {
        long id = item.getFileNo();
        String extension = item.getExtension();
        String fileName = getFileName(id, extension);
        long folder = id % 10L;

        // 파일 서버 업로드 경로
        String fileDir = uploadPath + folder;
        String filePath = fileDir + "/" + fileName;
        File _fileDir = new File(fileDir);
        if(!_fileDir.exists()) {
            _fileDir.mkdir();
        }

        // 파일 서버 접속 URL(fileUrl)
        String fileUrl = request.getContextPath() + uploadUrl + folder + "/" + fileName;

    }

    private String getFileName(long fileNo, String extension) {
        return extension == null || extension.isBlank() ? "" + fileNo : fileNo + "." + extension;
    }

    @Data @Builder
    static class Options {
        private String gid;
        private String location;
        private SearchMode mode = SearchMode.ALL;
    }

    static enum SearchMode {
        ALL,
        DONE,
        UNDONE
    }

}
