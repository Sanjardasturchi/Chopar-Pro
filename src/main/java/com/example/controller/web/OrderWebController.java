package com.example.controller.web;

import com.example.dto.OrderDTO;
import com.example.dto.extra.order.OrderCreateDTO;
import com.example.dto.extra.order.OrderFilterDTO;
import com.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Email history Api list", description = "Api list for email history")
@RestController
@RequestMapping("/order/api")
public class OrderWebController {
    //=============== Service =================
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "Api for create", description = "this api used for create order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<String> create(@RequestBody OrderCreateDTO order) {
        return ResponseEntity.ok(orderService.create(order));
    }

    @PutMapping("/cancel/{id}")
    @Operation(summary = "Api for cancel", description = "this api used for cancel order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> cancel(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.cancel(id));
    }

    @PutMapping("/cancelByUser/{id}")
    @Operation(summary = "Api for cancel by client", description = "this api used for cancel by client order")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<String> cancelByUser(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.cancelByUser(id));
    }

    @PutMapping("/delivered/{id}")
    @Operation(summary = "Api for put", description = "this api used for delivered order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> delivered(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.delivered(id));
    }

    @PutMapping("/cancelled/{id}")
    @Operation(summary = "Api for put", description = "this api used for cancelled order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> cancelled(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.cancelled(id));
    }

    @PutMapping("/on-the-way/{id}")
    @Operation(summary = "Api for put", description = "this api used for onTheWay order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> onTheWay(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.onTheWay(id));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Api for update", description = "this api used for update order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<String> update(@PathVariable("id") Integer id,
                                         @RequestBody OrderCreateDTO order) {
        return ResponseEntity.ok(orderService.update(id, order));
    }

    @GetMapping("/get-by-id/{id}")
    @Operation(summary = "Api for get", description = "this api used for get by id")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/get-by-pagination")
    @Operation(summary = "Api for get", description = "this api used for get by pagination")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<PageImpl<OrderDTO>> getByPagination(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                              @RequestParam(value = "size",defaultValue = "10") Integer size) {
        return ResponseEntity.ok(orderService.getByPagination(page, size));
    }

    @GetMapping("/get-by-filter")
    @Operation(summary = "Api for get", description = "this api used for get by filter with pagination")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<OrderDTO>> getByPaginationWithFilter(@RequestBody OrderFilterDTO filterDTO,
                                                                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                        @RequestParam(value = "size",defaultValue = "10") Integer size) {
        return ResponseEntity.ok(orderService.      getByPaginationWithFilter(filterDTO,page, size));
    }

}