package org.awesome.controllers.user;

import lombok.RequiredArgsConstructor;
import org.awesome.constants.RentalType;
import org.awesome.entities.FileInfo;
import org.awesome.entities.RentalBook;
import org.awesome.models.file.FileInfoService;
import org.awesome.repositories.FileInfoRepository;
import org.awesome.repositories.RentalBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
    private final RentalBookRepository bookRepository;
    private final FileInfoRepository fileRepository;
    private final FileInfoService infoService;

    @GetMapping
    public String Index(Model model) {
        List<RentalBook> books = bookRepository.findByRentalTypeOrderByRegDtDesc(RentalType.PAPER);
        List<RentalBook> ebooks = bookRepository.findByRentalTypeOrderByRegDtDesc(RentalType.EBOOK);
        // 최근 등록된 도서 6개와 gid가 일치하는 이미지파일
        if(books.size() > 6) {
            List<RentalBook> recentBooks = new ArrayList<>();
            for(int i = 0; i < 6; i++) {
                recentBooks.add(books.get(i));
            }
            books = recentBooks;
        }

        List<FileInfo> bookFiles = new ArrayList<>();
        for(RentalBook book : books) {
            List<FileInfo> files = fileRepository.findByGid(book.getGid());
            FileInfo file = files.get(0);

            String fileUrl = infoService.getFileUrl(file);
            file.setFileUrl(fileUrl);

            bookFiles.add(file);
        }

        model.addAttribute("books", books);
        model.addAttribute("bookFiles", bookFiles);

        // 최근 등록된 ebook도서 6개와 gid가 일치하는 이미지파일
        if(ebooks.size() > 6) {
            List<RentalBook> recentEbooks = new ArrayList<>();
            for(int i = 0; i < 6; i++) {
                recentEbooks.add(books.get(i));
            }
            ebooks = recentEbooks;
        }

        List<FileInfo> ebookFiles = new ArrayList<>();
        for(RentalBook ebook : ebooks) {
            List<FileInfo> files = fileRepository.findByGid(ebook.getGid());
            FileInfo file = files.get(0);

            String fileUrl = infoService.getFileUrl(file);
            file.setFileUrl(fileUrl);

            ebookFiles.add(file);
        }
        model.addAttribute("ebooks", ebooks);
        model.addAttribute("ebookFiles", ebookFiles);

        String[] addScript = { "banner" };
        model.addAttribute("addScript", addScript);
        return "front/index";
    }

}
