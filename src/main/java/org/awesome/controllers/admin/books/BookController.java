package org.awesome.controllers.admin.books;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.awesome.commons.Pagination;
import org.awesome.constants.RentalStatus;
import org.awesome.entities.FileInfo;
import org.awesome.entities.Rental;
import org.awesome.entities.RentalBook;
import org.awesome.models.book.BookInfoService;
import org.awesome.models.book.BookListService;
import org.awesome.models.book.BookSaveService;
import org.awesome.repositories.FileInfoRepository;
import org.awesome.repositories.RentalBookRepository;
import org.awesome.repositories.RentalRepository;
import org.awesome.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;


@Controller("adminBookController")
@RequestMapping("/admin/book")
@RequiredArgsConstructor
public class BookController {
    private final BookSaveService saveService;
    private final BookInfoService infoService;
    private final BookListService listService;
    private final RentalBookRepository bookRepository;
    private final FileInfoRepository fileRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final HttpSession session;

    @GetMapping
    public String index(BookSearch bookSearch, Model model, HttpServletRequest request) {

        List<RentalBook> books = listService.gets(bookSearch).toList();
        Page<RentalBook> page = listService.getPage();

        String url = request.getContextPath() + "/admin/book";
        Pagination<RentalBook> pagination = new Pagination<>(page, url);
        model.addAttribute("bookSearch", bookSearch);
        model.addAttribute("pagination", pagination);
        model.addAttribute("books", books);
        return "admin/book/index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        BookForm bookForm = new BookForm();
        model.addAttribute("bookForm", bookForm);

        String[] addScript = { "ckeditor/ckeditor", "book/form" };
        model.addAttribute("addScript", addScript);
        return "admin/book/register";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") String bookId, Model model) {
        BookForm bookForm = infoService.get(bookId);
        bookForm.setMode("update");

        // 파일리스트 불러오기
        List<FileInfo> files = fileRepository.findByGid(bookForm.getGid());

        String[] addScript = { "ckeditor/ckeditor", "book/form" };
        model.addAttribute("addScript", addScript);
        model.addAttribute("bookForm", bookForm);
        model.addAttribute("files", files);
        return "admin/book/update";
    }

    @PostMapping("/save")
    public String save(@Valid BookForm bookForm, Errors errors, Model model) {

        if (errors.hasErrors()) {
            System.out.println(errors);
            String mode = bookForm.getMode();
            if (mode.equals("update")) {
                return "admin/book/update";
            } else {
                return "admin/book/register";
            }
        }
        saveService.save(bookForm);

        // 파일 업로드 완료 및 메인 이미지 지정(location)
        List<FileInfo> file = fileRepository.findByGidOrderByRegDtAsc(bookForm.getGid());
        if(file != null || !file.isEmpty()) {
            file.stream().forEach(s -> s.setDone(true));
        }
        fileRepository.saveAllAndFlush(file);
        System.out.println(file);
        return "redirect:/admin/book";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") String bookId, Model model){
        RentalBook book = bookRepository.findById(bookId).orElse(null);
        List<Rental> rentals = rentalRepository.findAllByBook(book);


        // 삭제된 도서의 대여기록은 반납처리(0000-00-00)
        for(Rental rental : rentals) {
//            User user = userRepository.findByRental(rental);
//            if(user != null) {
//                Rental rentalRecord = user
//            }

            rental.setBook(null);
            rental.setStatus(RentalStatus.RETURN);
            rental.setRealRtDt(LocalDate.parse("1111-11-11"));
        }
        if(book != null) {
            bookRepository.delete(book);
        }
        bookRepository.flush();
        return "redirect:/admin/book";
    }

}
