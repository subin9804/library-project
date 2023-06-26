package org.awesome.models.book;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.awesome.controllers.admin.books.BookSearch;
import org.awesome.entities.RentalBook;
import org.awesome.repositories.RentalBookRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookListService {

    private final EntityManager em;

    private final RentalBookRepository repository;

    private Page<RentalBook> data;

    public BookListService gets(BookSearch bookSearch) {

        data = repository.getBooks(bookSearch);

        return this;
    }

    public List<RentalBook> toList() {
        List<RentalBook> books = data.getContent();
        books.stream().forEach(em::detach); // 영속성 분리

        return books;
    }

    public Page<RentalBook> getPage() {
        return data;
    }
}
