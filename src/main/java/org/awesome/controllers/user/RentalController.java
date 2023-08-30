package org.awesome.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.awesome.constants.RentalStatus;
import org.awesome.entities.Rental;
import org.awesome.entities.RentalBook;
import org.awesome.models.rental.RentalService;
import org.awesome.repositories.RentalBookRepository;
import org.awesome.repositories.RentalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller("user.RentalController")
@RequiredArgsConstructor
@RequestMapping("/book")
public class RentalController {

    private final RentalBookRepository bookRepository;
    private final RentalService rentalService;
    public final RentalRepository rentalRepository;
    public final HttpServletRequest request;

    // 렌탈
    @PostMapping("/rent")
    public String rent() {
        rentalService.rental();

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    // 리턴
    @PostMapping("/return")
    public String returnPs () {

        String bookId = request.getParameter("bookId");
        Long rentalNo = Long.valueOf(request.getParameter("rentalNo"));
        LocalDate realRtDt = LocalDate.now();

        RentalBook book = bookRepository.findById(bookId).orElse(null);
        Rental item = rentalRepository.findById(rentalNo).orElse(null);

        // 실제반납일 기록
        item.setRealRtDt(realRtDt);
        rentalRepository.flush();

        // 렌탈 상태 반납으로 변경
        item.setStatus(RentalStatus.valueOf("RETURN"));
        rentalRepository.flush();

        // 도서 상태 반납으로 변경
        book.setStatus(RentalStatus.valueOf("RETURN"));
        bookRepository.flush();

        // 연장횟수 0으로 변경
        item.setDelayCnt(0);

        // mypage에서 반납하는 경우 mypage로 리다이렉트,
        // 도서상세페이지에서 반납하는 경우 이전경로로 리다이렉트
        String uri = request.getRequestURI();
        if(uri == "/mypage/rent") {
            return "redirect:/mypage/rent";
        } else {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
    }



}


