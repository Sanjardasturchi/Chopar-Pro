package com.example.controller.web;

import com.example.dto.CategoryDTO;
import com.example.enums.Language;
import com.example.service.CategoryService;
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
@Tag(name = "Category Api list", description = "Api list for Category")
@RestController
@RequestMapping("/category/api")
public class CategoryWebController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @Operation(summary = "Api for create", description = "this api used for create category by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
        return "DONE";
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Api for update", description = "this api used for update category by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@PathVariable("id") Integer id,
                         @Valid @RequestBody CategoryDTO categoryDTO,
                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        categoryService.update(id, categoryDTO, language);
        return "DONE";
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Api for delete", description = "this api used for delete category by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Integer id,
                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        categoryService.delete(id, language);
        return "DONE";
    }

    @GetMapping("/get-list")
    @Operation(summary = "Api for get", description = "this api used for get category list by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoryDTO>> getList() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/get-by-language")
    @Operation(summary = "Api for get", description = "this api used for get category list by admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<CategoryDTO>> getByLanguage(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(categoryService.getByLanguage(language));
    }
}
