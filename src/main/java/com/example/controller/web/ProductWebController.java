package com.example.controller.web;

import com.example.dto.extra.ProductCreateDTO;
import com.example.service.ProductService;
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



//8. product
//    id,order_number,name_uz, name_ru, name_en,visible,created_date, description_uz, description_ru, description_en,
//  	price, category_id
//  / 1. create product (order_number,name_uz, name_ru, name_en, description_uz.ru.en, price, attachlist[image_id,image_id], category_id)
//       admin page(https://demo.templatemonster.com/demo/172401.html?_gl=1*t9frl*_ga*NTkzNDY4MDc0LjE2OTY3NTg0NDQ.*_ga_FTPYEGT5LY*MTY5Njc1ODQ0NC4xLjEuMTY5Njc1ODQ1OC40Ni4wLjA.&_ga=2.142290298.974307283.1696758445-593468074.1696758444)
//    2. update  (order_number,name_uz, name_ru, name_en, description_uz.ru.en, price, attachlist[image_id,image_id], category_id)
//    3. delete(visible=false)
//    4. get pagination by categoryId and language
//		(id,name,description,price,mainImage,)
//    5. get last 10 added productList
//		(id,name,description,price,mainImage,)
//	6. gat top selled 10 products
//		(id,name,description,price,mainImage,)
//	9. Get by Id as client
//		(id,name,description,price,attachList,)
//	10. Get by Id as admin
//		(id, name_uz, name_ru, name_en,visible,created_date, description_uz, description_ru, description_en, price, category_id,  attachlist[])
}
