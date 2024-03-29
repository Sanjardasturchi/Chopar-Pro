package com.example.controller.web;

import com.example.dto.SMSHistoryDTO;
import com.example.service.SMSHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Tag(name = "Product Api list", description = "Api list for product")
@RestController
@RequestMapping("/sms-history/api")
public class SMSHistoryWebController {
    @Autowired
    private SMSHistoryService smsHistoryService;

    @GetMapping("/getByPhone")
    @Operation(summary = "Api for get",description = "Api for get SMSHistory by phone number")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<SMSHistoryDTO>> getByPhone(@RequestParam("phone")String phone){
        return ResponseEntity.ok(smsHistoryService.getByPhone(phone));
    }

    @GetMapping("/getByDate")
    @Operation(summary = "Api for get",description = "Api for get SMSHistory by given date")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<SMSHistoryDTO>> getByDate(@RequestParam("date") LocalDate date){
        return ResponseEntity.ok(smsHistoryService.getByDate(date));
    }

    @GetMapping("/getByPagination")
    @Operation(summary = "Api for get",description = "Api for get SMSHistory by pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<SMSHistoryDTO>> getByPagination(@RequestParam("page") Integer page,
                                                                   @RequestParam("size") Integer size){
        return ResponseEntity.ok(smsHistoryService.getByPagination(page,size));
    }
}
