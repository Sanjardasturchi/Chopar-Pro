package com.example.controller.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Product Api list", description = "Api list for product")
@RestController
@RequestMapping("/product/api")
public class ProductController {

}
