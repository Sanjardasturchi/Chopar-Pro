package com.example.controller.thymleaf;

import com.example.dto.extra.RegistrationByEmailDTO;
import com.example.dto.spring.JWTDTO;
import com.example.enums.Language;
import com.example.service.RegistrationService;
import com.example.utils.JWTUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/register")
    public String register(Model model,@Valid @ModelAttribute RegistrationByEmailDTO register){
        log.info("registration {}", register.getEmail());
        String register1 = registrationService.register(register, Language.UZ);
        if (!register1.equals("Success")){
            model.addAttribute("registeration", new RegistrationByEmailDTO());
            model.addAttribute("info",register1);
            return "auth/login";
        }
        return "auth/verification";
    }
    @GetMapping("/go-to-add")
    public String goToAdd(Model model) {
        model.addAttribute("registeration", new RegistrationByEmailDTO());
        model.addAttribute("info","\uD83D\uDE0E");
        return "auth/register";
    }
    @GetMapping("/verification/email/{id}")
    public String verification(Model model,@PathVariable("id") String token) {
        JWTDTO decode = JWTUtil.decode(token);
        if (decode.getId() != null) {
            registrationService.changeProfileStatus(decode.getId());
//            return "auth/login";
            return "auth/registered";
        }
        return "404";
    }

    @GetMapping("/go-to-loginPage")
    public String goToFailedLoginPage(Model model) {
        model.addAttribute("error", false);
        model.addAttribute("logout", false);
        return "auth/login";
    }

    @GetMapping("/get")
    public String get(){
        return "DONE";
    }

}
