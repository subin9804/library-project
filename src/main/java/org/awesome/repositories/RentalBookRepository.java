package org.awesome.repositories;

import com.querydsl.core.BooleanBuilder;
import org.awesome.constants.RentalStatus;
import org.awesome.constants.RentalType;
import org.awesome.controllers.admin.books.BookSearch;
import org.awesome.entities.QRentalBook;
import org.awesome.entities.RentalBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Arrays;
import java.util.List;

public interface RentalBookRepository extends JpaRepository<RentalBook, String>, QuerydslPredicateExecutor<RentalBook> {

    default Page<RentalBook> getBooks(BookSearch bookSearch) {
        /** 페이징 처리 S */
        int page = bookSearch.getPage();
        page = page < 1 ? 1 : page;
        int limit = bookSearch.getLimit();
        limit = limit < 1 ? 20 : limit;

        Pageable pageable = PageRequest.of(page - 1, limit);
        /** 페이징 처리 E */

        /** 검색 조건 처리 S */
        BooleanBuilder builder = new BooleanBuilder();
        QRentalBook rentalBook = QRentalBook.rentalBook;
        String bookId = bookSearch.getBookId();
        String sopt = bookSearch.getSopt();
        String skey = bookSearch.getSkey();
        String[] status = bookSearch.getStatus();
        String[] rentalType = bookSearch.getRentalType();

        if (bookId != null && !bookId.isBlank()) { // 도서ID
            builder.and(rentalBook.bookId.contains(bookId));
        }

        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            BooleanBuilder orBuilder = new BooleanBuilder();
            if (sopt.equals("bookNm")) { // 책이름
                orBuilder.or(rentalBook.bookNm.contains(skey));
            } else if (sopt.equals("author")) { // 저자
                orBuilder.or(rentalBook.author.contains(skey));
            } else if (sopt.equals("publisher")) { // 출판사
                orBuilder.or(rentalBook.publisher.contains(skey));
            } else {
                orBuilder.andAnyOf(rentalBook.bookNm.contains(skey),
                                rentalBook.author.contains(skey),
                                rentalBook.publisher.contains(skey));
            }

            builder.and(orBuilder);
        }

        if (status != null && status.length > 0) { // 대여 상태 조회
            List<RentalStatus> statuses = Arrays.stream(status).map(RentalStatus::valueOf).toList();
            builder.and(rentalBook.status.in(statuses));
        }

        if (rentalType != null && rentalType.length > 0) { // 도서 종류
            List<RentalType> rentalTypes = Arrays.stream(rentalType).map(RentalType::valueOf).toList();
            builder.and(rentalBook.rentalType.in(rentalTypes));
        }
        /** 검색 조건 처리 E */

        Page<RentalBook> data = findAll(builder, pageable);
        return data;
    }
}
