package com.example.controller.thymleaf;

import com.example.dto.ProfileDTO;
import com.example.dto.extra.CreateProfileByAdminDTO;
import com.example.dto.extra.RegistrationByEmailDTO;
import com.example.enums.Language;
import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/go-to-create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String goToCreate(Model model) {
        model.addAttribute("profile", new RegistrationByEmailDTO());
        return "admin/profile/create";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(Model model, @ModelAttribute ProfileDTO dto) {
        if (profileService.saveByAdmin(dto)) {

        }
        return "admin/main";
    }
}
