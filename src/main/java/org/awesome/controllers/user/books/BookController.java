package org.awesome.controllers.user.books;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.awesome.commons.Pagination;
import org.awesome.constants.RentalStatus;
import org.awesome.controllers.admin.books.BookSearch;
import org.awesome.entities.FileInfo;
import org.awesome.entities.Rental;
import org.awesome.entities.RentalBook;
import org.awesome.models.book.BookListService;
import org.awesome.models.file.FileInfoService;
import org.awesome.models.rental.RentalService;
import org.awesome.repositories.FileInfoRepository;
import org.awesome.repositories.RentalBookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    @Value("${file.upload.url}")
    private String uploadUrl;
    private final RentalBookRepository bookRepository;
    private final RentalService rentalService;
    private final HttpServletRequest request;
    private final FileInfoRepository fileRepository;
    private final BookListService listService;
    private final FileInfoService infoService;

    // 도서 목록
    @GetMapping
    public String index(BookSearch bookSearch, Model model, HttpServletRequest request) {

        List<RentalBook> books = listService.gets(bookSearch).toList();
        List<FileInfo> files = new ArrayList<>();

        /** 파일 url 세팅 (transient로 DB에 저장되지 않음) */
        for(RentalBook book : books) {
            List<FileInfo> fileGroup = fileRepository.findByGidOrderByRegDtAsc(book.getGid());
            FileInfo file = fileGroup.get(0);
            String fileUrl = infoService.getFileUrl(file);
            file.setFileUrl(fileUrl);
            files.add(file);
        }

        Page<RentalBook> page = listService.getPage();

        String url = request.getContextPath() + "/book";
        Pagination<RentalBook> pagination = new Pagination<>(page, url);
        model.addAttribute("bookSearch", bookSearch);
        model.addAttribute("files", files);
        model.addAttribute("pagination", pagination);
        model.addAttribute("books", books);
        return "front/books/index";
    }

    // 상세페이지
    @GetMapping("/{bookId}")
    public String index(@PathVariable(name="bookId") String bookId, Model model) {
        RentalBook book = bookRepository.findById(bookId).orElse(null);
        List<FileInfo> files = fileRepository.findByGidOrderByRegDtAsc(book.getGid());

        /** 파일 url 세팅 (transient로 DB에 저장되지 않음) */
        for(FileInfo f : files) {
            String fileUrl = infoService.getFileUrl(f);
            f.setFileUrl(fileUrl);
        }

        // 도서가 대여중일 경우 대여한 회원정보와 대여번호를 가져옴
        if(book.getStatus() == RentalStatus.RENT) {
            Rental rental = rentalService.getRentalInfo(bookId);
            if (rental != null) {
                String userId = rental.getUser().getUserId();
                Long rentalNo = rental.getRentalNo();

                model.addAttribute("userId", userId);
                model.addAttribute("rentalNo", rentalNo);
            }

        }

        model.addAttribute("images", files);
        model.addAttribute("book", book);

        return "front/books/detail";
    }

}
