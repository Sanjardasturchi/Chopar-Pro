package com.example.controller.web;

import com.example.dto.extra.CreateProfileByAdminDTO;
import com.example.enums.Language;
import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Profile Api list", description = "Api list for Profile")
@RestController
@RequestMapping("/profile")
public class ProfileWebController {
    @Autowired
    private ProfileService profileService;

    @PutMapping("/api/update/{id}")
    @Operation(summary = "Api for update", description = "this api used for update profile by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@PathVariable("id") String profileId,
                         @Valid @RequestBody CreateProfileByAdminDTO updateDTO,
                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return profileService.updateByAdmin(updateDTO,profileId, language);
    }
}
