package com.example.controller.web;

import com.example.dto.RegionDTO;
import com.example.dto.extra.region.CreateRegionDTO;
import com.example.enums.Language;
import com.example.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Region Api list", description = "Api list for Region")
@RestController
@RequestMapping("/region/api")
public class RegionWebController {
    //=============== Service =================
    @Autowired
    private RegionService regionService;

    @PostMapping("/create")
    @Operation(summary = "Api for create", description = "this api used for create region by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid @RequestBody CreateRegionDTO regionDTO) {
        regionService.create(regionDTO);
        return "DONE";
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Api for update", description = "this api used for update region by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@PathVariable("id") Integer id,
                         @Valid @RequestBody CreateRegionDTO regionDTO,
                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        regionService.update(id,regionDTO,language);
        return "DONE";
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Api for delete", description = "this api used for delete region by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Integer id,
                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        regionService.delete(id,language);
        return "DONE";
    }

    @GetMapping("/get-list")
    @Operation(summary = "Api for get", description = "this api used for get region list by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RegionDTO>> getList(){
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/get-by-language")
    @Operation(summary = "Api for get", description = "this api used for get region list by admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<RegionDTO>> getByLanguage(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language){
        return ResponseEntity.ok(regionService.getByLanguage(language));
    }
}
