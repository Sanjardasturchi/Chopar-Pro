package com.example.controller.web;

import com.example.dto.extra.region.CreateRegionDTO;
import com.example.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RestController
@RequestMapping("/region/api")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/create")
    @Operation(summary = "Api for create", description = "this api used for create region by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid @RequestBody CreateRegionDTO regionDTO) {
        regionService.create(regionDTO);
        return "DONE";
    }
}
