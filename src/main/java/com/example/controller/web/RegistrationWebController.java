package com.example.controller.web;

import com.example.dto.extra.profile.CreateProfileByAdminDTO;
import com.example.dto.extra.RegistrationByEmailDTO;
import com.example.enums.Language;
import com.example.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Registration Api list", description = "Api list for Registration")
@RestController
@RequestMapping("/registration/api")
public class RegistrationWebController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    @Operation( summary = "Api for registration", description = "this api used for create profile by admin")
    public String   register(@Valid @RequestBody RegistrationByEmailDTO registerDTO,
                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language){
        return registrationService.register(registerDTO, language);
    }



    @PostMapping("/register-by-admin")
    @Operation( summary = "Api for registration", description = "this api used for create profile by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String registerByAdmin(@Valid @RequestBody CreateProfileByAdminDTO createDTO,
                           @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language){
        return registrationService.registerByAdmin(createDTO, language);
    }
}
