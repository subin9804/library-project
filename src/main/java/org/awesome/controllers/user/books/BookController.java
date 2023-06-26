package org.awesome.controllers.user.books;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.awesome.entities.RentalBook;
import org.awesome.models.rental.RentalService;
import org.awesome.repositories.RentalBookRepository;
import org.awesome.repositories.RentalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final RentalBookRepository bookRepository;
    private final RentalService rentalService;
    public final RentalRepository rentalRepository;
    public final HttpServletRequest request;

    // 상세페이지
    @GetMapping("/{bookId}")
    public String index(@PathVariable(name="bookId") String bookId, Model model) {
        RentalBook book = bookRepository.findById(bookId).orElse(null);
        model.addAttribute("book", book);

        return "front/books/detail";
    }

}
