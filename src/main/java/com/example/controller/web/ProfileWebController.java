package com.example.controller.web;

import com.example.dto.ProfileDTO;
import com.example.dto.extra.profile.UpdateProfileDTO;
import com.example.enums.Language;
import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Profile Api list", description = "Api list for Profile")
@RestController
@RequestMapping("/profile/api")
public class ProfileWebController {
    //=============== Service =================
    @Autowired
    private ProfileService profileService;

    @PutMapping("/upda+te/{id}")
    @Operation(summary = "Api for update", description = "this api used for update profile by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@PathVariable("id") String profileId,
                         @Valid @RequestBody UpdateProfileDTO updateDTO,
                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return profileService.updateByAdmin(updateDTO, profileId, language);
    }

    @PutMapping("/update-own-detail")
    @Operation(summary = "Api for update", description = "this api used for update profile own detail")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public String updateOwnDetail(@Valid @RequestBody UpdateProfileDTO updateDTO,
                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return profileService.updateOwnDetail(updateDTO, language);
    }

    @GetMapping("/get-by-pagination")
    @Operation(summary = "Api for get", description = "this api used for get profile list by pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<ProfileDTO>> getByPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(profileService.getByPagination(page, size, language));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Api for get", description = "this api used for get profile list by pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id,
                                             @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(profileService.deleteById(id, language));
    }
}
