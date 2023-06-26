package org.awesome.controllers.admin.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.awesome.commons.CommonException;
import org.awesome.commons.Pagination;
import org.awesome.constants.RentalStatus;
import org.awesome.entities.Rental;
import org.awesome.entities.User;
import org.awesome.models.user.UserEditService;
import org.awesome.models.user.UserListService;
import org.awesome.repositories.RentalRepository;
import org.awesome.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("adminUserController")
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserListService userListService;
    private final UserEditService userEditService;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final HttpServletResponse response;
    private final HttpServletRequest request;

    @GetMapping
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
    @GetMapping("/edit/{userNo}")
    public String editUser(@PathVariable Long userNo, Model model) {
        User user = userRepository.findById(userNo).orElseGet(User::new);
        UserListForm userForm = null;
        if (user != null) {
            userForm = new ModelMapper().map(user, UserListForm.class);
            userForm.setUserType(user.getUserType().toString());
        }

//        String password = user.getUserPw();
//        System.out.println("pass: "+password);

        String addScript = "admin/setUserPw";

        model.addAttribute("userForm", userForm);
        model.addAttribute("addScript", addScript);
        return "admin/user/edit";
    }

    @PostMapping("/edit")
    public String editUserPs(@Valid UserListForm userForm, Errors errors, Model model) {
        System.out.println(userForm.getUserNo());

        if (errors.hasErrors()) {
            System.out.println(errors);
            return "admin/user/edit";
        }

        userEditService.edit(userForm);

        return "redirect:/admin/user";
    }


    // user 삭제
    @GetMapping("/delete/{userNo}")
    public String deleteUser(@PathVariable Long userNo){
        User user = userRepository.findById(userNo).orElse(null);
        List<Rental> books = rentalRepository.findAllByUser(user);
        boolean haveToReturn = false;

        for(Rental book : books) {
            if(book.getStatus() == RentalStatus.RENT) {
                haveToReturn = true;
            }
        }
        if(haveToReturn) {
            throw new CommonException("반납하지 않은 도서가 있습니다.", HttpStatus.BAD_REQUEST);
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