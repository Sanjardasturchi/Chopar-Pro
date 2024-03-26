package com.example.controller.web;

import com.example.dto.ProductDTO;
import com.example.dto.extra.ProductCreateDTO;
import com.example.enums.Language;
import com.example.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Product Api list", description = "Api list for product")
@RestController
@RequestMapping("/product/api")
public class ProductWebController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "Api for create", description = "this api used for create product by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid @RequestBody ProductCreateDTO productDTO) {
        productService.create(productDTO);
        return "DONE";
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Api for update", description = "this api used for update product by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@PathVariable("id") Integer id,
                         @Valid @RequestBody ProductCreateDTO productDTO,
                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return productService.update(id, productDTO,language);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Api for delete", description = "this api used for delete product by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Integer id,
                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return productService.delete(id,language);
    }

    @GetMapping("/getByPaginationByCategoryId")
    @Operation(summary = "Api for get", description = "this api used for get By Pagination By Category Id")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<PageImpl<ProductDTO>> getByPaginationByCategoryId(@RequestParam("categoryId") Integer categoryId,
                                                                            @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                            @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(productService.getByPaginationByCategoryId(categoryId,language,page,size));
    }

    @GetMapping("/getLastTenAddedProducts")
    @Operation(summary = "Api for get", description = "this api used for get Last 10 added products")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<ProductDTO>> getLastTenAddedProducts(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(productService.getLastTenAddedProducts(language));
    }

    @GetMapping("/getTopTenSoldProducts")
    @Operation(summary = "Api for get", description = "this api used for get top 10 sold products")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<ProductDTO>> getTopTenSoldProducts(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(productService.getTopTenSoldProducts(language));
    }

    @GetMapping("/getByIdForClient/{id}")
    @Operation(summary = "Api for get", description = "this api used for get by for client")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ProductDTO> getByIdForClient(@PathVariable("id")Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(productService.getByIdForClient(id,language));
    }

    @GetMapping("/getByIdForAdmin/{id}")
    @Operation(summary = "Api for get", description = "this api used for get by for admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ProductDTO> getByIdForAdmin(@PathVariable("id")Integer id,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(productService.getByIdForAdmin(id,language));
    }
}
