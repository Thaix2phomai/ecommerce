package vn.com.techmaster.wineshopping_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.techmaster.wineshopping_project.dto.UserDTO;
import vn.com.techmaster.wineshopping_project.model.User;
import vn.com.techmaster.wineshopping_project.repository.UserRepo;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String listAllUsers(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }


}
