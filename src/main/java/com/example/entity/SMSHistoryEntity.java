package com.example.entity;

import com.example.enums.SMSStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "sms_history")
public class SMSHistoryEntity extends BaseEntity{
    private String phone;
    private String message;
    private SMSStatus status;
}
