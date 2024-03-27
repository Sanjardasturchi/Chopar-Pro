package com.example.controller.web;

import com.example.dto.EmailHistoryDTO;
import com.example.enums.Language;
import com.example.service.EmailHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Tag(name = "Email history Api list", description = "Api list for email history")
@RestController
@RequestMapping("/email-history/api")
public class EmailHistoryWebController {
    //=============== Service =================
    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/get-history-by-email/{email}")
    @Operation(summary = "Api for update", description = "this api used for update profile by admin")
    public ResponseEntity<List<EmailHistoryDTO>> getHistoryByEmail(@PathVariable("email")String email){
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    @GetMapping("/get-history-by-given-date/{date}")
    @Operation(summary = "Api for update", description = "this api used for update profile by admin")
    public ResponseEntity<List<EmailHistoryDTO>> getHistoryByGivenDate(@PathVariable("date")LocalDate date){
        return ResponseEntity.ok(emailHistoryService.getByDate(date));
    }

    @Operation(summary = "Api for update", description = "this api used for update profile by admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-history-by-pagination")
    public ResponseEntity<PageImpl<EmailHistoryDTO>>    getHistoryByPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language){
        return ResponseEntity.ok(emailHistoryService.getByPagination(size,page));
    }



    //7. EmailHistory
    //    id, message, email, created_data
    //    1. Create EmailHistory when email is send using application. (No need create api)
    //    2. Get EmailHistory by email
    //            (id, email,message,created_date)
    //    3. Get EmailHistory  by given date
    //            (id, email,message,created_date)
    //    4. Pagination (ADMIN)
    //            (id, email,message,created_date)
    //
    //       (!Should be limit for email sending. For 1 email 3 sms allowed during 1 minut. )
}
