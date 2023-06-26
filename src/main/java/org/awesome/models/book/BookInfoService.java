package org.awesome.models.book;

import lombok.RequiredArgsConstructor;
import org.awesome.controllers.admin.books.BookForm;
import org.awesome.entities.RentalBook;
import org.awesome.repositories.RentalBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookInfoService {
    private final RentalBookRepository repository;

    public BookForm get(String bookId) {
        BookForm bookForm = null;
        RentalBook book = repository.findById(bookId).orElse(null);
        if (book != null) {
            bookForm = new ModelMapper().map(book, BookForm.class);
            bookForm.setRentalType(book.getRentalType().toString());
        }

        return bookForm;
    }
}
