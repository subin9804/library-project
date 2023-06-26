package org.awesome.models.book;

import lombok.RequiredArgsConstructor;
import org.awesome.constants.RentalType;
import org.awesome.controllers.admin.books.BookForm;
import org.awesome.entities.Rental;
import org.awesome.entities.RentalBook;
import org.awesome.repositories.RentalBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * 도서 정보 저장 처리(추가, 수정)
 *
 */
@Service
@RequiredArgsConstructor
public class BookSaveService {
    private final RentalBookRepository repository;

    public void save(BookForm bookForm) {

        String mode = bookForm.getMode();
        RentalBook rentalBook = null;
        if (mode.equals("update")) {
            String bookId = bookForm.getBookId();
            rentalBook = repository.findById(bookId).orElseGet(RentalBook::new);
            rentalBook.setBookId(bookId);
            rentalBook.setBookNm(bookForm.getBookNm());
            rentalBook.setAuthor(bookForm.getAuthor());
            rentalBook.setGid(bookForm.getGid());
            rentalBook.setPublisher(bookForm.getPublisher());
            rentalBook.setDescription(bookForm.getDescription());
        } else {
            rentalBook = new ModelMapper().map(bookForm, RentalBook.class);
        }

        rentalBook.setRentalType(RentalType.valueOf(bookForm.getRentalType()));

        repository.saveAndFlush(rentalBook);

    }
}
