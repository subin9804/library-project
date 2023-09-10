package org.awesome.models.rental;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.awesome.constants.RentalStatus;
import org.awesome.entities.Rental;
import org.awesome.entities.RentalBook;
import org.awesome.entities.User;
import org.awesome.models.user.UserInfo;
import org.awesome.repositories.RentalBookRepository;
import org.awesome.repositories.RentalRepository;
import org.awesome.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Log
@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalBookRepository bookRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public void rental() {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String userId = userInfo.getUserId().toString();
        User user = userRepository.findByUserId(userId);

        String bookId = request.getParameter("bookId");
        RentalBook book = bookRepository.findById(bookId).orElse(null);

//        log.info(user.toString());
//        log.info(bookId.toString());

        if(user != null && book != null) {
            Rental rental = Rental.builder()
                .book(book)
                .user(user)
                .status(RentalStatus.RENT)
                .bookNm(book.getBookNm())
                .returnDt(LocalDate.now().plusDays(7))
                .build();

            rentalRepository.saveAndFlush(rental);

            // 책 상태 rent로 바꾸기
            book.setStatus(RentalStatus.valueOf("RENT"));
            bookRepository.flush();
        }
    }

    public Rental getRentalInfo(String bookId) {
        RentalBook book = bookRepository.findById(bookId).orElse(null);
        List<Rental> rentalList = rentalRepository.findAllByBook(book);

        Rental rented = null;
        for(Rental rental : rentalList) {
            if(rental.getStatus() == RentalStatus.RENT) {
                rented = rental;
                break;
            }
        }

        System.out.println(rented);
        return rented;
    }
}
