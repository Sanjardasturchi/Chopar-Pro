package com.example.controller.thymleaf;

import com.example.dto.AuthDTO;
import com.example.dto.ProductDTO;
import com.example.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/go-to-info/{id}")
    public String goToFailedLoginPage(Model model, @PathVariable("id") Integer id) {
        ProductDTO productDTO = productService.getById(id);
        if (productDTO == null) {
            return "404";
        }
        model.addAttribute("product", productDTO);
        return "product/product-info";
    }
}
