package org.awesome;

import org.awesome.entities.RentalBook;
import org.awesome.repositories.RentalBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {
    @Autowired
    private RentalBookRepository rentalBookRepository;

    @Test
    @DisplayName("하아")
    public void test() {
        String bookId = "234";

        RentalBook rentalBook = rentalBookRepository.findById(bookId).orElse(null);
        System.out.println(rentalBook);

    }
}
