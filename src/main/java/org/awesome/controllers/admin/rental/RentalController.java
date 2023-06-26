package org.awesome.controllers.admin.rental;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.awesome.commons.Pagination;
import org.awesome.entities.Rental;
import org.awesome.entities.User;
import org.awesome.models.rental.RentalService;
import org.awesome.models.user.UserInfo;
import org.awesome.repositories.RentalRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log
@Controller("admin.rental.RentalController")
@RequiredArgsConstructor
@RequestMapping("/admin")
public class RentalController {
    public final RentalService rentalService;
    public final RentalRepository rentalRepository;
    public final HttpServletRequest request;

    // 대여 내역
    @GetMapping("/rental")
    public String index(Model model) {
        Page<Rental> page = rentalRepository.getRentalList();
        String url = request.getContextPath() + "/mypage";
        Pagination<Rental> pagination = new Pagination<>(page, url);

        List<Rental> rental = rentalRepository.findAll();

        model.addAttribute("pagination", pagination);
        model.addAttribute("rental", rental);

        return "admin/rental/index";
    }
}