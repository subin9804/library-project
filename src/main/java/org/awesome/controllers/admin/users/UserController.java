package org.awesome.controllers.admin.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.awesome.commons.CommonException;
import org.awesome.commons.Pagination;
import org.awesome.constants.RentalStatus;
import org.awesome.entities.Rental;
import org.awesome.entities.RentalBook;
import org.awesome.entities.User;
import org.awesome.models.user.UserEditService;
import org.awesome.models.user.UserListService;
import org.awesome.repositories.RentalBookRepository;
import org.awesome.repositories.RentalRepository;
import org.awesome.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller("adminUserController")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserListService userListService;
    private final UserEditService userEditService;
    private final UserRepository userRepository;
    private final RentalBookRepository bookRepository;
    private final RentalRepository rentalRepository;
    private final HttpServletResponse response;
    private final HttpServletRequest request;

    @GetMapping("/user")
    public String userList(UserSearch userSearch, Model model, HttpServletRequest request) {
        List<User> userList = userListService.getUserList(userSearch).toList();
        Page<User> page = userListService.getPage();

        String url = request.getContextPath() + "/admin/user";
        Pagination<User> pagination = new Pagination<>(page, url);
        model.addAttribute("userList", userList);
        model.addAttribute("pagination", pagination);

        return "admin/user/index";
    }

    // user 정보 수정
    @GetMapping("user/edit/{userNo}")
    public String editUser(@PathVariable Long userNo, Model model) {
        User user = userRepository.findById(userNo).orElseGet(User::new);

        UserListForm userListForm = null;
        if (user != null) {
            userListForm = new ModelMapper().map(user, UserListForm.class);
            userListForm.setUserType(user.getUserType().toString());
        }

        String password = user.getUserPw();
        System.out.println("pass: "+password);

        String addScript = "admin/setUserPw";

        model.addAttribute("userListForm", userListForm);
        model.addAttribute("addScript", addScript);
        return "admin/user/edit";
    }

    @PostMapping("user/edit")
    public String editUserPs(@Valid UserListForm userForm, Errors errors) {

        if (errors.hasErrors()) {
            System.out.println(errors);
            return "admin/user/edit";
        }

        userEditService.edit(userForm);

        return "redirect:/admin/user";
    }


    // user 삭제
    @GetMapping("user/delete/{userNo}")
    public String deleteUser(@PathVariable Long userNo, Model model){
        User user = userRepository.findById(userNo).orElse(null);
        List<Rental> rentals = rentalRepository.findByUser(user);

        // 삭제될 유저가 빌린 도서들을 모두 반납처리
        // 삭제될 유저의 대여기록에서 유저정보 제거
        for(Rental rental : rentals) {

            if(rental.getStatus() == RentalStatus.RENT) {
                RentalBook book =  bookRepository.findById(rental.getBook().getBookId()).orElse(null);

                user.setRental(null);
                rental.setUser(null);
                rental.setRealRtDt(LocalDate.now());
                rental.setStatus(RentalStatus.RETURN);

                book.setStatus(RentalStatus.RETURN);

                rentalRepository.saveAndFlush(rental);
                bookRepository.saveAndFlush(book);
            }
        }

        if (user != null) {
            userRepository.delete(user);
        }
        userRepository.flush();
        return "redirect:/admin/user";
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