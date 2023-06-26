package org.awesome.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.awesome.commons.Pagination;
import org.awesome.constants.RentalStatus;
import org.awesome.entities.Rental;
import org.awesome.entities.RentalBook;
import org.awesome.models.rental.RentalService;
import org.awesome.repositories.RentalBookRepository;
import org.awesome.repositories.RentalRepository;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Log
@Controller
@RequiredArgsConstructor
public class MypageController {

    public final RentalService rentalService;
    public final RentalRepository rentalRepository;
    public final HttpServletRequest request;
    private final RentalBookRepository bookRepository;

    // 대여 내역
    @GetMapping("/mypage/rent")
    public String index(Model model) {
        Page<Rental> page = rentalRepository.getRentalList();
        String url = request.getContextPath() + "/mypage";
        Pagination<Rental> pagination = new Pagination<>(page, url);

        List<Rental> rental = rentalRepository.findAll(Sort.by(Sort.Order.desc("rentDt")));

        model.addAttribute("pagination", pagination);
        model.addAttribute("rental", rental);

        return "front/mypage/rentalList";
    }

}
