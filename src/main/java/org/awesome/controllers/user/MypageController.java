package org.awesome.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.awesome.commons.CommonException;
import org.awesome.commons.Pagination;
import org.awesome.entities.Rental;
import org.awesome.models.rental.RentalService;
import org.awesome.models.user.UserInfo;
import org.awesome.repositories.RentalBookRepository;
import org.awesome.repositories.RentalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    public final RentalService rentalService;
    public final RentalRepository rentalRepository;
    public final HttpServletRequest request;
    private final RentalBookRepository bookRepository;
    private final HttpServletResponse response;

    // 유저 프로필
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        model.addAttribute("userInfo", userInfo);
        return "front/mypage/profile";
    }

    // 유저 프로필 수정
    public String editProfile() {

        return "redirect:/front/mypage/profile";
    }


    // 대여 내역
    @GetMapping("/rent")
    public String index(Model model) {
        Page<Rental> page = rentalRepository.getRentalList();
        String url = request.getContextPath() + "/mypage";
        Pagination<Rental> pagination = new Pagination<>(page, url);

        List<Rental> rental = rentalRepository.findAll(Sort.by(Sort.Order.desc("rentDt")));

        model.addAttribute("pagination", pagination);
        model.addAttribute("rental", rental);

        return "front/mypage/rentalList";
    }


    // 대여기간 연장
    @GetMapping("delay/{rentalNo}")
    public String delay(@PathVariable long rentalNo, Model model) {
        Rental rental = rentalRepository.findById(rentalNo).orElse(null);

        if(rental == null) {
            throw new CommonException("대여하지 않은 도서입니다.");
        }

        if(rental.getDelayCnt() > 1) {
            throw new CommonException("대여 연장 횟수를 초과하였습니다.");
        } else {
            rental.setDelayCnt(rental.getDelayCnt() + 1);
            rental.setReturnDt(rental.getReturnDt().plusDays(7));

            System.out.println("plusDays: " + rental.getReturnDt().plusDays(7));
            rentalRepository.saveAndFlush(rental);
        }

        return "redirect:/mypage/rent";
    }

    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model) {
        e.printStackTrace();

        String message = e.getMessage();
        HttpStatus status = e.getStatus();
        response.setStatus(status.value());

        String script = String.format("alert('%s');history.back();", message);
        model.addAttribute("script", script);
        return "commons/execute_script";
    }

}
