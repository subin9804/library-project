package org.awesome.controllers.admin.books;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.awesome.commons.Pagination;
import org.awesome.entities.RentalBook;
import org.awesome.models.book.BookInfoService;
import org.awesome.models.book.BookListService;
import org.awesome.models.book.BookSaveService;
import org.awesome.repositories.RentalBookRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller("adminBookController")
@RequestMapping("/admin/book")
@RequiredArgsConstructor
public class BookController {
    private final BookSaveService saveService;
    private final BookInfoService infoService;
    private final BookListService listService;
    private final RentalBookRepository bookRepository;
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

        String[] addScript = { "ckeditor/ckeditor", "book/form" };
        model.addAttribute("addScript", addScript);
        model.addAttribute("bookForm", bookForm);
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
        return "redirect:/admin/book";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") String bookId, Model model){
        RentalBook book = bookRepository.findById(bookId).orElse(null);

        if(book != null) {
            bookRepository.delete(book);
        }
        bookRepository.flush();
        return "redirect:/admin/book";
    }

}
