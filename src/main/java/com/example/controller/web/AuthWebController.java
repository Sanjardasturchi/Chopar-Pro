package com.example.controller.web;

import com.example.dto.AuthDTO;
import com.example.dto.ProfileDTO;
import com.example.enums.Language;
import com.example.exp.AppBadException;
import com.example.service.AuthService;
import com.example.service.ProductService;
import com.example.service.ProfileService;
import com.example.service.ResourceBundleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RestController
@RequestMapping("/auth/api")
public class AuthWebController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ResourceBundleService resourceBundleService;

    @PostMapping("/login")
    @Operation(summary = "Api for login", description = "this api used for authorization ")
    public ResponseEntity<ProfileDTO> loginWeb(@Valid @RequestBody AuthDTO auth,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        ProfileDTO profile = authService.getProfile(auth);
        if (profile == null) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.found",language));
        }
        return ResponseEntity.ok(profile);
    }
}
