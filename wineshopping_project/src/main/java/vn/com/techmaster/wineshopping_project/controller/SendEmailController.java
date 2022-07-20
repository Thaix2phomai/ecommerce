package vn.com.techmaster.wineshopping_project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.com.techmaster.wineshopping_project.dto.UserDTO;
import vn.com.techmaster.wineshopping_project.model.Contact;
import vn.com.techmaster.wineshopping_project.repository.ContactRepo;
import vn.com.techmaster.wineshopping_project.request.ContactRequest;
import vn.com.techmaster.wineshopping_project.request.ReplyRequest;
import vn.com.techmaster.wineshopping_project.service.ContactService;
import vn.com.techmaster.wineshopping_project.service.ReplyService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/send")
public class SendEmailController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepo contactRepo;


    @Autowired
    private ReplyService replyService;

    @GetMapping("contact")
    public String sendEmail(Model model) {
        model.addAttribute("contactRequest", new ContactRequest("", "", "", ""));
        return "contact";
    }


    @PostMapping("contact")
    public String sendMessageToAdmin(@Valid @ModelAttribute("contactRequest") ContactRequest contactRequest, BindingResult result) {
        if (result.hasErrors()) {
            return "contact";
        }
        contactService.addNewMessage(contactRequest);
        return "redirect:/";
    }

    @GetMapping("message")
    public String listAllMessage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("messages", contactRepo.findAll());
        return "customer_message";
    }

    @GetMapping("reply/{id}")
    public String replyMesssge(Model model, @PathVariable("id") String id, HttpSession session) {
        UserDTO userDTO =  (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        Optional<Contact> contact = contactRepo.findById(id);
        if (contact.isPresent()) {
            Contact currentContact = contact.get();
            model.addAttribute("replyRequest", new ReplyRequest (currentContact.getEmail(), "")) ;
        }
        return "reply";

    }

    @PostMapping("/reply")
    public String replyMessegeAdmin(@Valid @ModelAttribute("replyRequest") ReplyRequest replyRequest, Model model, HttpSession session ){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        replyService.creatNewReply(replyRequest.getEmail(), replyRequest.getReply());
        return "redirect:/send/message";
    }
}
