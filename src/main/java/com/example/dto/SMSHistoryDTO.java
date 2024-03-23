package com.example.dto;

import com.example.enums.SMSStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class SMSHistoryDTO {
    private Integer id;
    private String phone;
    private String message;
    private SMSStatus status;
    private LocalDateTime createdDate;
}
