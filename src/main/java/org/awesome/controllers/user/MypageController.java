package org.awesome.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.awesome.commons.CommonException;
import org.awesome.commons.Pagination;
import org.awesome.entities.Rental;
import org.awesome.entities.User;
import org.awesome.models.rental.RentalService;
import org.awesome.models.user.ProfileEditService;
import org.awesome.models.user.UserInfo;
import org.awesome.repositories.RentalRepository;
import org.awesome.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    public final RentalService rentalService;
    public final RentalRepository rentalRepository;
    public final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserRepository userRepository;
    private final ProfileEditService editService;
    private final PasswordEncoder encoder;

    // 유저 프로필
    @GetMapping
    public String profile(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        User user = userRepository.findById(userInfo.getUserNo()).orElse(null);

        model.addAttribute("user", user);

        return "front/mypage/profile";
    }

    // 유저 프로필 수정
    @GetMapping("/edit")
    public String editProfile(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        boolean isChecked = (boolean) session.getAttribute("isChecked");

        if(isChecked != true) {
            return "front/mypage/password";
        }

        userInfo.setUserPw("");
        userInfo.setUserPwCk("");
        model.addAttribute("userInfo", userInfo);

        return "front/mypage/profileEdit";
    }

    @PostMapping("/password")
    public String password(HttpSession session, String password) {
        System.out.println(password);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        User user = userRepository.findById(userInfo.getUserNo()).orElse(null);

        if(encoder.matches(password, user.getUserPw())) {
            session.setAttribute("isChecked", true);
            return "redirect:/mypage/edit";
        } else throw new CommonException("비밀번호가 일치하지 않습니다.");
    }

    @PostMapping("/edit")
    public String editPs(HttpSession session, UserInfo userInfo) {
        editService.editProfile(session, userInfo);

        session.setAttribute("isChecked", false);
        return "redirect:/mypage";
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
