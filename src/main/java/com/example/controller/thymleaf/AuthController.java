package com.example.controller.thymleaf;

import com.example.dto.AuthDTO;
import com.example.dto.ProfileDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.AuthService;
import com.example.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProductService productService;

    @PostMapping("/login")
    @Operation(summary = "Api for login", description = "this api used for authorization")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public String login(Model model, @Valid @ModelAttribute AuthDTO auth) {
        log.info("Login {} ", auth.getEmail());
        String result = authService.check(auth, Language.UZ);
        if (!result.equals("Success")) {
            if (result.equals("Invalid password")) {
                model.addAttribute("invalid", true);
            } else {
                model.addAttribute("error", true);
            }
            model.addAttribute("auth", new AuthDTO());
            model.addAttribute("logout", false);
            return "auth/login";
        }
        ProfileDTO profile = authService.getProfile(auth);
        if (profile.getRole().equals(ProfileRole.ROLE_USER)) {
            model.addAttribute("productList", productService.getAll(Language.UZ));
            return "user/main";
        }
        model.addAttribute("profile", profile);
        return "admin/main";
    }

    @GetMapping("/go-to-loginPage")
    public String goToFailedLoginPage(Model model) {
        model.addAttribute("auth", new AuthDTO());
        model.addAttribute("error", false);
        model.addAttribute("logout", false);
        return "auth/login";
    }

    @GetMapping("/go-to-loginPageWithLogout")
    public String goToLoginPageWithLogout(Model model) {
        model.addAttribute("auth", new AuthDTO());
        model.addAttribute("error", false);
        model.addAttribute("logout", true);
        return "auth/login";
    }

}
