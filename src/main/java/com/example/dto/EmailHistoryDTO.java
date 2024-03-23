package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class EmailHistoryDTO {
    private Integer id;
    private String message;
    private String email;
    private LocalDateTime createdData;
}
